package domainapp.appdefn;

import org.apache.isis.applib.AppManifestAbstract2;

public class DomainAppAppManifestNoFlywayDb extends AppManifestAbstract2 {

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
