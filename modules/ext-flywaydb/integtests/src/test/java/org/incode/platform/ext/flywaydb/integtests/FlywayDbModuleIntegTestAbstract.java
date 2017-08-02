package org.incode.platform.ext.flywaydb.integtests;

import org.junit.BeforeClass;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.platform.ext.flywaydb.integtests.app.FlywayDbDemoAppManifest;

public abstract class FlywayDbModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(FlywayDbDemoAppManifest.BUILDER
                .withAdditionalServices(ModuleFixtureScriptsSpecificationProvider.class)
                .build());
    }

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class ModuleFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
        @Override
        public FixtureScriptsSpecification getSpecification() {
            return FixtureScriptsSpecification.builder("domainapp.modules.simple").with(
                    FixtureScripts.MultipleExecutionStrategy.EXECUTE_ONCE_BY_VALUE).build();
        }
    }
}
