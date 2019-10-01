package org.isisaddons.module.publishmq.dom.mq;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.isisaddons.module.publishmq.dom.servicespi.PublishMqSpiServicesModule;

@XmlRootElement(name = "module")
public class PublishMqSpiMqModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(new PublishMqSpiServicesModule());
    }


}
