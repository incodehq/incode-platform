package org.isisaddons.module.excel.dom.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.filter.Filter;
import org.apache.isis.applib.filter.Filters;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.adapter.mgr.AdapterManager;
import org.apache.isis.core.metamodel.consent.InteractionInitiatedBy;
import org.apache.isis.core.metamodel.services.ServicesInjector;
import org.apache.isis.core.metamodel.services.ServicesInjectorAware;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.Contributed;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.core.metamodel.spec.feature.OneToOneAssociation;
import org.apache.isis.core.metamodel.specloader.SpecificationLoader;

import org.isisaddons.module.excel.dom.AggregationType;
import org.isisaddons.module.excel.dom.ExcelMetaDataEnabled;
import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.module.excel.dom.HyperLink;
import org.isisaddons.module.excel.dom.PivotColumn;
import org.isisaddons.module.excel.dom.PivotDecoration;
import org.isisaddons.module.excel.dom.PivotRow;
import org.isisaddons.module.excel.dom.PivotValue;
import org.isisaddons.module.excel.dom.RowHandler;
import org.isisaddons.module.excel.dom.WorksheetContent;
import org.isisaddons.module.excel.dom.WorksheetSpec;

class ExcelConverter {

    private static final String XLSX_SUFFIX = ".xlsx";

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static final Filter<ObjectAssociation> VISIBLE_PROPERTIES = Filters.and(
            ObjectAssociation.Filters.PROPERTIES,
            ObjectAssociation.Filters.staticallyVisible(Where.STANDALONE_TABLES));

    static class RowFactory {
        private final Sheet sheet;
        private int rowNum;

        RowFactory(final Sheet sheet) {
            this.sheet = sheet;
        }

        public Row newRow() {
            return sheet.createRow(rowNum++);
        }
    }

    // //////////////////////////////////////

    private final SpecificationLoader specificationLoader;
    private final AdapterManager adapterManager;
    private final BookmarkService bookmarkService;
    private final ServicesInjector servicesInjector;

    ExcelConverter(
            final SpecificationLoader specificationLoader,
            final AdapterManager adapterManager,
            final BookmarkService bookmarkService, final ServicesInjector servicesInjector) {
        this.specificationLoader = specificationLoader;
        this.adapterManager = adapterManager;
        this.bookmarkService = bookmarkService;
        this.servicesInjector = servicesInjector;
    }

    // //////////////////////////////////////

    File appendSheet(final List<WorksheetContent> worksheetContents, XSSFWorkbook workbook) throws IOException {
        final ImmutableSet<String> worksheetNames = FluentIterable.from(worksheetContents)
                .transform(new Function<WorksheetContent, String>() {
                    @Nullable @Override public String apply(@Nullable final WorksheetContent worksheetContent) {
                        return worksheetContent.getSpec().getSheetName();
                    }
                }).toSet();
        if(worksheetNames.size() < worksheetContents.size()) {
            throw new IllegalArgumentException("Sheet names must have distinct names");
        }
        for (final String worksheetName : worksheetNames) {
            if(worksheetName.length() > 30) {
                throw new IllegalArgumentException(
                        String.format("Sheet name cannot exceed 30 characters (invalid name: '%s')",
                                worksheetName));
            }
        }

        final File tempFile =
                File.createTempFile(ExcelConverter.class.getName(), UUID.randomUUID().toString() + XLSX_SUFFIX);
        final FileOutputStream fos = new FileOutputStream(tempFile);

        for (WorksheetContent worksheetContent : worksheetContents) {
            final WorksheetSpec spec = worksheetContent.getSpec();
            appendSheet(workbook, worksheetContent.getDomainObjects(), spec.getFactory(), spec.getSheetName());
        }
        workbook.write(fos);
        fos.close();
        return tempFile;
    }

