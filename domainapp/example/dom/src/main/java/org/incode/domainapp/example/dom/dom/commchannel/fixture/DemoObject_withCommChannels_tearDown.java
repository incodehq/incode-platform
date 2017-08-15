package org.incode.domainapp.example.dom.dom.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;

public class DemoObject_withCommChannels_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // comm channels
        isisJdoSupport.executeUpdate("delete from \"exampleDomCommChannel\".\"CommunicationChannelOwnerLinkForDemoObject\"");

        isisJdoSupport.executeUpdate("delete from \"incodeCommChannel\".\"CommunicationChannelOwnerLink\"");
        isisJdoSupport.executeUpdate("delete from \"incodeCommChannel\".\"CommunicationChannel\"");

        // demo objects
        executionContext.executeChild(this, new DemoModuleTearDown());
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
