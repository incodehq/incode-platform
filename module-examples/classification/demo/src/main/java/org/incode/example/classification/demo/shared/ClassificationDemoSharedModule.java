package org.incode.example.classification.demo.shared;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.classification.demo.shared.demowithatpath.ClassificationDemoSharedDemoWithAtPathSubmodule;
import org.incode.example.classification.demo.shared.otherwithatpath.ClassificationDemoSharedOtherWithAtPathSubmodule;

@XmlRootElement(name = "module")
public class ClassificationDemoSharedModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new ClassificationDemoSharedDemoWithAtPathSubmodule(),
                new ClassificationDemoSharedOtherWithAtPathSubmodule()
        );
    }

}
