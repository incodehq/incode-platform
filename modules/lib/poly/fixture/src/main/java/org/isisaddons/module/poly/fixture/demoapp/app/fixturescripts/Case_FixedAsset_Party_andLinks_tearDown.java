package org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

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
