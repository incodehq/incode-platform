package org.isisaddons.module.audit.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.audit.fixture.AuditFixturesModule;
import org.isisaddons.module.fakedata.FakeDataModule;

@XmlRootElement(name = "module")
public class AuditModuleIntegTestModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new AuditFixturesModule(),
                new FakeDataModule()
        );
    }
}
