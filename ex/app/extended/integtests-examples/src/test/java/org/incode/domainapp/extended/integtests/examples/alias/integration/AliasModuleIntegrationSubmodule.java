package org.incode.domainapp.extended.integtests.examples.alias.integration;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.integtests.examples.alias.integration.dom.AliasForDemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.FixturesModuleSharedDemoSubmodule;
import org.incode.example.alias.dom.AliasModule;

@XmlRootElement(name = "module")
public class AliasModuleIntegrationSubmodule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new FixturesModuleSharedDemoSubmodule(),
                new AliasModule()
        );
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override
            protected void execute(final FixtureScript.ExecutionContext executionContext) {
                deleteFrom(AliasForDemoObject.class);
            }
        };
    }
}
