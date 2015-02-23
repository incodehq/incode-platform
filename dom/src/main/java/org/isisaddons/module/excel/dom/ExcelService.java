/*
 *  Copyright 2014 Dan Haywood
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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.RecoverableException;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.core.metamodel.adapter.mgr.AdapterManager;
import org.apache.isis.core.metamodel.services.ServicesInjectorSpi;
import org.apache.isis.core.metamodel.spec.SpecificationLoaderSpi;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ExcelService {

    public static final String XSLX_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static class Exception extends RecoverableException {

        private static final long serialVersionUID = 1L;

        public Exception(final String msg, final Throwable ex) {
            super(msg, ex);
        }

        public Exception(final Throwable ex) {
            super(ex);
        }
    }


    // //////////////////////////////////////

    private final ExcelFileBlobConverter excelFileBlobConverter;
    private BookmarkService bookmarkService;
    
    public ExcelService() {
        excelFileBlobConverter = new ExcelFileBlobConverter();
    }

    // //////////////////////////////////////

    @Programmatic
    @PostConstruct
    public void init(final Map<String,String> properties) {
        bookmarkService = getServicesInjector().lookupService(BookmarkService.class);
    }

    // //////////////////////////////////////

    /**
     * Creates a Blob holding a spreadsheet of the domain objects.
     *
     * <p>
     *     There are no specific restrictions on the domain objects; they can be either persistable entities or
     *     view models.  Do be aware though that if imported back using {@link #fromExcel(org.apache.isis.applib.value.Blob, Class)},
     *     then new instances are always created.  It is generally better therefore to work with view models than to
     *     work with entities.  This also makes it easier to maintain backward compatibility in the future if the
     *     persistence model changes; using view models represents a stable API for import/export.
     * </p>
     */
    @Programmatic
    public <T> Blob toExcel(
            final List<T> domainObjects, 
            final Class<T> cls, 
            final String fileName) throws ExcelService.Exception {
        try {
            final File file = newExcelConverter().toFile(cls, domainObjects);
            return excelFileBlobConverter.toBlob(fileName, file);
        } catch (final IOException ex) {
            throw new ExcelService.Exception(ex);
        }
    }

    /**
     * Returns a list of objects for each line in the spreadsheet, of the specified type.
     *
     * <p>
     *     If the class is a view model then the objects will be properly instantiated (that is, using
     *     {@link org.apache.isis.applib.DomainObjectContainer#newViewModelInstance(Class, String)}, with the correct
     *     view model memento); otherwise the objects will be simple transient objects (that is, using
     *     {@link org.apache.isis.applib.DomainObjectContainer#newTransientInstance(Class)}).
     * </p>
     */
    @Programmatic
    public <T> List<T> fromExcel(
            final Blob excelBlob, 
            final Class<T> cls) throws ExcelService.Exception {
        try {
            return newExcelConverter().fromBytes(cls, excelBlob.getBytes(), container);
        } catch (final IOException | InvalidFormatException e) {
            throw new ExcelService.Exception(e);
        }
    }

    private ExcelConverter newExcelConverter() {
        return new ExcelConverter(getSpecificationLoader(), getAdapterManager(), getBookmarkService());
    }


    // //////////////////////////////////////

    private SpecificationLoaderSpi getSpecificationLoader() {
        return IsisContext.getSpecificationLoader();
    }
    
    private AdapterManager getAdapterManager() {
        return getPersistenceSession().getAdapterManager();
    }

    private ServicesInjectorSpi getServicesInjector() {
        return getPersistenceSession().getServicesInjector();
    }

    private PersistenceSession getPersistenceSession() {
        return IsisContext.getPersistenceSession();
    }

    private BookmarkService getBookmarkService() {
        return bookmarkService;
    }

    // //////////////////////////////////////
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
