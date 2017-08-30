#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;

public class DemoObject_withCommChannels_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new CommChannelModule_tearDown());
        executionContext.executeChild(this, new DemoModuleTearDown());
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
