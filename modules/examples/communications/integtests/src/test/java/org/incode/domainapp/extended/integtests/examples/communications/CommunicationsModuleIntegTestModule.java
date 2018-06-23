package org.incode.domainapp.extended.integtests.examples.communications;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.command.dom.CommandDomModule;
import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.example.communications.demo.usage.communications.FixturesModuleExamplesCommunicationsIntegrationSubmodule;

@XmlRootElement(name = "module")
public class CommunicationsModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new FixturesModuleExamplesCommunicationsIntegrationSubmodule(),
                new CommandDomModule(),
                new FakeDataModule()
        );
    }
}
