package domainapp.appdefn.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import domainapp.appdefn.DomainAppAppManifest;

public abstract class DomainAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(
                DomainAppAppManifest.BUILDER
                .withConfigurationProperty(
                        "isis.persistor.datanucleus.impl.javax.jdo.PersistenceManagerFactoryClass",
                        "org.datanucleus.api.jdo.JDOPersistenceManagerFactory"));
    }

}
