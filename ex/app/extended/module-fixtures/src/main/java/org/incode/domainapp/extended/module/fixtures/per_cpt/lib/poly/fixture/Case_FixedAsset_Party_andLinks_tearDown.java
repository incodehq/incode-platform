package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democommchannel.CommunicationChannel;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demofixedasset.FixedAsset;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demoparty.Party;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent.CaseContentLink;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent.fixedasset.CaseContentLinkForFixedAsset;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent.party.CaseContentLinkForParty;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.ccowner.CommunicationChannelOwnerLink;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.ccowner.fixedasset.CommunicationChannelOwnerLinkForFixedAsset;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.ccowner.party.CommunicationChannelOwnerLinkForParty;

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
