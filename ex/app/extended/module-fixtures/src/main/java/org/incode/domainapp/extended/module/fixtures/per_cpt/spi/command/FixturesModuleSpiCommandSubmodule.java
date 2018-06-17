package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.command.CommandModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.SomeCommandAnnotatedObject;

@XmlRootElement(name = "module")
public class FixturesModuleSpiCommandSubmodule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(new CommandModule());
    }

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {
                deleteFrom(SomeCommandAnnotatedObject.class);
            }
        };
    }
}
