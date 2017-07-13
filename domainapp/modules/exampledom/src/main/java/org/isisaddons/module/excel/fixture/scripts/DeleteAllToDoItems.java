package org.isisaddons.module.excel.fixture.scripts;

import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DeleteAllToDoItems extends DiscoverableFixtureScript {

    private final String user;

    public DeleteAllToDoItems() {
        this(null);
    }

    public DeleteAllToDoItems(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null ? this.user : getContainer().getUser().getName();

        isisJdoSupport.executeUpdate(String.format("delete from \"%s\" where \"ownedBy\" = '%s'", ExcelModuleDemoToDoItem.class.getSimpleName(), ownedBy));
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
