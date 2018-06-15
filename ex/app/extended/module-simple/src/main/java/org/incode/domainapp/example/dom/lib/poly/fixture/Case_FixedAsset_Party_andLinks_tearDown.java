package org.incode.domainapp.example.dom.lib.poly.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class Case_FixedAsset_Party_andLinks_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"CaseContentLinkForParty\"");
        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"CaseContentLinkForFixedAsset\"");
        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"CaseContentLink\"");
        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"Case\"");

        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"CommunicationChannelOwnerLinkForParty\"");
        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"CommunicationChannelOwnerLinkForFixedAsset\"");
        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"CommunicationChannelOwnerLink\"");
        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"CommunicationChannel\"");

        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"FixedAsset\"");
        isisJdoSupport.executeUpdate("delete from \"exampleLibPoly\".\"Party\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
