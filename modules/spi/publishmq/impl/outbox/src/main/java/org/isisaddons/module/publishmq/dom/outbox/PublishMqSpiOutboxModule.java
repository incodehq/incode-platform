package org.isisaddons.module.publishmq.dom.outbox;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract;
import org.isisaddons.module.publishmq.dom.outbox.events.OutboxEvent;
import org.isisaddons.module.publishmq.dom.servicespi.PublishMqSpiServicesModule;

@XmlRootElement(name = "module")
public class PublishMqSpiOutboxModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(new PublishMqSpiServicesModule());
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract() {
            @Override
            protected void execute(final ExecutionContext executionContext) {
                deleteFrom(OutboxEvent.class);
            }
        };
    }

}
