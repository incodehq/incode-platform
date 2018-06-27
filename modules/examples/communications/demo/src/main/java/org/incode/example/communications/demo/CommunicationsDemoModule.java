package org.incode.example.communications.demo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.communications.demo.shared.CommunicationsDemoSharedModule;
import org.incode.example.communications.demo.usage.CommunicationsDemoUsageModule;

@XmlRootElement(name = "module")
public class CommunicationsDemoModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new CommunicationsDemoSharedModule(),
                new CommunicationsDemoUsageModule()
        );
    }

}
