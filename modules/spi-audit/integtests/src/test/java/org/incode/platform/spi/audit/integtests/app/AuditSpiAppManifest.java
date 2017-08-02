package org.incode.platform.spi.audit.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.audit.AuditModule;

import org.incode.domainapp.example.dom.spi.audit.ExampleDomSpiAuditModule;

public class AuditSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            AuditModule.class,
            ExampleDomSpiAuditModule.class
    );

    public AuditSpiAppManifest() {
        super(BUILDER);
    }

}
