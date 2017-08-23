package domainapp.appdefn.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import domainapp.appdefn.DomainAppAppManifestNoFlywayDb;

public abstract class DomainAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(DomainAppAppManifestNoFlywayDb.BUILDER);
    }

}
