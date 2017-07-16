package domainapp.modules.exampledom.lib.excel.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import domainapp.modules.exampledom.lib.excel.fixture.data.CreateAllToDoItems;
import domainapp.modules.exampledom.lib.excel.fixture.data.DeleteAllToDoItems;

public class RecreateToDoItems extends DiscoverableFixtureScript {

    private final String user;

    public RecreateToDoItems() {
        this(null);
    }

    public RecreateToDoItems(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null ? this.user : getContainer().getUser().getName();

        executionContext.executeChild(this, new DeleteAllToDoItems(ownedBy));
        executionContext.executeChild(this, new CreateAllToDoItems(ownedBy));

        getContainer().flush();
    }

}
