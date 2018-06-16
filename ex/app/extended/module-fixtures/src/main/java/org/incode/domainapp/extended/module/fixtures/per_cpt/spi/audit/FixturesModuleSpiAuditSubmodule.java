package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.audit.AuditModule;

@XmlRootElement(name = "module")
public class FixturesModuleSpiAuditSubmodule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new AuditModule()
        );
    }
}
