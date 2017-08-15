package org.incode.domainapp.example.dom.dom.communications.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;
import org.incode.module.communications.fixture.teardown.CommunicationModuleTearDown;
import org.incode.module.country.fixture.teardown.CountryModuleTearDown;
import org.incode.module.document.fixture.teardown.DocumentModuleTearDown;

public class DemoObjectWithNotes_and_DemoInvoice2_and_docs_and_comms_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        // communication & commchannel links
        isisJdoSupport.executeUpdate("delete from \"exampleDomCommunications\".\"PaperclipForDemoInvoice2\"");

        isisJdoSupport.executeUpdate("delete from \"exampleDomCommunications\".\"CommunicationChannelOwnerLinkForDemoObjectWithNotes\"");

        // comms, doc, country
        executionContext.executeChild(this, new CommunicationModuleTearDown());
        executionContext.executeChild(this, new DocumentModuleTearDown());
        executionContext.executeChild(this, new CountryModuleTearDown());

        // demo objects
        executionContext.executeChild(this, new DemoModuleTearDown());

    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