    private Sheet appendSheet(
            final XSSFWorkbook workbook,
            final List<?> domainObjects,
            final WorksheetSpec.RowFactory<?> factory,
            final String sheetName) throws IOException {

        final ObjectSpecification objectSpec = specificationLoader.loadSpecification(factory.getCls());

        final List<ObjectAdapter> adapters = Lists.transform(domainObjects, ObjectAdapter.Functions.adapterForUsing(adapterManager));

        @SuppressWarnings("deprecation")
        final List<? extends ObjectAssociation> propertyList = objectSpec.getAssociations(VISIBLE_PROPERTIES);

        List<ObjectAssociation> annotatedAsHyperlink = new ArrayList<>();
        for (Field f : fieldsAnnotatedWith(factory.getCls(), HyperLink.class)){
            for (ObjectAssociation oa :propertyList){
                if (oa.getId().equals(f.getName())){
                    annotatedAsHyperlink.add(oa);
                }
            }
        }

        final Sheet sheet = ((Workbook) workbook).createSheet(sheetName);

        final RowFactory rowFactory = new RowFactory(sheet);
        final Row headerRow = rowFactory.newRow();


        // header row
        int i = 0;
        for (final ObjectAssociation property : propertyList) {
            final Cell cell = headerRow.createCell(i++);
            cell.setCellValue(property.getName());
        }

        final CellMarshaller cellMarshaller = newCellMarshaller(workbook);

        // detail rows
        for (final ObjectAdapter objectAdapter : adapters) {
            final Row detailRow = rowFactory.newRow();
            i = 0;
            for (final ObjectAssociation oa : propertyList) {
                final Cell cell = detailRow.createCell(i++);
                final OneToOneAssociation otoa = (OneToOneAssociation) oa;
                if (annotatedAsHyperlink.contains(oa)){
                    cellMarshaller.setCellValueForHyperlink(objectAdapter, otoa, cell);
                } else {
                    cellMarshaller.setCellValue(objectAdapter, otoa, cell);
                }
            }
        }

        // freeze panes
        sheet.createFreezePane(0, 1);

        return sheet;
    }

    File appendPivotSheet(final List<WorksheetContent> worksheetContents) throws IOException {
        final ImmutableSet<String> worksheetNames = FluentIterable.from(worksheetContents)
                .transform(new Function<WorksheetContent, String>() {
                    @Nullable @Override public String apply(@Nullable final WorksheetContent worksheetContent) {
                        return worksheetContent.getSpec().getSheetName();
                    }
                }).toSet();
        if(worksheetNames.size() < worksheetContents.size()) {
            throw new IllegalArgumentException("Sheet names must have distinct names");
        }
        for (final String worksheetName : worksheetNames) {
            if(worksheetName.length() > 30) {
                throw new IllegalArgumentException(
                        String.format("Sheet name cannot exceed 30 characters (invalid name: '%s')",
                                worksheetName));
            }
        }

        final XSSFWorkbook workbook = new XSSFWorkbook();
        final File tempFile =
                File.createTempFile(ExcelConverter.class.getName(), UUID.randomUUID().toString() + XLSX_SUFFIX);
        final FileOutputStream fos = new FileOutputStream(tempFile);

        for (WorksheetContent worksheetContent : worksheetContents) {
            final WorksheetSpec spec = worksheetContent.getSpec();
            appendPivotSheet(workbook, worksheetContent.getDomainObjects(), spec.getFactory(), spec.getSheetName());
        }
        workbook.write(fos);
        fos.close();
        return tempFile;
    }

