package org.isisaddons.module.excel.integtests.tests;

import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import com.google.common.io.Resources;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.util.ExcelFileBlobConverter;
import org.isisaddons.module.excel.integtests.ExcelModuleIntegTestAbstract;
import org.isisaddons.module.excel.fixture.demoapp.demomodule.fixturehandlers.excelupload.ExcelUploadServiceForDemoToDoItem;
import org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.DemoToDoItem;
import org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.DemoToDoItemMenu;
import org.isisaddons.module.excel.fixture.demoapp.todomodule.fixturescripts.DemoToDoItem_tearDown;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExcelModuleDemoUploadService_IntegTest extends ExcelModuleIntegTestAbstract {

    @Inject
    private ExcelUploadServiceForDemoToDoItem uploadService;

    @Before
    public void setUpData() throws Exception {
        runFixtureScript(new DemoToDoItem_tearDown());
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
        final List<DemoToDoItem> all = toDoItems.allInstances();

        assertThat(all.size(), is(8));
    }

    @Inject
    private DemoToDoItemMenu toDoItems;

    @Inject
    DomainObjectContainer container;


}
