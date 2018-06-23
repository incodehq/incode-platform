package org.incode.examples.communications.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.command.dom.CommandDomModule;
import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.examples.communications.demo.usage.CommunicationsDemoUsageModule;

@XmlRootElement(name = "module")
public class CommunicationsModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new CommunicationsDemoUsageModule(),
                new CommandDomModule(),
                new FakeDataModule()
        );
    }
}
