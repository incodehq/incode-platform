package org.incode.domainapp.example.dom.dom.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectTearDown;

public class CommChannelDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDomCommChannel\".\"CommunicationChannelOwnerLinkForDemoObject\"");

        executionContext.executeChild(this, new DemoObjectTearDown());

        isisJdoSupport.executeUpdate("delete from \"incodeCommChannel\".\"CommunicationChannelOwnerLink\"");
        isisJdoSupport.executeUpdate("delete from \"incodeCommChannel\".\"CommunicationChannel\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
