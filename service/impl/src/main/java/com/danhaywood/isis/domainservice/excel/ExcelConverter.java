/*
 *  Copyright 2013 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.danhaywood.isis.domainservice.excel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.ViewModel;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.filter.Filter;
import org.apache.isis.applib.filter.Filters;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.adapter.mgr.AdapterManager;
import org.apache.isis.core.metamodel.adapter.oid.RootOid;
import org.apache.isis.core.metamodel.facets.object.viewmodel.ViewModelFacet;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.SpecificationLoader;
import org.apache.isis.core.metamodel.spec.feature.Contributed;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.core.metamodel.spec.feature.OneToOneAssociation;

public class ExcelConverter {

    private static final String XLSX_SUFFIX = ".xlsx";
    @SuppressWarnings("unchecked")
    private static final Filter<ObjectAssociation> VISIBLE_PROPERTIES = Filters.and(
            ObjectAssociation.Filters.PROPERTIES, 
            ObjectAssociation.Filters.staticallyVisible(Where.STANDALONE_TABLES));

    static class RowFactory {
        private final Sheet sheet;
        private int rowNum;

        RowFactory(Sheet sheet) {
            this.sheet = sheet;
        }

        public Row newRow() {
            return sheet.createRow((short) rowNum++);
        }
    }

    // //////////////////////////////////////


    private final SpecificationLoader specificationLoader;
    private final AdapterManager adapterManager;

    public ExcelConverter(
            final SpecificationLoader specificationLoader,
            final AdapterManager adapterManager) {
        this.specificationLoader = specificationLoader;
        this.adapterManager = adapterManager;
    }

    // //////////////////////////////////////

    public <T> File toFile(final Class<T> cls, final List<T> domainObjects) throws IOException {

        final ObjectSpecification objectSpec = specificationLoader.loadSpecification(cls);
        final ViewModelFacet viewModelFacet = objectSpec.getFacet(ViewModelFacet.class);
        
        final List<ObjectAdapter> adapters = Lists.transform(domainObjects, ObjectAdapter.Functions.adapterForUsing(adapterManager));
        
        @SuppressWarnings("deprecation")
        final List<? extends ObjectAssociation> propertyList = objectSpec.getAssociations(VISIBLE_PROPERTIES);
        
        final Workbook wb = new XSSFWorkbook();
        final String sheetName =  cls.getSimpleName();
        final File tempFile = File.createTempFile(ExcelConverter.class.getName(), sheetName + XLSX_SUFFIX);

        final FileOutputStream fos = new FileOutputStream(tempFile);
        final Sheet sheet = wb.createSheet(sheetName);
        
        final ExcelConverter.RowFactory rowFactory = new RowFactory(sheet);
        final Row headerRow = rowFactory.newRow();
        
        // header row
        int i=0;
        for (ObjectAssociation property : propertyList) {
            final Cell cell = headerRow.createCell((short) i++);
            cell.setCellValue(property.getName());
        }
        if(viewModelFacet != null) {
            final Cell cell = headerRow.createCell((short) i++);
            cell.setCellValue("viewModelMemento");
        }
        
        final CellMarshaller cellMarshaller = newCellMarshaller(wb);
        
        // detail rows
        for (final ObjectAdapter objectAdapter : adapters) {
            final Row detailRow = rowFactory.newRow();
            i=0;
            for (final ObjectAssociation property : propertyList) {
                final Cell cell = detailRow.createCell((short) i++);
                cellMarshaller.setCellValue(objectAdapter, property, cell);
            }
            if(viewModelFacet != null) {
                final Cell cell = detailRow.createCell((short) i++);
                RootOid oid = (RootOid) objectAdapter.getOid();
                cell.setCellValue(oid.asBookmark().getIdentifier());
            }
        }
        
        // freeze panes
        sheet.createFreezePane(0, 1);
        
        wb.write(fos);
        fos.close();
        return tempFile;
    }

    public <T extends ViewModel> List<T> fromBytes(
            final Class<T> cls,
            final byte[] bs, 
            final DomainObjectContainer container) throws IOException, InvalidFormatException {

        final List<T> viewModels = Lists.newArrayList();
        
        final ObjectSpecification objectSpec = specificationLoader.loadSpecification(cls);
        final ViewModelFacet viewModelFacet = objectSpec.getFacet(ViewModelFacet.class);

        if(viewModelFacet == null) {
            throw new IllegalArgumentException("Class '" + objectSpec.getCssClass() + "' is not a view model");
        }
        
        final ByteArrayInputStream bais = new ByteArrayInputStream(bs);
        final Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(bais);
        final CellMarshaller cellMarshaller = newCellMarshaller(wb);

        final Sheet sheet = wb.getSheetAt(0);
        
        boolean header = true;
        final Map<Integer, Property> propertyByColumn = Maps.newHashMap();
        for(final Row row: sheet) {
            if(header) {
                for(final Cell cell: row) {
                    int columnIndex = cell.getColumnIndex();
                    final String propertyName = cellMarshaller.getCellValue(cell, String.class);
                    if(!"viewModelMemento".equals(propertyName)) {
                        final OneToOneAssociation property = getAssociation(objectSpec, propertyName);
                        if(property != null) {
                            final Class<?> propertyType = property.getSpecification().getCorrespondingClass();
                            propertyByColumn.put(columnIndex, new Property(propertyName, property, propertyType));
                        }
                    }
                }
                header = false;
            } else {
                // detail
                String identifier = null;
                for(final Cell cell: row) {
                    cell.getColumnIndex();
                    int columnIndex = cell.getColumnIndex();
                    final Property property = propertyByColumn.get(columnIndex);
                    if(property != null) {
                        final Object cellValue = cellMarshaller.getCellValue(cell, property.getType());
                        if(cellValue != null) {
                            property.setCurrentValue(cellValue);
                        }
                    } else {
                        identifier = cellMarshaller.getCellValue(cell, String.class);
                    }
                }
                final T viewModel = container.newViewModelInstance(cls, identifier);
                final ObjectAdapter viewModelAdapter = adapterManager.adapterFor(viewModel);
                for(final Property property: propertyByColumn.values()) {
                    final Object value = property.getCurrentValue();
                    if(value != null) {
                        final ObjectAdapter valueAdapter = adapterManager.adapterFor(value);
                        property.getOneToOneAssociation().set(viewModelAdapter, valueAdapter);
                    }
                }
                viewModels.add(viewModel);
            }
        }
        
        return viewModels;
    }

    private OneToOneAssociation getAssociation(final ObjectSpecification objectSpec, final String propertyName) {
        final List<ObjectAssociation> associations = objectSpec.getAssociations(Contributed.INCLUDED);
        for (final ObjectAssociation association : associations) {
            if(propertyName.equals(association.getName()) && association instanceof OneToOneAssociation) {
                return (OneToOneAssociation)association;
            }
        }
        return null;
    }

    static class Property {
        private final String name;
        private final Class<?> type;
        private final OneToOneAssociation property;
        private Object currentValue;
        
        public Property(String name, OneToOneAssociation property, Class<?> type) {
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
        public void setCurrentValue(Object currentValue) {
            this.currentValue = currentValue;
        }
    }
    
    @SuppressWarnings("unused")
    private void autoSize(final Sheet sh, int numProps) {
        for(int prop=0; prop<numProps; prop++) {
            sh.autoSizeColumn(prop);
        }
    }

    // //////////////////////////////////////

    
    protected CellMarshaller newCellMarshaller(final Workbook wb) {
        final CellStyle dateCellStyle = createDateFormatCellStyle(wb);
        final CellMarshaller cellMarshaller = new CellMarshaller(dateCellStyle);
        return cellMarshaller;
    }
    
    protected CellStyle createDateFormatCellStyle(final Workbook wb) {
        final CreationHelper createHelper = wb.getCreationHelper();
        final SimpleDateFormat dateInstance = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM);
        final String pattern = dateInstance.toPattern();
        short dateFormat = createHelper.createDataFormat().getFormat(pattern);
        final CellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setDataFormat(dateFormat);
        return dateCellStyle;
    }


}