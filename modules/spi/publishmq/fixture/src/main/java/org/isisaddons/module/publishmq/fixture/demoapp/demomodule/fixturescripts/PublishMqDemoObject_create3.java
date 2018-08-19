package org.isisaddons.module.publishmq.fixture.demoapp.demomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.dom.PublishMqDemoObject;
import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.dom.PublishMqDemoObjects;

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
