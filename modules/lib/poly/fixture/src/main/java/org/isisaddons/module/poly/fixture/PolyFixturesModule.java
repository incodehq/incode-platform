package org.isisaddons.module.poly.fixture;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParty;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLinkForParty;
import org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoCasePrimaryContentLinkForParty;
import org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoPolyDemoCasePrimaryContentLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoCasePrimaryContentLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLinkForParty;

@XmlRootElement(name = "module")
public class PolyFixturesModule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {

                deleteFrom(PolyDemoCaseContentLinkForFixedAsset.class);
                deleteFrom(PolyDemoCaseContentLinkForParty.class);
                deleteFrom(PolyDemoCaseContentLink.class);

                deleteFrom(PolyDemoCasePrimaryContentLinkForFixedAsset.class);
                deleteFrom(PolyDemoCasePrimaryContentLinkForParty.class);
                deleteFrom(PolyDemoPolyDemoCasePrimaryContentLink.class);

                deleteFrom(PolyDemoCommunicationChannelOwnerLinkForParty.class);
                deleteFrom(PolyDemoCommunicationChannelOwnerLinkForFixedAsset.class);
                deleteFrom(PolyDemoCommunicationChannelOwnerLink.class);
                deleteFrom(PolyDemoCommunicationChannel.class);

                deleteFrom(PolyDemoParty.class);
                deleteFrom(PolyDemoFixedAsset.class);
                deleteFrom(PolyDemoCase.class);
            }
        };
    }
}
