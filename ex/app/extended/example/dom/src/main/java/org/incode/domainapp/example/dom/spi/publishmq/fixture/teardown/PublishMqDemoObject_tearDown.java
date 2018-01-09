package org.incode.domainapp.example.dom.spi.publishmq.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class PublishMqDemoObject_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleSpiPublishMq\".\"PublishMqDemoObject\"");
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
