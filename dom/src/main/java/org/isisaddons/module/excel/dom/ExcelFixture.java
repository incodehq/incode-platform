/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import org.datanucleus.enhancement.Persistable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.util.ExcelServiceImpl;

public class ExcelFixture extends FixtureScript {

    private final List<Class> classes;

    public ExcelFixture(final URL excelResource, final Class... classes) {
        this(excelResource, Arrays.asList(classes));
    }

    public ExcelFixture(final URL excelResource, final List<Class> classes) {
        for (Class cls : classes) {
            final boolean viewModel = ExcelFixtureRowHandler.class.isAssignableFrom(cls);
            final boolean persistable = Persistable.class.isAssignableFrom(cls);
            if(!viewModel && !persistable) {
                throw new IllegalArgumentException( String.format(
                        "Class '%s' does not implement '%s', nor is it persistable",
                        cls.getSimpleName(), ExcelFixtureRowHandler.class.getSimpleName()));
            }
        }
        this.classes = classes;
        setExcelResource(excelResource);
    }

    //region > excelResource (input)
    private URL excelResource;

    @MemberOrder(sequence = "1")
    public URL getExcelResource() {
        return excelResource;
    }

    public void setExcelResource(final URL excelResource) {
        this.excelResource = excelResource;
    }
    //endregion

    //region > objectsByClass (output)
    private final Map<Class, List<Object>> objectsByClass = Maps.newHashMap();

    /**
     * The objects created by this fixture, for a specific class (output).
     */
    public List<?> getObjects(Class<?> cls) {
        return objectsByClass.get(cls);
    }
    //endregion

    //region > objects (output)
    private final List objects = Lists.newArrayList();

    /**
     * The simpleobjects created by this fixture (output).
     */
    public List<?> getObjects() {
        return objects;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        final ExcelServiceImpl excelServiceImpl = new ExcelServiceImpl(container, bookmarkService);

        // defaults
        final URL excelResource = checkParam("excelResource", ec, URL.class);

        // validate
        byte[] bytes;
        try {
            bytes = Resources.toByteArray(excelResource);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read from resource: " + excelResource);
        }

        // execute
        final Blob blob = new Blob("unused", ExcelService.XSLX_MIME_TYPE, bytes);
        for (Class cls : classes) {
            final List rowObjects = excelServiceImpl.fromExcel(
                    blob, cls, ExcelServiceImpl.SheetLookupPolicy.BY_NAME);
            Object previousRow = null;
            for (final Object rowObj : rowObjects) {
                final List<Object> createdObjects = create(rowObj, ec, previousRow);
                if(createdObjects != null) {
                    addToMap(cls, createdObjects);
                    addToCombined(createdObjects);
                }
                previousRow = rowObj;
            }
        }
    }

    private List<Object> create(
            final Object rowObj,
            final ExecutionContext ec,
            final Object previousRow) {
        if(rowObj instanceof ExcelFixtureRowHandler) {
            final ExcelFixtureRowHandler rowHandler = (ExcelFixtureRowHandler) rowObj;
            return rowHandler.handleRow(ec, this, previousRow);
        } else {
            container.persistIfNotAlready(rowObj);
            return Collections.singletonList(rowObj);
        }
    }

    private void addToMap(final Class cls, final List<Object> createdObjects) {
        List<Object> objectList = objectsByClass.get(cls);
        if(objectList == null) {
            objectList = Lists.newArrayList();
            this.objectsByClass.put(cls, objectList);
        }
        objectList.addAll(createdObjects);
    }

    private void addToCombined(final List<Object> createdObjects) {
        this.objects.add(createdObjects);
    }



    @javax.inject.Inject
    private DomainObjectContainer container;
    @javax.inject.Inject
    private BookmarkService bookmarkService;

}
