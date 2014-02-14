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
package com.danhaywood.isis.domainservice.excel.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.danhaywood.isis.domainservice.excel.applib.ExcelService;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.ViewModel;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.core.metamodel.adapter.mgr.AdapterManager;
import org.apache.isis.core.metamodel.spec.SpecificationLoaderSpi;
import org.apache.isis.core.runtime.system.context.IsisContext;

public class ExcelServiceImpl implements ExcelService {

    private final ExcelFileBlobConverter excelFileBlobConverter;

    public ExcelServiceImpl() {
        excelFileBlobConverter = new ExcelFileBlobConverter();
    }

    // //////////////////////////////////////

    public <T> Blob toExcel(
            final List<T> domainObjects, 
            final Class<T> cls, 
            final String fileName) throws ExcelService.Exception {
        final ExcelConverter excelConverter = new ExcelConverter(getSpecificationLoader(), getAdapterManager());
        try {
            File file = excelConverter.toFile(cls, domainObjects);
            return excelFileBlobConverter.toBlob(fileName, file);
        } catch (IOException ex) {
            throw new ExcelService.Exception(ex);
        }
    }

    @Override
    public <T extends ViewModel> List<T> fromExcel(
            final Blob excelBlob, 
            final Class<T> cls) throws ExcelService.Exception {
        final ExcelConverter excelConverter = new ExcelConverter(getSpecificationLoader(), getAdapterManager());
        try {
            return excelConverter.fromBytes(cls, excelBlob.getBytes(), container);
        } catch (IOException e) {
            throw new ExcelService.Exception(e);
        } catch (InvalidFormatException e) {
            throw new ExcelService.Exception(e);
        }
    }

    // //////////////////////////////////////

    private SpecificationLoaderSpi getSpecificationLoader() {
        return IsisContext.getSpecificationLoader();
    }
    
    private AdapterManager getAdapterManager() {
        return IsisContext.getPersistenceSession().getAdapterManager();
    }
    
    // //////////////////////////////////////
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
