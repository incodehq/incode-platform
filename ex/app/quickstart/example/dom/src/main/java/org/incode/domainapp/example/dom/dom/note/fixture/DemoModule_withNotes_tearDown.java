package org.incode.domainapp.example.dom.dom.note.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;

public class DemoModule_withNotes_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // links to demo objects
        isisJdoSupport.executeUpdate("delete from \"exampleDomNote\".\"NotableLinkForDemoObject\"");

        // notes
        isisJdoSupport.executeUpdate("delete from \"incodeNote\".\"NotableLink\"");
        isisJdoSupport.executeUpdate("delete from \"incodeNote\".\"Note\"");

        // demo objects
        executionContext.executeChild(this, new DemoModuleTearDown());
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
