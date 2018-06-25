package org.incode.domainapp.extended.integtests.lib.excel.tests;

import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import com.google.common.io.Resources;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.util.ExcelFileBlobConverter;

import org.incode.domainapp.extended.integtests.lib.excel.ExcelModuleIntegTestAbstract;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.dom.upload.ExcelUploadServiceForDemoToDoItem;
import org.incode.domainapp.extended.module.fixtures.shared.todo.dom.DemoToDoItem;
import org.incode.domainapp.extended.module.fixtures.shared.todo.dom.DemoToDoItemMenu;
import org.incode.domainapp.extended.module.fixtures.shared.todo.fixture.DemoToDoItem_tearDown;

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
