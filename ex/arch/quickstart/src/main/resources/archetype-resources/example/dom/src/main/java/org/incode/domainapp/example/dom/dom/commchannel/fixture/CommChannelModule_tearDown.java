#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;

public class CommChannelModule_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleDomCommChannel${symbol_escape}".${symbol_escape}"CommunicationChannelOwnerLinkForDemoObject${symbol_escape}"");

        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"incodeCommChannel${symbol_escape}".${symbol_escape}"CommunicationChannelOwnerLink${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"incodeCommChannel${symbol_escape}".${symbol_escape}"CommunicationChannel${symbol_escape}"");

    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
