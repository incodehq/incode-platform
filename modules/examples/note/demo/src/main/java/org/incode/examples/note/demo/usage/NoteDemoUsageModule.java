package org.incode.examples.note.demo.usage;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.note.NoteModule;
import org.incode.examples.note.demo.shared.NoteDemoSharedModule;
import org.incode.examples.note.demo.usage.dom.demolink.NotableLinkForDemoObject;

@XmlRootElement(name = "module")
public class NoteDemoUsageModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new NoteModule(),
                new NoteDemoSharedModule()
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
