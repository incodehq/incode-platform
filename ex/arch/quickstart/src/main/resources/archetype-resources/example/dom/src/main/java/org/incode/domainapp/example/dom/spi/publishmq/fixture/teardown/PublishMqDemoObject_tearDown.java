#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.spi.publishmq.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class PublishMqDemoObject_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleSpiPublishMq${symbol_escape}".${symbol_escape}"PublishMqDemoObject${symbol_escape}"");
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
