package org.isisaddons.module.docx.integtests;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.junit.BeforeClass;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

import org.isisaddons.module.docx.DocxModule;

import domainapp.modules.exampledom.lib.docx.ExampleDomLibDocxModule;

public abstract class DocxModuleIntegTestAbstract extends IntegrationTestAbstract {

    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging-integtest.properties");
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new IsisConfigurationForJdoIntegTests())
                    .with(new AppManifest() {
                        @Override
                        public List<Class<?>> getModules() {
                            return Lists.<Class<?>>newArrayList(
                                    DocxModule.class,
                                    ExampleDomLibDocxModule.class
                                    );
                        }

                        @Override
                        public List<Class<?>> getAdditionalServices() {
                            return null;
                        }

                        @Override
                        public String getAuthenticationMechanism() {
                            return null;
                        }

                        @Override
                        public String getAuthorizationMechanism() {
                            return null;
                        }

                        @Override
                        public List<Class<? extends FixtureScript>> getFixtures() {
                            return null;
                        }

                        @Override
                        public Map<String, String> getConfigurationProperties() {
                            return null;
                        }
                    })
                    .build()
                    .setUpSystem();
            IsisSystemForTest.set(isft);
        }

        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

}
