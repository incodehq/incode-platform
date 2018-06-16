package org.incode.domainapp.extended.integtests.lib.stringinterpolator;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.FixturesModuleLibStringInterpolatorSubmodule;

public abstract class StringInterpolatorModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new StringInterpolatorModuleIntegTestAbstract.MyModule();
    }

    @XmlRootElement(name = "module")
    public static class MyModule extends ModuleAbstract {

        public MyModule() {
            withConfigurationProperty("isis.website", "http://isis.apache.org");
        }

        @Override
        public Set<Module> getDependencies() {
            final Set<Module> dependencies = super.getDependencies();
            dependencies.addAll(Sets.newHashSet(
                    new StringInterpolatorModule(),
                    new FixturesModuleLibStringInterpolatorSubmodule(),
                    new FakeDataModule()
            ));
            return dependencies;
        }
    }

    protected StringInterpolatorModuleIntegTestAbstract() {
        super(module());
    }


}
