package domainapp.application.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import domainapp.application.manifest.FlywayDbDemoAppManifest;

public abstract class DomainAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(new FlywayDbDemoAppManifest());
    }

}
