package org.incode.domainapp.extended.integtests.spi.audit;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;
import org.incode.domainapp.extended.integtests.spi.audit.app.AuditSpiAppManifest;

public abstract class AuditModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(AuditSpiAppManifest.BUILDER
                .withAdditionalModules(FixturesModuleSharedSubmodule.class)
        );
    }

}
