package org.incode.platform.spi.audit.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.example.dom.spi.audit.ExampleDomSpiAuditModule;

public class AuditSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER =
            org.isisaddons.module.audit.AuditSpiAppManifest.BUILDER
                .withAdditionalModules(ExampleDomSpiAuditModule.class);

    public AuditSpiAppManifest() {
        super(BUILDER);
    }

}
