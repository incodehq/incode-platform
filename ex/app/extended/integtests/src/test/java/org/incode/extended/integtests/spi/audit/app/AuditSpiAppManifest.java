package org.incode.extended.integtests.spi.audit.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.ExampleDomSpiAuditModule;

public class AuditSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER =
            org.isisaddons.module.audit.AuditSpiAppManifest.BUILDER
                .withAdditionalModules(ExampleDomSpiAuditModule.class);

    public AuditSpiAppManifest() {
        super(BUILDER);
    }

}
