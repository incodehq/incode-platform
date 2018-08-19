package org.isisaddons.module.publishmq.fixture;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.dom.PublishMqDemoObject;

@XmlRootElement(name = "module")
public class PublishMqFixturesModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(new PublishMqModule());
    }

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {
                deleteFrom(PublishMqDemoObject.class);
            }
        };
    }
}
