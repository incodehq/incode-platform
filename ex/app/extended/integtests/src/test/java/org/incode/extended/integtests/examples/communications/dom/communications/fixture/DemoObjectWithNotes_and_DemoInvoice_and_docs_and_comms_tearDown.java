package org.incode.extended.integtests.examples.communications.dom.communications.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.extended.integtests.examples.communications.dom.communications.dom.commchannels.CommunicationChannelOwnerLinkForDemoObjectWithNotes;
import org.incode.extended.integtests.examples.communications.dom.communications.dom.paperclips.PaperclipForDemoInvoice;

public class DemoObjectWithNotes_and_DemoInvoice_and_docs_and_comms_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(PaperclipForDemoInvoice.class);
        deleteFrom(CommunicationChannelOwnerLinkForDemoObjectWithNotes.class);
    }

}
