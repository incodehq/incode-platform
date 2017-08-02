package org.incode.domainapp.example.dom.lib.poly.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class PolyAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"CaseContentLinkForParty\"");
        isisJdoSupport.executeUpdate("delete from \"CaseContentLinkForFixedAsset\"");
        isisJdoSupport.executeUpdate("delete from \"CaseContentLink\"");
        isisJdoSupport.executeUpdate("delete from \"Case\"");

        isisJdoSupport.executeUpdate("delete from \"CommunicationChannelOwnerLinkForParty\"");
        isisJdoSupport.executeUpdate("delete from \"CommunicationChannelOwnerLinkForFixedAsset\"");
        isisJdoSupport.executeUpdate("delete from \"CommunicationChannelOwnerLink\"");
        isisJdoSupport.executeUpdate("delete from \"CommunicationChannel\"");

        isisJdoSupport.executeUpdate("delete from \"FixedAsset\"");
        isisJdoSupport.executeUpdate("delete from \"Party\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
