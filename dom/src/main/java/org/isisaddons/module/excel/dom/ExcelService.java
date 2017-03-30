/*
 *  Copyright 2014-2016 Dan Haywood
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
package org.isisaddons.module.excel.dom;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.Lists;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.RecoverableException;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.util.ExcelServiceImpl;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ExcelService {

    public static class Exception extends RecoverableException {

        private static final long serialVersionUID = 1L;

        public Exception(final String msg, final Throwable ex) {
            super(msg, ex);
        }

        public Exception(final Throwable ex) {
            super(ex);
        }
    }

    public static final String XSLX_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private ExcelServiceImpl excelServiceImpl;

    public ExcelService() {
    }

    @Programmatic
    @PostConstruct
    public void init(final Map<String,String> properties) {
        excelServiceImpl = new ExcelServiceImpl();
        serviceRegistry.injectServicesInto(excelServiceImpl);
    }

    // //////////////////////////////////////

    /**
     * Creates a Blob holding a spreadsheet of the domain objects.
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
    public <T> Blob toExcel(
            final List<T> domainObjects,
            final Class<T> cls,
            final String sheetName,
            final String fileName) throws ExcelService.Exception {
        return excelServiceImpl.toExcel(domainObjects, cls, sheetName, fileName);
    }

    @Programmatic
    public <T> Blob toExcel(
            final WorksheetContent worksheetContent,
            final String fileName) throws ExcelService.Exception {
        return excelServiceImpl.toExcel(worksheetContent, fileName);
    }

    @Programmatic
    public Blob toExcel(
            final List<WorksheetContent> worksheetContents,
            final String fileName) throws ExcelService.Exception {

        return excelServiceImpl.toExcel(worksheetContents, fileName);
    }

    @Programmatic
    public <T> Blob toExcelPivot(
            final List<T> domainObjects,
            final Class<T> cls,
            final String fileName) throws ExcelService.Exception {
        return excelServiceImpl.toExcelPivot(domainObjects, cls, fileName);
    }

    public <T> Blob toExcelPivot(
            final List<T> domainObjects,
            final Class<T> cls,
            final String sheetName,
            final String fileName) throws ExcelService.Exception {
        return excelServiceImpl.toExcelPivot(domainObjects, cls, sheetName, fileName);
    }

    @Programmatic
    public <T> Blob toExcelPivot(
            final WorksheetContent worksheetContent,
            final String fileName) throws ExcelService.Exception {
        return excelServiceImpl.toExcelPivot(worksheetContent, fileName);
    }

    @Programmatic
    public Blob toExcelPivot(
            final List<WorksheetContent> worksheetContents,
            final String fileName) throws ExcelService.Exception {

        return excelServiceImpl.toExcelPivot(worksheetContents, fileName);
    }

    /**
     * Returns a list of objects for each line in the spreadsheet, of the specified type.
     *
     * <p>
     *     If the class is a view model then the objects will be properly instantiated (that is, using
     *     {@link DomainObjectContainer#newViewModelInstance(Class, String)}, with the correct
     *     view model memento); otherwise the objects will be simple transient objects (that is, using
     *     {@link DomainObjectContainer#newTransientInstance(Class)}).
     * </p>
     */
    @Programmatic
    public <T> List<T> fromExcel(
            final Blob excelBlob,
            final Class<T> cls,
            final String sheetName) throws ExcelService.Exception {
        return excelServiceImpl.fromExcel(excelBlob, cls, sheetName);
    }

    @Programmatic
    public <T> List<T> fromExcel(
            final Blob excelBlob,
            final WorksheetSpec worksheetSpec) throws ExcelService.Exception {
        return excelServiceImpl.fromExcel(excelBlob, worksheetSpec);
    }

    @Programmatic
    public List<List<?>> fromExcel(
            final Blob excelBlob,
            final List<WorksheetSpec> worksheetSpecs) throws ExcelService.Exception {
        return excelServiceImpl.fromExcel(excelBlob, worksheetSpecs);
    }

    @Programmatic
    public List<List<?>> fromExcel(
            final Blob excelBlob,
            final WorksheetSpec.Factory factory) throws ExcelService.Exception {

        return fromExcel(excelBlob, factory, null);
    }

    @Programmatic
    public List<List<?>> fromExcel(
            final Blob excelBlob,
            final WorksheetSpec.Factory factory,
            final WorksheetSpec.Sequencer sequencer) throws ExcelService.Exception {

        List<WorksheetSpec> worksheetSpecs = Lists.newArrayList();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(excelBlob.getBytes())) {
            final Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(bais);
            final int numberOfSheets = wb.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                final Sheet sheet = wb.getSheetAt(i);
                WorksheetSpec worksheetSpec = factory.fromSheet(sheet.getSheetName());
                if(worksheetSpec != null) {
                    worksheetSpecs.add(worksheetSpec);
                }
            }
        } catch (InvalidFormatException | IOException e) {
            throw new ExcelService.Exception(e);
        }

        if(sequencer != null) {
            worksheetSpecs = sequencer.sequence(worksheetSpecs);
        }

        return fromExcel(excelBlob, worksheetSpecs);
    }

    @javax.inject.Inject
    private DomainObjectContainer container;
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    @javax.inject.Inject
    private ServiceRegistry serviceRegistry;

}
