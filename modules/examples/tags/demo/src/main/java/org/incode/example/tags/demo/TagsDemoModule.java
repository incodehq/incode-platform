package org.incode.example.tags.demo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.tags.demo.shared.TagsDemoSharedModule;

@XmlRootElement(name = "module")
public class TagsDemoModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new TagsDemoSharedModule()
        );
    }

}
