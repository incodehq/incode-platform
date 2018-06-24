package org.incode.examples.note.demo.shared;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.examples.note.demo.shared.demo.NoteDemoSharedDemoSubmodule;

@XmlRootElement(name = "module")
public class NoteDemoSharedModule extends ModuleAbstract {
    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(new NoteDemoSharedDemoSubmodule());
    }
}