    private void appendPivotSheet(
            final XSSFWorkbook workbook,
            final List<?> domainObjects,
            final WorksheetSpec.RowFactory<?> factory,
            final String sheetName) throws IOException {

        final ObjectSpecification objectSpec = specificationLoader.loadSpecification(factory.getCls());

        final List<ObjectAdapter> adapters = Lists.transform(domainObjects, ObjectAdapter.Functions.adapterForUsing(adapterManager));

        @SuppressWarnings("deprecation")
        final List<? extends ObjectAssociation> propertyList = objectSpec.getAssociations(VISIBLE_PROPERTIES);

        // Validate the annotations for pivot
        validateAnnotations(propertyList, factory.getCls());

        // Proces the annotations for pivot
        final List<String> annotationList = new ArrayList<>();
        final List<Integer> orderList = new ArrayList<>();
        final List<AggregationType> typeList = new ArrayList<>();
        for (AnnotationOrderAndType annotationOrderAndType : getAnnotationAndOrderFrom(propertyList, factory.getCls())){
            annotationList.add(annotationOrderAndType.annotation);
            orderList.add(annotationOrderAndType.order);
            typeList.add(annotationOrderAndType.type);
        }

        // create pivot sheet
        final Sheet pivotSheet = ((Workbook) workbook).createSheet(sheetName);

        // Create source sheet for pivot
        String pivotSourceSheetName = ("source for ".concat(sheetName));
        if (WorksheetSpec.isTooLong(pivotSourceSheetName)) {
            pivotSourceSheetName = WorksheetSpec.trim(pivotSourceSheetName);
        }
        final Sheet pivotSourceSheet = appendSheet(workbook, domainObjects, factory, pivotSourceSheetName);
        pivotSourceSheet.shiftRows(0, pivotSourceSheet.getLastRowNum(), 3);
        final Row annotationRow = pivotSourceSheet.createRow(0);
        final Row orderRow = pivotSourceSheet.createRow(1);
        final Row typeRow = pivotSourceSheet.createRow(2);
        PivotUtils.createAnnotationRow(annotationRow, annotationList);
        PivotUtils.createOrderRow(orderRow, orderList);
        PivotUtils.createTypeRow(typeRow, typeList);

        // And finally: fill the pivot sheet with a pivot of the values found in pivot source sheet
        SheetPivoter p = new SheetPivoter();
        p.pivot(pivotSourceSheet, pivotSheet);
        pivotSourceSheet.removeRow(annotationRow);
        pivotSourceSheet.removeRow(orderRow);
        pivotSourceSheet.removeRow(typeRow);
        pivotSourceSheet.shiftRows(3, pivotSourceSheet.getLastRowNum(), -3);
    }

    private void validateAnnotations(final List<? extends ObjectAssociation> list, Class<?> cls) throws IllegalArgumentException{

        if (fieldsAnnotatedWith(cls, PivotRow.class).size()==0){
            throw new IllegalArgumentException("No annotation for row found");
        }
        if (fieldsAnnotatedWith(cls, PivotRow.class).size()>1){
            throw new IllegalArgumentException("Only one annotation for row allowed");
        }
        if (fieldsAnnotatedWith(cls, PivotColumn.class).size()==0){
            throw new IllegalArgumentException("No annotation for column found");
        }
        if (fieldsAnnotatedWith(cls, PivotValue.class).size()==0){
            throw new IllegalArgumentException("No annotation for value found");
        }

    }

    private List<AnnotationOrderAndType> getAnnotationAndOrderFrom(final List<? extends ObjectAssociation> list, final Class<?> cls){

        List<AnnotationOrderAndType> results = new ArrayList<>();
        for (ObjectAssociation oa : list){
            AnnotationOrderAndType resultToAdd = null;
            if (fieldsAnnotatedWith(cls, PivotRow.class).get(0).getName().equals(oa.getId())){
                resultToAdd = new AnnotationOrderAndType("row", 0, null);
            }
            for (Field f : fieldsAnnotatedWith(cls, PivotColumn.class)){
                if (f.getName().equals(oa.getId())){
                    resultToAdd = new AnnotationOrderAndType("column", f.getAnnotation(PivotColumn.class).order(), null);
                }
            }
            for (Field f : fieldsAnnotatedWith(cls, PivotValue.class)){
                if (f.getName().equals(oa.getId())){
                    resultToAdd = new AnnotationOrderAndType("value", f.getAnnotation(PivotValue.class).order(), f.getAnnotation(PivotValue.class).type());
                }
            }
            for (Field f : fieldsAnnotatedWith(cls, PivotDecoration.class)){
                if (f.getName().equals(oa.getId())){
                    resultToAdd = new AnnotationOrderAndType("deco", f.getAnnotation(PivotDecoration.class).order(), null);
                }
            }
            if (resultToAdd==null){
                resultToAdd = new AnnotationOrderAndType("skip", 0, null);
            }
            results.add(resultToAdd);
        }
        return results;
    }

    private List<Field> fieldsAnnotatedWith(final Class<?> cls, final Class<? extends Annotation> annotationCls){
        List<Field> result = new ArrayList<>();
        for (Field f : cls.getDeclaredFields()){
            if (f.isAnnotationPresent(annotationCls)) {
                result.add(f);
            }
        }
        return result;
    }

    private class AnnotationOrderAndType {

        AnnotationOrderAndType(final String annotation, final Integer order, final AggregationType type){
            this.annotation = annotation;
            this.order = order;
            this.type = type;
        }

