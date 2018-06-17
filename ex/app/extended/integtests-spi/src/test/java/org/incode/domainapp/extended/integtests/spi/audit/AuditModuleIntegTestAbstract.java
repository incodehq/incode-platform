package org.incode.domainapp.extended.integtests.spi.audit;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.FixturesModuleSpiAuditSubmodule;

public abstract class AuditModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    @XmlRootElement(name = "module")
    public static class MyModule extends ModuleAbstract {

        @Override
        public Set<Module> getDependencies() {
            return Sets.newHashSet(
                    new FixturesModuleSpiAuditSubmodule(),
                    new FakeDataModule()
            );
        }
    }

    public static ModuleAbstract module() {
        return new MyModule();
    }

    protected AuditModuleIntegTestAbstract() {
        super(module());
    }


    protected void deleteFromAuditEntry() {
        runFixtureScript(new TeardownFixtureAbstract2() {
            @Override protected void execute(final FixtureScript.ExecutionContext executionContext) {
                deleteFrom(AuditEntry.class);
            }
        });
        transactionService.flushTransaction();
    }


}
