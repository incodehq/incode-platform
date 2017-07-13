package org.isisaddons.module.publishmq.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.isisaddons.module.publishmq.fixture.dom.PublishMqDemoObject;
import org.isisaddons.module.publishmq.fixture.dom.PublishMqDemoObjects;
import org.isisaddons.module.publishmq.fixture.scripts.teardown.PublishMqDemoObjectsTearDownFixture;

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
