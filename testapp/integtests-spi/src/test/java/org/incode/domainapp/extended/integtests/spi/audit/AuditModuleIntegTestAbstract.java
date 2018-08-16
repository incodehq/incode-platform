package org.incode.domainapp.extended.integtests.spi.audit;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.audit.dom.AuditEntry;

public abstract class AuditModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new AuditModuleIntegTestModule();
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
