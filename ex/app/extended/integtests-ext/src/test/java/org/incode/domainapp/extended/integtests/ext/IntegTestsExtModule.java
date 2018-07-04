package org.incode.domainapp.extended.integtests.ext;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.domainapp.extended.integtests.ext.flywaydb.FlywayDbModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.ext.togglz.TogglzModuleIntegTestAbstract;

@XmlRootElement(name = "module")
public class IntegTestsExtModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                FlywayDbModuleIntegTestAbstract.module(),
                TogglzModuleIntegTestAbstract.module()
        );
    }
}
