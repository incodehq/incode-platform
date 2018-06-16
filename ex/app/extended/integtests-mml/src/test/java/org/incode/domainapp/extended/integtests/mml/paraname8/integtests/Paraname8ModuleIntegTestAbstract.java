package org.incode.domainapp.extended.integtests.mml.paraname8.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.shared.demo.FixturesModuleSharedDemoSubmodule;

public abstract class Paraname8ModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new Paraname8ModuleIntegTestAbstract.MyModule();
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

    protected Paraname8ModuleIntegTestAbstract() {
        super(module());
    }


}
