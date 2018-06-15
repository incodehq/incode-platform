package org.incode.extended.integtests.examples.country;

import org.junit.Before;
import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.example.country.CountryExampleDomManifest;
import org.incode.example.country.fixture.teardown.CountryModule_tearDown;

public abstract class CountryModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(CountryExampleDomManifest.BUILDER
                        // TODO: suspect this is not needed, so commenting it out to see...
                        //.withAdditionalServices(ModuleFixtureScriptsSpecificationProvider.class)
                        .build());
    }

    @Before
    public void cleanUpFromPreviousTest() {
        runFixtureScript(new CountryModule_tearDown());
    }


//    @DomainService(nature = NatureOfService.DOMAIN)
//    public static class ModuleFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
//        @Override
//        public FixtureScriptsSpecification getSpecification() {
//            return FixtureScriptsSpecification.builder("org.incode.example.country").with(
//                    FixtureScripts.MultipleExecutionStrategy.EXECUTE_ONCE_BY_VALUE).build();
//        }
//    }
}
