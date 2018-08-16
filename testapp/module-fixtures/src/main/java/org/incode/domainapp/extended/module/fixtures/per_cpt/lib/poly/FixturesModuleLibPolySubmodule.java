package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democommchannel.CommunicationChannel;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demofixedasset.FixedAsset;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demoparty.Party;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent.CaseContentLink;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent.fixedasset.CaseContentLinkForFixedAsset;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent.party.CaseContentLinkForParty;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.caseprimary.CasePrimaryContentLink;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.caseprimary.fixedasset.CasePrimaryContentLinkForFixedAsset;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.caseprimary.party.CasePrimaryContentLinkForParty;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.ccowner.CommunicationChannelOwnerLink;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.ccowner.fixedasset.CommunicationChannelOwnerLinkForFixedAsset;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.ccowner.party.CommunicationChannelOwnerLinkForParty;

@XmlRootElement(name = "module")
public class FixturesModuleLibPolySubmodule extends ModuleAbstract {

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
