package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.dom.CommunicationChannelOwnerLinkForDemoObject;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;

public class CommChannelModule_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        deleteFrom(CommunicationChannelOwnerLinkForDemoObject.class);
        deleteFrom(CommunicationChannelOwnerLink.class);
        deleteFrom(CommunicationChannel.class);
    }


}
