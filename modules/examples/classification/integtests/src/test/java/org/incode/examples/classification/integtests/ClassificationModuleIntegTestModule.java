package org.incode.examples.classification.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.example.classification.demo.usage.ClassificationDemoUsageModule;

@XmlRootElement(name = "module")
public class ClassificationModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new ClassificationDemoUsageModule(),
                new FakeDataModule()
        );
    }
}
