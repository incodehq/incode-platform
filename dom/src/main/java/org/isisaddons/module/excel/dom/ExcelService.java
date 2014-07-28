/*
 *  Copyright 2013~2014 Dan Haywood
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
import org.apache.isis.applib.ViewModel;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.core.metamodel.adapter.mgr.AdapterManager;
import org.apache.isis.core.metamodel.services.ServicesInjectorSpi;
import org.apache.isis.core.metamodel.spec.SpecificationLoaderSpi;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;

@DomainService
public class ExcelService {

    public static class Exception extends RuntimeException {

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
    public void init(Map<String,String> properties) {
        bookmarkService = getServicesInjector().lookupService(BookmarkService.class);
    }

    // //////////////////////////////////////

    @Programmatic
    public <T> Blob toExcel(
            final List<T> domainObjects, 
            final Class<T> cls, 
            final String fileName) throws ExcelService.Exception {
        try {
            File file = newExcelConverter().toFile(cls, domainObjects);
            return excelFileBlobConverter.toBlob(fileName, file);
        } catch (IOException ex) {
            throw new ExcelService.Exception(ex);
        }
    }

    @Programmatic
    public <T extends ViewModel> List<T> fromExcel(
            final Blob excelBlob, 
            final Class<T> cls) throws ExcelService.Exception {
        try {
            return newExcelConverter().fromBytes(cls, excelBlob.getBytes(), container);
        } catch (IOException e) {
            throw new ExcelService.Exception(e);
        } catch (InvalidFormatException e) {
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
