package org.isisaddons.module.audit.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.audit.app.AuditSpiAppManifest;

public abstract class AuditModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new AuditSpiAppManifest());
    }

}
