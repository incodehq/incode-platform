package org.incode.domainapp.extended.integtests.examples.document;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.example.alias.demo.examples.document.FixturesModuleExamplesDocumentIntegrationSubmodule;

@XmlRootElement(name = "module")
public class DocumentModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new FixturesModuleExamplesDocumentIntegrationSubmodule(),
                new FakeDataModule()
        );
    }
}
