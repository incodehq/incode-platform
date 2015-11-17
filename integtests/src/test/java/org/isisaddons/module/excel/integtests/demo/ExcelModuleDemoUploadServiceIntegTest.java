/*
 *  Copyright 2015 Jeroen van der Wal
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
package org.isisaddons.module.excel.integtests.demo;

import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import com.google.common.io.Resources;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.util.ExcelFileBlobConverter;
import org.isisaddons.module.excel.fixture.app.ExcelModuleDemoUploadService;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItems;
import org.isisaddons.module.excel.fixture.scripts.DeleteAllToDoItems;
import org.isisaddons.module.excel.integtests.ExcelModuleModuleIntegTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExcelModuleDemoUploadServiceIntegTest extends ExcelModuleModuleIntegTest{

    @Inject
    private ExcelModuleDemoUploadService uploadService;

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new DeleteAllToDoItems());
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void uploadSpreadsheet() throws Exception{

        // Given
        final URL excelResource = Resources.getResource(getClass(), "ToDoItemsWithMultipleSheets.xlsx");
        final Blob blob = new ExcelFileBlobConverter().toBlob("unused", excelResource);

        // When
        uploadService.uploadSpreadsheet(blob, null);

        // Then
        final List<ExcelModuleDemoToDoItem> all = toDoItems.allInstances();

        assertThat(all.size(), is(8));
    }

    @Inject
    private ExcelModuleDemoToDoItems toDoItems;

    @Inject
    DomainObjectContainer container;


}
