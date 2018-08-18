package org.isisaddons.module.togglz.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

@XmlRootElement(name = "module")
public class TogglzModuleIntegTestModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        final Set<Module> dependencies = super.getDependencies();
        dependencies.addAll(Sets.newHashSet(
                new FixturesModuleSharedDemoSubmodule(),
                new FakeDataModule()
        ));
        return dependencies;
    }
}
