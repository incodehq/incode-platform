package org.incode.platform.spi.security.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.security.SecurityModule;

import domainapp.modules.exampledom.spi.security.ExampleDomSpiSecurityModule;

public class SecuritySpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder
            .forModules(
                    SecurityModule.class,
                    ExampleDomSpiSecurityModule.class
            )
            .withAdditionalServices(
                    org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt.class
                    ,org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto.class
                    //,org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceVetoBeatsAllow.class
            );

    public SecuritySpiAppManifest() {
        super(BUILDER);
    }


}
