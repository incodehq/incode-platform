package org.isisaddons.module.excel.dom.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.core.metamodel.specloader.SpecificationLoader;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;
import org.apache.isis.core.runtime.system.session.IsisSessionFactory;

import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.module.excel.dom.PivotColumn;
import org.isisaddons.module.excel.dom.PivotRow;
import org.isisaddons.module.excel.dom.PivotValue;
import org.isisaddons.module.excel.dom.WorksheetContent;
import org.isisaddons.module.excel.dom.WorksheetSpec;

public class ExcelServiceImpl {

    private final ExcelFileBlobConverter excelFileBlobConverter = new ExcelFileBlobConverter();


    /**
     * Creates a Blob holding a single-sheet spreadsheet of the domain objects.
     *
     * <p>
     *     There are no specific restrictions on the domain objects; they can be either persistable entities or
     *     view models.  Do be aware though that if imported back using {@link #fromExcel(Blob, Class, String)},
     *     then new instances are always created.  It is generally better therefore to work with view models than to
     *     work with entities.  This also makes it easier to maintain backward compatibility in the future if the
     *     persistence model changes; using view models represents a stable API for import/export.
     * </p>
     *
     * @param sheetName - must be 31 chars or less
     */
    @Programmatic
    public <T> Blob toExcel(
            final List<T> domainObjects,
            final Class<T> cls,
            final String sheetName,
            final String fileName) {
        return toExcel(new WorksheetContent(domainObjects, new WorksheetSpec(cls, sheetName)), fileName);
    }
    
    /**
     * As {@link #toExcel(List, Class, String, String)}, but appends a single-sheet spreadsheet of the domain objects to 
     * an existing workbook instead of creating one.  
     *
     * @param sheetName - must be 31 chars or less
     * @param in - an existing excel workbook to which this sheet will be appended
     */
    @Programmatic
    public <T> Blob toExcel(
            final List<T> domainObjects,
            final Class<T> cls,
            final String sheetName,
            final String fileName,
            final InputStream in) {
        return toExcel(new WorksheetContent(domainObjects, new WorksheetSpec(cls, sheetName)), fileName, in);
    }

    /**
     * As {@link #toExcel(List, Class, String, String)}, but with the domain objects, class and sheet name provided using a
     * {@link WorksheetContent}.
     */
    @Programmatic
    public <T> Blob toExcel(WorksheetContent worksheetContent, final String fileName) {
        return toExcel(Collections.singletonList(worksheetContent), fileName);
    }
    
    /**
     * As {@link #toExcel(List, Class, String, String)}, but with the domain objects, class and sheet name provided using a
     * {@link WorksheetContent} and with an input stream.
     */
    @Programmatic
    public <T> Blob toExcel(WorksheetContent worksheetContent, final String fileName, final InputStream in) {
        return toExcel(Collections.singletonList(worksheetContent), fileName, in);
    }

    /**
     * As {@link #toExcel(WorksheetContent, String)}, but with multiple sheets.
     */
    @Programmatic
    public Blob toExcel(final List<WorksheetContent> worksheetContents, final String fileName) {
        try {
            final File file = newExcelConverter().appendSheet(worksheetContents, new XSSFWorkbook());
            return excelFileBlobConverter.toBlob(fileName, file);
        } catch (final IOException ex) {
            throw new ExcelService.Exception(ex);
        }
    }
    
    /**
     * As {@link #toExcel(WorksheetContent, String)}, but with multiple sheets and an input stream.
     */
    @Programmatic
    public Blob toExcel(final List<WorksheetContent> worksheetContents, final String fileName, final InputStream in) {
        try {
            final File file = newExcelConverter().appendSheet(worksheetContents, new XSSFWorkbook(in));
            return excelFileBlobConverter.toBlob(fileName, file);
        } catch (final IOException ex) {
            throw new ExcelService.Exception(ex);
        }
    }

