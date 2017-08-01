package org.isisaddons.metamodel.paraname8.integtests;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

import domainapp.modules.exampledom.metamodel.paraname8.ExampleDomMetaModelParaname8Module;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on the current thread,
 * initialized with ToDo app's domain services. 
 */
public class Paraname8ModuleSystemInitializer {
    
    private Paraname8ModuleSystemInitializer(){}

    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new SimpleAppSystemBuilder().build().setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }

    private static class SimpleAppSystemBuilder extends IsisSystemForTest.Builder {

        public SimpleAppSystemBuilder() {
            withLoggingAt(org.apache.log4j.Level.INFO);
            with(new IsisConfigurationForJdoIntegTests());
            with(new AppManifest() {
                @Override
                public List<Class<?>> getModules() {
                    return Lists.newArrayList(ExampleDomMetaModelParaname8Module.class);
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
                    Map<String, String> props = Maps.newHashMap();
                    props.put(
                            "isis.reflector.facets.include",
                            org.isisaddons.metamodel.paraname8.NamedFacetOnParameterParaname8Factory.class.getName());

                    return props;
                }
            });
        }
    }

}
