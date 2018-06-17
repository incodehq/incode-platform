package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.publishmq.PublishMqModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.dom.demo.PublishMqDemoObject;

@XmlRootElement(name = "module")
public class FixturesModuleSpiPublishMqSubmodule extends ModuleAbstract {

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