    /**
     * Creates a Blob holding a single-sheet spreadsheet with a pivot of the domain objects. The sheet name is derived from the
     * class name.
     *
     * <p>
     *     Minimal requirements for the domain object are:
     * </p>
     * <ul>
     *     <li>
     *         One property has annotation {@link PivotRow} and will be used as row identifier in left column of pivot.
     *         Empty values are supported.
     *     </li>
     *     <li>
     *         At least one property has annotation {@link PivotColumn}. Its values will be used in columns of pivot.
     *         Empty values are supported.
     *     </li>
     *     <li>
     *         At least one property has annotation {@link PivotValue}. Its values will be distributed in the pivot.
     *     </li>
     * </ul>
     */
    @Programmatic
    public <T> Blob toExcelPivot(
            final List<T> domainObjects,
            final Class<T> cls,
            final String fileName) throws ExcelService.Exception {
        return toExcelPivot(domainObjects, cls, null, fileName);
    }

    @Programmatic
    public <T> Blob toExcelPivot(
            final List<T> domainObjects,
            final Class<T> cls,
            final String sheetName,
            final String fileName) {
        return toExcelPivot(new WorksheetContent(domainObjects, new WorksheetSpec(cls, sheetName)), fileName);
    }

    @Programmatic
    public <T> Blob toExcelPivot(WorksheetContent worksheetContent, final String fileName) {
        return toExcelPivot(Collections.singletonList(worksheetContent), fileName);
    }

    @Programmatic
    public <T> Blob toExcelPivot(final List<WorksheetContent> worksheetContents, final String fileName) {
        try {
            final File file = newExcelConverter().appendPivotSheet(worksheetContents);
            return excelFileBlobConverter.toBlob(fileName, file);
        } catch (final IOException ex) {
            throw new ExcelService.Exception(ex);
        }
    }


    // //////////////////////////////////////


    /**
     * Returns a list of objects for each line in the spreadsheet, of the specified type.  The objects are read
     * from a sheet taken as the simple name of the class.
     *
     * <p>
     *     If the class is a view model then the objects will be properly instantiated (that is, using
     *     {@link org.apache.isis.applib.DomainObjectContainer#newViewModelInstance(Class, String)}, with the correct
     *     view model memento); otherwise the objects will be simple transient objects (that is, using
     *     {@link org.apache.isis.applib.DomainObjectContainer#newTransientInstance(Class)}).
     * </p>
     *  @param sheetName - must be 30 characters or less
     *
     */
    @Programmatic
    public <T> List<T> fromExcel(
            final Blob excelBlob,
            final Class<T> cls,
            final String sheetName) throws ExcelService.Exception {
        final WorksheetSpec worksheetSpec = new WorksheetSpec(cls, sheetName);
        return fromExcel(excelBlob, worksheetSpec);
    }

    /**
     * As {@link #fromExcel(Blob, Class, String)}, but specifying the class name and sheet name by way of a
     * {@link WorksheetSpec}.
     */
    @Programmatic
    public <T> List<T> fromExcel(
            final Blob excelBlob,
            final WorksheetSpec worksheetSpec) throws ExcelService.Exception {
        final List<List<?>> listOfList =
                fromExcel(excelBlob, Collections.singletonList(worksheetSpec));
        return (List<T>) listOfList.get(0);
    }

    /**
     * As {@link #fromExcel(Blob, WorksheetSpec)}, but reading multiple sheets (and returning a list of lists of
     * domain objects).
     */
    @Programmatic
    public List<List<?>> fromExcel(
            final Blob excelBlob,
            final List<WorksheetSpec> worksheetSpecs) throws ExcelService.Exception {
        try {
            return newExcelConverter().fromBytes(worksheetSpecs, excelBlob.getBytes());
        } catch (final IOException | InvalidFormatException e) {
            throw new ExcelService.Exception(e);
        }
    }

    private ExcelConverter newExcelConverter() {
        return new ExcelConverter(getSpecificationLoader(), getPersistenceSession(), bookmarkService, isisSessionFactory.getServicesInjector());
    }


    // //////////////////////////////////////


    @javax.inject.Inject
    BookmarkService bookmarkService;

    @javax.inject.Inject
    IsisSessionFactory isisSessionFactory;

    private SpecificationLoader getSpecificationLoader() {
        return isisSessionFactory.getSpecificationLoader();
    }

    private PersistenceSession getPersistenceSession() {
        return isisSessionFactory.getCurrentSession().getPersistenceSession();
    }

}
