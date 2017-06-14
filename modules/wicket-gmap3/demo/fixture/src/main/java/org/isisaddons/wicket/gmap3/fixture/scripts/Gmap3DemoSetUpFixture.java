package org.isisaddons.wicket.gmap3.fixture.scripts;

import org.isisaddons.wicket.gmap3.fixture.scripts.todo.Gmap3ToDoItemsFixture;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class Gmap3DemoSetUpFixture extends DiscoverableFixtureScript {

    private final String ownedBy;

    public Gmap3DemoSetUpFixture() {
        this(null);
    }

    public Gmap3DemoSetUpFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        execute(new Gmap3DemoTearDownFixture(ownedBy), executionContext);
        execute(new Gmap3ToDoItemsFixture(), executionContext);
    }

}
