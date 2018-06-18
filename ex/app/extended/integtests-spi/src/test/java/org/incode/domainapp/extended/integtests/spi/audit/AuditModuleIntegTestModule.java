package org.incode.domainapp.extended.integtests.spi.audit;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.FixturesModuleSpiAuditSubmodule;

@XmlRootElement(name = "module")
public class AuditModuleIntegTestModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new FixturesModuleSpiAuditSubmodule(),
                new FakeDataModule()
        );
    }
}
