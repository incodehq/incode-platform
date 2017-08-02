package org.incode.domainapp.example.dom.spi.publishmq.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.spi.publishmq.dom.demo.PublishMqDemoObject;
import org.incode.domainapp.example.dom.spi.publishmq.dom.demo.PublishMqDemoObjects;

public class PublishMqDemoObjectsFixture extends DiscoverableFixtureScript {

    public PublishMqDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
	    executionContext.executeChild(this, new PublishMqDemoObjectsTearDownFixture());

        // create
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
