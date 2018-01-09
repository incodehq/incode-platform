package domainapp.appdefn;

import org.apache.isis.applib.AppManifestAbstract;

public class DomainAppAppManifestNoFlywayDb extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifest.BUILDER
            .withConfigurationProperty(
                    "isis.persistor.datanucleus.impl.javax.jdo.PersistenceManagerFactoryClass",
                    "org.datanucleus.api.jdo.JDOPersistenceManagerFactory")
            .withConfigurationProperty(
                    "isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateAll",
                    "true")
            ;

    public DomainAppAppManifestNoFlywayDb() {
        super(BUILDER);
    }

}
