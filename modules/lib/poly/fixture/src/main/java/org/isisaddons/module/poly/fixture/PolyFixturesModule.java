package org.isisaddons.module.poly.fixture;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Case;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.CommunicationChannel;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.FixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.Party;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.contentlink.CaseContentLink;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.contentlink.CaseContentLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.contentlink.CaseContentLinkForParty;
import org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.dom.CasePrimaryContentLink;
import org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.dom.CasePrimaryContentLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.dom.CasePrimaryContentLinkForParty;
import org.isisaddons.module.poly.fixture.demoapp.polyccowner.dom.CommunicationChannelOwnerLink;
import org.isisaddons.module.poly.fixture.demoapp.polyccowner.dom.CommunicationChannelOwnerLinkForFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.polyccowner.dom.CommunicationChannelOwnerLinkForParty;

@XmlRootElement(name = "module")
public class PolyFixturesModule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {

                deleteFrom(CaseContentLinkForFixedAsset.class);
                deleteFrom(CaseContentLinkForParty.class);
                deleteFrom(CaseContentLink.class);

                deleteFrom(CasePrimaryContentLinkForFixedAsset.class);
                deleteFrom(CasePrimaryContentLinkForParty.class);
                deleteFrom(CasePrimaryContentLink.class);

                deleteFrom(CommunicationChannelOwnerLinkForParty.class);
                deleteFrom(CommunicationChannelOwnerLinkForFixedAsset.class);
                deleteFrom(CommunicationChannelOwnerLink.class);
                deleteFrom(CommunicationChannel.class);

                deleteFrom(Party.class);
                deleteFrom(FixedAsset.class);
                deleteFrom(Case.class);
            }
        };
    }
}
