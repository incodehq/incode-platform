package org.isisaddons.module.excel.fixture.scripts;

import java.net.URL;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

import org.isisaddons.module.excel.dom.ExcelFixture;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;

public class CreateAllToDoItems extends FixtureScript {

    private final String user;

    public CreateAllToDoItems() {
        this(null);
    }

    public CreateAllToDoItems(String ownedBy) {
        this.user = ownedBy;
    }


    private List<ExcelModuleDemoToDoItem> todoItems = Lists.newArrayList();

    /**
     * output
     */
    public List<ExcelModuleDemoToDoItem> getToDoItems() {
        return todoItems;
    }

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null ? this.user : getContainer().getUser().getName();

        installFor(ownedBy, executionContext);

        getContainer().flush();
    }

    private void installFor(String user, ExecutionContext ec) {

        ec.setParameter("user", user);

        this.todoItems.addAll(load(ec, "ToDoItems.xlsx"));
        this.todoItems.addAll(load(ec, "MoreToDoItems.xlsx"));
        this.todoItems.addAll(load(ec, "ToDoItems.xlsx")); // should be ignored because of execution strategy

        getContainer().flush();
    }

    private List<ExcelModuleDemoToDoItem> load(
            final ExecutionContext executionContext,
            final String resourceName) {
        final URL excelResource = Resources.getResource(getClass(), resourceName);
        final ExcelFixture excelFixture = new ExcelFixture(excelResource, ExcelModuleDemoToDoItemRowHandler.class);
        excelFixture.setExcelResourceName(resourceName);
        executionContext.executeChild(this, excelFixture);

        return (List<ExcelModuleDemoToDoItem>) excelFixture.getObjects();
    }

    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
