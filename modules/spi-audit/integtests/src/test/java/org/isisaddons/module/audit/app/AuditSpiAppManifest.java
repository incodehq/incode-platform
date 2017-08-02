package org.isisaddons.module.audit.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.audit.AuditModule;

import domainapp.modules.exampledom.spi.audit.ExampleDomSpiAuditModule;

public class AuditSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.withModules(
            AuditModule.class,
            ExampleDomSpiAuditModule.class
    );

    public AuditSpiAppManifest() {
        super(BUILDER);
    }

}
