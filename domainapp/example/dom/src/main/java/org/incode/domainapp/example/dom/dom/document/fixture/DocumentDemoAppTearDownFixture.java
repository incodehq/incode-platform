package org.incode.domainapp.example.dom.dom.document.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.module.document.fixture.teardown.DocumentModuleTearDown;

public class DocumentDemoAppTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDomDocument\".\"PaperclipForDemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"exampleDomDocument\".\"DemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"exampleDomDocument\".\"PaperclipForOtherObject\"");
        isisJdoSupport.executeUpdate("delete from \"exampleDomDocument\".\"OtherObject\"");

        executionContext.executeChild(this, new DocumentModuleTearDown());
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
