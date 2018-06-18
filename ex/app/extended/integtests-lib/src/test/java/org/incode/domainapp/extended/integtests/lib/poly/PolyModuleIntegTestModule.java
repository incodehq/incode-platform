package org.incode.domainapp.extended.integtests.lib.poly;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.FixturesModuleLibPolySubmodule;

@XmlRootElement(name = "module")
public class PolyModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        final Set<Module> dependencies = super.getDependencies();
        dependencies.addAll(Sets.newHashSet(
                new FakeDataModule(),
                new FixturesModuleLibPolySubmodule()
        ));
        return dependencies;
    }
}
