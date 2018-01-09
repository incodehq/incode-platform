package domainapp.modules.simple.integtests;

import javax.inject.Inject;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import domainapp.modules.simple.SimpleModuleManifest;

public abstract class SimpleModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(SimpleModuleManifest.BUILDER
                        .withAdditionalModules(FakeDataModule.class)
                        .withConfigurationProperty("isis.objects.editing","false")
        );
    }

    @Inject
    protected FakeDataService fakeDataService;


}
