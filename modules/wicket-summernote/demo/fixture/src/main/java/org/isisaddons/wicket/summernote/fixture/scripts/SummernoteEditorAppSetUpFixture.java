package org.isisaddons.wicket.summernote.fixture.scripts;

import org.isisaddons.wicket.summernote.fixture.scripts.todo.SummernoteEditorToDoItemsFixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class SummernoteEditorAppSetUpFixture extends DiscoverableFixtureScript {

    private final String user;

    public SummernoteEditorAppSetUpFixture() {
        this(null);
    }

    public SummernoteEditorAppSetUpFixture(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null? this.user : getContainer().getUser().getName();

        // prereqs
        execute(new SummernoteEditorAppTearDownFixture(ownedBy), executionContext);

        // create
        execute(new SummernoteEditorToDoItemsFixture(), executionContext);
    }

}