        String annotation;
        Integer order;
        AggregationType type;

    }

    List<List<?>> fromBytes(
            final List<WorksheetSpec> worksheetSpecs,
            final byte[] bs) throws IOException, InvalidFormatException {

        final List<List<?>> listOfLists = Lists.newArrayList();
        for (WorksheetSpec worksheetSpec : worksheetSpecs) {
            listOfLists.add(fromBytes(bs, worksheetSpec));
        }
        return listOfLists;
    }

    <T> List<T> fromBytes(
            final byte[] bs,
            final WorksheetSpec worksheetSpec) throws IOException, InvalidFormatException {

        try (ByteArrayInputStream bais = new ByteArrayInputStream(bs)) {
            final Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(bais);
            return fromWorkbook(wb, worksheetSpec);
        }
    }

    private <T> List<T> fromWorkbook(
            final Workbook workbook,
            final WorksheetSpec worksheetSpec) {

        final WorksheetSpec.RowFactory<Object> factory = worksheetSpec.getFactory();
        if(factory instanceof ServicesInjectorAware) {
            final ServicesInjectorAware servicesInjectorAware = (ServicesInjectorAware) factory;
            servicesInjectorAware.setServicesInjector(this.servicesInjector);

        }

        final Class<T> cls = (Class<T>) factory.getCls();
        final String sheetName = worksheetSpec.getSheetName();
        final Mode mode = worksheetSpec.getMode();

        final List<T> importedItems = Lists.newArrayList();

        final CellMarshaller cellMarshaller = this.newCellMarshaller(workbook);

        final Sheet sheet = lookupSheet(cls, sheetName, workbook);

        boolean header = true;
        final Map<Integer, Property> propertyByColumn = Maps.newHashMap();

        final ObjectSpecification objectSpec = specificationLoader.loadSpecification(cls);

        T previousRow = null;
        for (final Row row : sheet) {
            if (header) {
                for (final Cell cell : row) {

                    try{
                        if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                            final int columnIndex = cell.getColumnIndex();
                            final String propertyName = cellMarshaller.getStringCellValue(cell);
                            final OneToOneAssociation property = getAssociation(objectSpec, propertyName);
                            if (property != null) {
                                final Class<?> propertyType = property.getSpecification().getCorrespondingClass();
                                propertyByColumn.put(columnIndex, new Property(propertyName, property, propertyType));
                            }
                        }

                    } catch (final Exception e) {
                        switch (mode) {
                        case RELAXED:
                            // ignore
                        default:
                            throw new ExcelService.Exception(String.format("Error processing Excel row nr. %d. Message: %s", row.getRowNum(), e.getMessage()), e);
                        }
                    }

                }
                header = false;
            } else {
                // detail

                // Let's require at least one column to be not null for detecting a blank row.
                // Excel can have physical rows with cells empty that it seem do not existent for the user.
                ObjectAdapter templateAdapter = null;
                T imported = null;
                for (final Cell cell : row) {

                    try {

                        final int columnIndex = cell.getColumnIndex();
                        final Property property = propertyByColumn.get(columnIndex);
                        if (property != null) {
                            final OneToOneAssociation otoa = property.getOneToOneAssociation();
                            final Object value = cellMarshaller.getCellValue(cell, otoa);
                            if (value != null) {
                                if (imported == null) {
                                    // copy the row into a new object
                                    imported = (T) factory.create();
                                    // set excel metadata if applicable
                                    if (ExcelMetaDataEnabled.class.isAssignableFrom(cls)){
                                        ExcelMetaDataEnabled importedEnhanced = (ExcelMetaDataEnabled) imported;
                                        importedEnhanced.setExcelRowNumber(row.getRowNum());
                                        importedEnhanced.setExcelSheetName(sheetName);
                                        imported = (T) importedEnhanced;
                                    }
                                    templateAdapter = this.adapterManager.adapterFor(imported);
                                }
                                final ObjectAdapter valueAdapter = this.adapterManager.adapterFor(value);
                                otoa.set(templateAdapter, valueAdapter, InteractionInitiatedBy.USER);
                            }
                        } else {
                            // not expected; just ignore.
                        }

                    } catch (final Exception e) {
                        switch (mode) {
                        case RELAXED:
                            // ignore
                            break;
                        default:
                            throw new ExcelService.Exception(String.format("Error processing Excel row nr. %d. Message: %s", row.getRowNum(), e.getMessage()), e);

                        }
                    }
                }

                // we need to remove the templateAdapter because earlier on we will have created an adapter (and corresponding OID)
                // for a view model where the OID is initially computed on the incomplete (in fact, empty) view model.
                // removing the adapter therefore removes the OID as well, so next time an adapter is needed for the view model
                // the OID will be recomputed based on the fully populated view model pojo.
                if(templateAdapter != null) {
                    this.adapterManager.removeAdapter(templateAdapter);
                }

                if (imported != null) {
                    importedItems.add(imported);

                    if(imported instanceof RowHandler) {
                        ((RowHandler)imported).handleRow((RowHandler) previousRow);
                    }

                    previousRow = imported;
                }

            }



        }
        return importedItems;
    }

    protected <T> Sheet lookupSheet(final Class<T> cls, final String sheetName, final Workbook workbook) {
        final List<String> sheetNames = determineCandidateSheetNames(sheetName, cls);
        return lookupSheet(workbook, sheetNames);
    }

    private static <T> List<String> determineCandidateSheetNames(final String sheetName, final Class<T> cls) {
        final List<String> names = Lists.newArrayList();
        if(sheetName != null) {
            names.add(sheetName);
        }
        final String simpleName = cls.getSimpleName();
        if(WorksheetSpec.hasSuffix(simpleName)) {
            names.add(WorksheetSpec.prefix(simpleName));
        }
        return names;
    }

    protected Sheet lookupSheet(
            final Workbook wb,
            final List<String> sheetNames) {
        for (String sheetName : sheetNames) {
            final Sheet sheet = wb.getSheet(sheetName);
            if(sheet != null) {
                return sheet;
            }
        }
        throw new IllegalArgumentException(String.format("Could not locate sheet named any of: '%s'", sheetNames));
    }

    private static OneToOneAssociation getAssociation(final ObjectSpecification objectSpec, final String propertyNameOrId) {
        final List<ObjectAssociation> associations = objectSpec.getAssociations(Contributed.INCLUDED);
        for (final ObjectAssociation association : associations) {
            if (association instanceof OneToOneAssociation) {
                if (propertyNameOrId.equalsIgnoreCase(association.getName())) {
                    return (OneToOneAssociation) association;
                }
                if (propertyNameOrId.equalsIgnoreCase(association.getId())) {
                    return (OneToOneAssociation) association;
                }
            }
        }
        return null;
    }

    static class Property {
        private final String name;
        private final Class<?> type;
        private final OneToOneAssociation property;
        private Object currentValue;

        public Property(final String name, final OneToOneAssociation property, final Class<?> type) {
            this.name = name;
            this.property = property;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public OneToOneAssociation getOneToOneAssociation() {
            return property;
        }

        public Class<?> getType() {
            return type;
        }

        public Object getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(final Object currentValue) {
            this.currentValue = currentValue;
        }

        @Override
        public String toString() {
            return ObjectContracts.toString(this, "name,type,currentValue");
        }
    }

    @SuppressWarnings("unused")
    private void autoSize(final Sheet sh, final int numProps) {
        for (int prop = 0; prop < numProps; prop++) {
            sh.autoSizeColumn(prop);
        }
    }

    // //////////////////////////////////////

    protected CellMarshaller newCellMarshaller(final Workbook wb) {
        final CellStyle dateCellStyle = createDateFormatCellStyle(wb);
        final CellStyle defaultCellStyle = defaultCellStyle(wb);
        final CellMarshaller cellMarshaller = new CellMarshaller(bookmarkService, dateCellStyle, defaultCellStyle);
        return cellMarshaller;
    }

    protected CellStyle createDateFormatCellStyle(final Workbook wb) {
        final CreationHelper createHelper = wb.getCreationHelper();
        final short dateFormat = createHelper.createDataFormat().getFormat("yyyy-mm-dd");
        final CellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setDataFormat(dateFormat);
        dateCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        return dateCellStyle;
    }

    protected CellStyle defaultCellStyle(final Workbook wb) {
        final CellStyle defaultCellStyle = wb.createCellStyle();
        defaultCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        return defaultCellStyle;
    }

}
