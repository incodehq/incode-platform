package org.incode.domainapp.extended.integtests.examples.note;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.FixturesModuleExamplesNoteIntegrationSubmodule;

@XmlRootElement(name = "module")
public class NoteModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new FixturesModuleExamplesNoteIntegrationSubmodule(),
                new FakeDataModule()
        );
    }
}
