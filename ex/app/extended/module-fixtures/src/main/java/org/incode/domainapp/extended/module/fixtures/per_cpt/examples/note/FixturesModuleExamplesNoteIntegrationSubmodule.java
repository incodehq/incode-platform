package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.dom.demolink.NotableLinkForDemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.FixturesModuleSharedDemoSubmodule;
import org.incode.example.note.dom.NoteModule;

@XmlRootElement(name = "module")
public class FixturesModuleExamplesNoteIntegrationSubmodule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new NoteModule(),
                new FixturesModuleSharedDemoSubmodule()
        );
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {
                deleteFrom(NotableLinkForDemoObject.class);
            }
        };
    }
}
