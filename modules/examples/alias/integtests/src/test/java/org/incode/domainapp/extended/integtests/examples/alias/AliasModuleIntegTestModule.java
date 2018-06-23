package org.incode.domainapp.extended.integtests.examples.alias;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.example.alias.demo.AliasDemoModule;

@XmlRootElement(name = "module")
public class AliasModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new AliasDemoModule(),
                new FakeDataModule()
        );
    }
}
