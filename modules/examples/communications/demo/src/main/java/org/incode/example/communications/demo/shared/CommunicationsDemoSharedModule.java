package org.incode.example.communications.demo.shared;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.communications.demo.shared.demowithnotes.CommunicationsDemoSharedDemoWithNotesSubmodule;

@XmlRootElement(name = "module")
public class CommunicationsDemoSharedModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new CommunicationsDemoSharedDemoWithNotesSubmodule());
    }
}
