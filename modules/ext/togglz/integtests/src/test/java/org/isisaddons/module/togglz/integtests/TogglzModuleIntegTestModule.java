package org.isisaddons.module.togglz.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.togglz.fixture.TogglzFixturesModule;

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
}
