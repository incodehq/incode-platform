package org.incode.domainapp.extended.integtests.mml.paraname8;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.shared.demo.FixturesModuleSharedDemoSubmodule;

@XmlRootElement(name = "module")
public class Paraname8ModuleIntegTestModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new FixturesModuleSharedDemoSubmodule(),
                new FakeDataModule()
        );
    }
}
