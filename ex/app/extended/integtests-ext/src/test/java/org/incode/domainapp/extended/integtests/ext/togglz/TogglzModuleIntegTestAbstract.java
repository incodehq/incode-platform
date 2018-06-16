package org.incode.domainapp.extended.integtests.ext.togglz;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.junit.Rule;
import org.togglz.junit.TogglzRule;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.base.togglz.TogglzFeature;
import org.incode.domainapp.extended.module.fixtures.shared.demo.FixturesModuleSharedDemoSubmodule;

public abstract class TogglzModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(TogglzFeature.class);

    public static ModuleAbstract module() {
        return new TogglzModuleIntegTestAbstract.MyModule();
    }

    @XmlRootElement(name = "module")
    public static class MyModule extends ModuleAbstract {

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

    protected TogglzModuleIntegTestAbstract() {
        super(module());
    }


}
