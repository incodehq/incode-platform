package org.incode.domainapp.extended.integtests.spi.publishmq;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.FixturesModuleSpiPublishMqSubmodule;

public abstract class PublishMqModuleIntegTestAbstract extends IntegrationTestAbstract3 {


    @XmlRootElement(name = "module")
    public static class MyModule extends ModuleAbstract {

        @Override
        public Set<Module> getDependencies() {
            return Sets.newHashSet(
                    new FixturesModuleSpiPublishMqSubmodule(),
                    new FakeDataModule()
            );
        }
    }

    public static ModuleAbstract module() {
        return new MyModule();
    }

    protected PublishMqModuleIntegTestAbstract() {
        super(module());
    }

}
