package org.incode.example.classification.demo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.classification.demo.shared.ClassificationDemoSharedModule;
import org.incode.example.classification.demo.usage.ClassificationDemoUsageModule;

@XmlRootElement(name = "module")
public class ClassificationDemoModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new ClassificationDemoSharedModule(),
                new ClassificationDemoUsageModule()
        );
    }

}
