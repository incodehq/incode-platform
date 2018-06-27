package org.incode.example.commchannel.demo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.commchannel.demo.shared.CommChannelDemoSharedModule;
import org.incode.example.commchannel.demo.usage.CommChannelDemoUsageModule;

@XmlRootElement(name = "module")
public class CommChannelDemoModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new CommChannelDemoSharedModule(),
                new CommChannelDemoUsageModule()
        );
    }

}
