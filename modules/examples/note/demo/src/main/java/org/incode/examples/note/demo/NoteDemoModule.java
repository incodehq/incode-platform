package org.incode.examples.note.demo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.examples.note.demo.shared.NoteDemoSharedModule;
import org.incode.examples.note.demo.usage.NoteDemoUsageModule;

@XmlRootElement(name = "module")
public class NoteDemoModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new NoteDemoSharedModule(),
                new NoteDemoUsageModule()
        );
    }

}
