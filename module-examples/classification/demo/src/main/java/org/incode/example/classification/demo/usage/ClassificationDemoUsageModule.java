package org.incode.example.classification.demo.usage;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.classification.ClassificationModule;
import org.incode.example.classification.demo.shared.ClassificationDemoSharedModule;
import org.incode.example.classification.demo.usage.fixture.Classifications_tearDown;

@XmlRootElement(name = "module")
public class ClassificationDemoUsageModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new ClassificationModule(),
                new ClassificationDemoSharedModule()
            );
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new Classifications_tearDown();
    }

}
