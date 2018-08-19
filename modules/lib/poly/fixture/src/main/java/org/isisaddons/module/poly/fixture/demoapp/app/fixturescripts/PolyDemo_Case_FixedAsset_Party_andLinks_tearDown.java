package org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParty;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLinkForParty;
import org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLinkForParty;

public class PolyDemo_Case_FixedAsset_Party_andLinks_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {

        deleteFrom(PolyDemoCaseContentLinkForParty.class);
        deleteFrom(PolyDemoCaseContentLinkForFixedAsset.class);
        deleteFrom(PolyDemoCaseContentLink.class);
        deleteFrom(PolyDemoCase.class);
        deleteFrom(PolyDemoCommunicationChannelOwnerLinkForParty.class);
        deleteFrom(PolyDemoCommunicationChannelOwnerLinkForFixedAsset.class);
        deleteFrom(PolyDemoCommunicationChannelOwnerLink.class);
        deleteFrom(PolyDemoCommunicationChannel.class);
        deleteFrom(PolyDemoFixedAsset.class);
        deleteFrom(PolyDemoParty.class);
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
