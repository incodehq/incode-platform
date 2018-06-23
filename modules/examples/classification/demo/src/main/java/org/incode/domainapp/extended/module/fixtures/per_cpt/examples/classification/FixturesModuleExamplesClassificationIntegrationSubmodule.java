package org.incode.example.alias.demo.examples.classification;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.alias.demo.examples.classification.fixture.Classifications_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demowithatpath.FixturesModuleSharedDemoWithAtPathSubmodule;
import org.incode.domainapp.extended.module.fixtures.shared.otherwithatpath.FixturesModuleSharedOtherWithAtPathSubmodule;
import org.incode.example.classification.ClassificationModule;

@XmlRootElement(name = "module")
public class FixturesModuleExamplesClassificationIntegrationSubmodule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new ClassificationModule(),
                new FixturesModuleSharedDemoWithAtPathSubmodule(),
                new FixturesModuleSharedOtherWithAtPathSubmodule()
            );
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new Classifications_tearDown();
    }

}
