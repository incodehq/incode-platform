package org.isisaddons.module.togglz.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.togglz.fixture.TogglzFixturesModule;
import org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom.TogglzDemoObject;

@XmlRootElement(name = "module")
public class TogglzModuleIntegTestModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        final Set<Module> dependencies = super.getDependencies();
        dependencies.addAll(Sets.newHashSet(
                new TogglzFixturesModule(),
                new FakeDataModule()
        ));
        return dependencies;
    }

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {
                deleteFrom(TogglzDemoObject.class);
            }
        };
    }
}
