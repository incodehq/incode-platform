package org.incode.domainapp.example.dom.dom.communications.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithNotesTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoInvoice2TearDown;
import org.incode.module.communications.fixture.teardown.CommunicationModuleTearDown;
import org.incode.module.country.fixture.teardown.CountryModuleTearDown;
import org.incode.module.document.fixture.teardown.DocumentModuleTearDown;

public class DemoModuleTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        isisJdoSupport.executeUpdate("delete from \"exampleDomCommunications\".\"PaperclipForDemoInvoice2\"");
        executionContext.executeChild(this, new DemoInvoice2TearDown());

        isisJdoSupport.executeUpdate("delete from \"exampleDomCommunications\".\"CommunicationChannelOwnerLinkForDemoObjectWithNotes\"");
        executionContext.executeChild(this, new DemoObjectWithNotesTearDown());

        executionContext.executeChild(this, new CommunicationModuleTearDown());
        executionContext.executeChild(this, new DocumentModuleTearDown());
        executionContext.executeChild(this, new CountryModuleTearDown());

    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
