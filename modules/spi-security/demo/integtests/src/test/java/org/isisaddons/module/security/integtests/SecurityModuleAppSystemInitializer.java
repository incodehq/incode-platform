package org.isisaddons.module.security.integtests;

import com.google.common.collect.Lists;
import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;
import org.isisaddons.module.security.SecurityModule;

import java.util.List;
import java.util.Map;

public class SecurityModuleAppSystemInitializer {
    
    private SecurityModuleAppSystemInitializer(){}

    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new AppManifest() {
                        @Override
                        public List<Class<?>> getModules() {
                            return Lists.<Class<?>>newArrayList(
                                    SecurityModule.class
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
                    .with(new IsisConfigurationForJdoIntegTests())
                    .build()
                    .setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }
}
