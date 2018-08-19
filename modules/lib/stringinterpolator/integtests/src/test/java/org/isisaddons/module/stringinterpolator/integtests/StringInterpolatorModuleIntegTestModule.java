package org.isisaddons.module.stringinterpolator.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.StringInterpolatorFixturesSubmodule;

@XmlRootElement(name = "module")
public class StringInterpolatorModuleIntegTestModule extends ModuleAbstract {

    public StringInterpolatorModuleIntegTestModule() {
        withConfigurationProperty("isis.website", "http://isis.apache.org");
    }

    @Override
    public Set<Module> getDependencies() {
        final Set<Module> dependencies = super.getDependencies();
        dependencies.addAll(Sets.newHashSet(
                new StringInterpolatorModule(),
                new StringInterpolatorFixturesSubmodule(),
                new FakeDataModule()
        ));
        return dependencies;
    }
}
