package org.incode.domainapp.example.dom.dom.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class CommChannelDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"incodeCommChannelDemo\".\"CommunicationChannelOwnerLinkForDemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"incodeCommChannelDemo\".\"CommChannelDemoObject\"");

        isisJdoSupport.executeUpdate("delete from \"incodeCommChannel\".\"CommunicationChannelOwnerLink\"");
        isisJdoSupport.executeUpdate("delete from \"incodeCommChannel\".\"CommunicationChannel\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
