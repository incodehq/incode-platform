package org.isisaddons.module.stringinterpolator.integtests;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on the current thread,
 * initialized with ToDo app's domain services.
 */
public class StringInterpolatorDemoSystemInitializer {

    private StringInterpolatorDemoSystemInitializer() {
    }

    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if (isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new IsisConfigurationForJdoIntegTests())
                    .with(new AppManifest() {
                        @Override
                        public List<Class<?>> getModules() {
                            return Lists.<Class<?>>newArrayList(
                                    StringInterpolatorModule.class
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
                            Map<String, String> map = Maps.newHashMap();
                            map.put("isis.website", "http://isis.apache.org");
                            return map;
                        }
                    })
                    .build()
                    .setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }
}
