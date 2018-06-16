package org.incode.domainapp.extended.integtests.spi;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.domainapp.extended.integtests.spi.audit.AuditModuleIntegTestAbstract;

@XmlRootElement(name = "module")
public class IntegTestsSpiModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                AuditModuleIntegTestAbstract.module()
        );
    }
}
