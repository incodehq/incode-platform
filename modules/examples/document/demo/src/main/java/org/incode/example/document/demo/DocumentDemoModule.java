package org.incode.example.document.demo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.document.demo.shared.DocumentDemoSharedModule;
import org.incode.example.document.demo.usage.DocumentDemoUsageModule;

@XmlRootElement(name = "module")
public class DocumentDemoModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocumentDemoSharedModule(),
                new DocumentDemoUsageModule()
        );
    }

}
