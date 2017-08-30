package org.incode.domainapp.example.dom.spi.publishmq.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.publishmq.dom.demo.PublishMqDemoObject;
import org.incode.domainapp.example.dom.spi.publishmq.dom.demo.PublishMqDemoObjects;

public class PublishMqDemoObject_create3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private PublishMqDemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, publishmqDemoObjects.createPublishMqDemoObject(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private PublishMqDemoObjects publishmqDemoObjects;

}
