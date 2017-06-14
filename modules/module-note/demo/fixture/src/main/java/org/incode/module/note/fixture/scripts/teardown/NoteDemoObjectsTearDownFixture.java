package org.incode.module.note.fixture.scripts.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class NoteDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"incodeNoteDemo\".\"NotableLinkForDemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"incodeNoteDemo\".\"NoteDemoObject\"");

        isisJdoSupport.executeUpdate("delete from \"incodeNote\".\"NotableLink\"");
        isisJdoSupport.executeUpdate("delete from \"incodeNote\".\"Note\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
