package org.isisaddons.module.publishmq.dom.status;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract;
import org.isisaddons.module.publishmq.dom.status.impl.StatusMessage;

@XmlRootElement(name = "module")
public class PublishMqSpiStatusModule extends ModuleAbstract {

    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract() {
            @Override
            protected void execute(final ExecutionContext executionContext) {
                deleteFrom(StatusMessage.class);
            }
        };
    }

}
