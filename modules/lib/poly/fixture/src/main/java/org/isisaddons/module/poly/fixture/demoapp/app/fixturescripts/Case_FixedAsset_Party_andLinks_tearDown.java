package org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Case;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.CommunicationChannel;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.FixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.Party;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.contentlink.CaseContentLink;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.contentlink.CaseContentLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.contentlink.CaseContentLinkForParty;
import org.isisaddons.module.poly.fixture.demoapp.polyccowner.dom.CommunicationChannelOwnerLink;
import org.isisaddons.module.poly.fixture.demoapp.polyccowner.dom.CommunicationChannelOwnerLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.polyccowner.dom.CommunicationChannelOwnerLinkForParty;

public class Case_FixedAsset_Party_andLinks_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {

        deleteFrom(CaseContentLinkForParty.class);
        deleteFrom(CaseContentLinkForFixedAsset.class);
        deleteFrom(CaseContentLink.class);
        deleteFrom(Case.class);
        deleteFrom(CommunicationChannelOwnerLinkForParty.class);
        deleteFrom(CommunicationChannelOwnerLinkForFixedAsset.class);
        deleteFrom(CommunicationChannelOwnerLink.class);
        deleteFrom(CommunicationChannel.class);
        deleteFrom(FixedAsset.class);
        deleteFrom(Party.class);
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
