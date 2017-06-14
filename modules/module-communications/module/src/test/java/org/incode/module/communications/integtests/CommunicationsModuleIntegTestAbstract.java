package org.incode.module.communications.integtests;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.junit.BeforeClass;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

import org.incode.module.communications.CommunicationsModuleManifest;

public abstract class CommunicationsModuleIntegTestAbstract extends IntegrationTestAbstract {

    @BeforeClass
    public static void initSystem() {
        org.apache.log4j.PropertyConfigurator.configure("logging-integtest.properties");
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new CommunicationsModuleManifest() {
                        @Override
                        public Map<String, String> getConfigurationProperties() {
                            final Map<String, String> map = Maps.newHashMap();
                            Util.withJavaxJdoRunInMemoryProperties(map);
                            Util.withDataNucleusProperties(map);
                            Util.withIsisIntegTestProperties(map);
                            return map;
                        }

                        @Override public List<Class<?>> getAdditionalServices() {
                            return Lists.newArrayList(ModuleFixtureScriptsSpecificationProvider.class);
                        }
                    })

                    .build();
            isft.setUpSystem();
            IsisSystemForTest.set(isft);
        }

        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class ModuleFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
        @Override
        public FixtureScriptsSpecification getSpecification() {
            return FixtureScriptsSpecification.builder("org.incode.module.communications").with(
                    FixtureScripts.MultipleExecutionStrategy.EXECUTE_ONCE_BY_VALUE).build();
        }
    }
}
