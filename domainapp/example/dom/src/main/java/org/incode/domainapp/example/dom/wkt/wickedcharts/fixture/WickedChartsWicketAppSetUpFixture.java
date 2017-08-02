package org.incode.domainapp.example.dom.wkt.wickedcharts.fixture;

import org.incode.domainapp.example.dom.wkt.wickedcharts.fixture.data.WickedChartsWicketToDoItemsFixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class WickedChartsWicketAppSetUpFixture extends DiscoverableFixtureScript {

    private final String user;

    public WickedChartsWicketAppSetUpFixture() {
        this(null);
    }

    public WickedChartsWicketAppSetUpFixture(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null? this.user : getContainer().getUser().getName();

        // prereqs
        execute(new WickedChartsWicketAppTearDownFixture(ownedBy), executionContext);

        // create
        execute(new WickedChartsWicketToDoItemsFixture(), executionContext);
    }

}
