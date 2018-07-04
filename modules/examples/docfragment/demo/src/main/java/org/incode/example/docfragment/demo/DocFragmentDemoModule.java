package org.incode.example.docfragment.demo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.docfragment.demo.shared.DocFragmentDemoSharedModule;
import org.incode.example.docfragment.demo.usage.DocFragmentDemoUsageModule;

@XmlRootElement(name = "module")
public class DocFragmentDemoModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocFragmentDemoSharedModule(),
                new DocFragmentDemoUsageModule()
        );
    }

}
