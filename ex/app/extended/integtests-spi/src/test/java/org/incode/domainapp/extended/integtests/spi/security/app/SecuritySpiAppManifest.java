package org.incode.extended.integtests.spi.security.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.security.SecurityModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.FixturesModuleSpiSecuritySubmodule;

public class SecuritySpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder
            .forModules(
                    SecurityModule.class,
                    FixturesModuleSpiSecuritySubmodule.class
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
