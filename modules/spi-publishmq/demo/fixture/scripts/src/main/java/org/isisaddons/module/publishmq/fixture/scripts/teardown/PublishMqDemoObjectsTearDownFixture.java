package org.isisaddons.module.publishmq.fixture.scripts.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class PublishMqDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"publishmq\".\"PublishMqDemoObject\"");
    }

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
