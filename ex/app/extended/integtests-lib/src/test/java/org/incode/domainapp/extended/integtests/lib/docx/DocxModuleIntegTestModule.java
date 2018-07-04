package org.incode.domainapp.extended.integtests.lib.docx;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.docx.DocxModule;
import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.FixturesModuleLibDocxSubmodule;

@XmlRootElement(name = "module")
public class DocxModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        final Set<Module> dependencies = super.getDependencies();
        dependencies.addAll(Sets.newHashSet(
                new DocxModule(),
                new FixturesModuleLibDocxSubmodule(),
                new FakeDataModule()
        ));
        return dependencies;
    }
}
