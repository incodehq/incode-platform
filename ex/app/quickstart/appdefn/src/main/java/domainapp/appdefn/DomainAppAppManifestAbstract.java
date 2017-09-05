package domainapp.appdefn;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;
import org.isisaddons.module.settings.SettingsModule;

import domainapp.appdefn.services.DomainAppAppDefnServicesSubModule;

public abstract class DomainAppAppManifestAbstract extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(

            DomainAppAppDefnServicesSubModule.class,   // glue services

            SecurityModule.class,       // expected by shiro config
            CommandModule.class,        // expected by quartz config
            SettingsModule.class        // expected by togglz

    )
    .withAdditionalServices(
            PasswordEncryptionServiceUsingJBcrypt.class,
            PermissionsEvaluationServiceAllowBeatsVeto.class
    )
    .withConfigurationPropertiesFile(DomainAppAppManifestAbstract.class,
            "isis.properties",
            "authentication_shiro.properties",
            "persistor_datanucleus.properties",
            "viewer_restfulobjects.properties",
            "viewer_wicket.properties"
    )
    .withConfigurationProperty("isis.viewer.wicket.rememberMe.cookieKey", "DomainAppEncryptionKey")
    ;

    public DomainAppAppManifestAbstract() {
        super(BUILDER);
    }

}
