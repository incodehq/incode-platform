package org.incode.domainapp.example.dom.dom.document.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithUrlTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.OtherObjectTearDown;
import org.incode.module.document.fixture.teardown.DocumentModuleTearDown;

public class DocumentDemoAppTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDomDocument\".\"PaperclipForDemoObjectWithUrl\"");

        executionContext.executeChild(this, new DemoObjectWithUrlTearDown());

        isisJdoSupport.executeUpdate("delete from \"exampleDomDocument\".\"PaperclipForOtherObject\"");

        executionContext.executeChild(this, new OtherObjectTearDown());

        executionContext.executeChild(this, new DocumentModuleTearDown());
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
