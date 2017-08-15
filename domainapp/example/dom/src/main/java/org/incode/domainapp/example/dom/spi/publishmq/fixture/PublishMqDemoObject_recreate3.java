package org.incode.domainapp.example.dom.spi.publishmq.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.spi.publishmq.dom.demo.PublishMqDemoObject;
import org.incode.domainapp.example.dom.spi.publishmq.dom.demo.PublishMqDemoObjects;
import org.incode.domainapp.example.dom.spi.publishmq.fixture.teardown.PublishMqDemoObject_tearDown;

public class PublishMqDemoObject_recreate3 extends DiscoverableFixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

	    executionContext.executeChild(this, new PublishMqDemoObject_tearDown());

        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private PublishMqDemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, publishmqDemoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private PublishMqDemoObjects publishmqDemoObjects;

}
