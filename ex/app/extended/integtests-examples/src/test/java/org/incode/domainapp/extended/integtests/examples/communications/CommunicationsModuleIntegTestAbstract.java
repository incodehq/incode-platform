package org.incode.domainapp.extended.integtests.examples.communications;

import java.util.Set;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.command.dom.CommandDomModule;
import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.FixturesModuleExamplesCommunicationsIntegrationSubmodule;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.app.fakesched.FakeScheduler;

public abstract class CommunicationsModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    @XmlRootElement(name = "module")
    public static class MyModule extends ModuleAbstract {
        @Override
        public Set<org.apache.isis.applib.Module> getDependencies() {
            return Sets.newHashSet(
                    new FixturesModuleExamplesCommunicationsIntegrationSubmodule(),
                    new CommandDomModule(),
                    new FakeDataModule()
            );
        }
    }

    public static ModuleAbstract module() {
        return new MyModule();
    }

    protected CommunicationsModuleIntegTestAbstract() {
        super(module());
    }

    @Inject
    protected FakeScheduler fakeScheduler;

}
