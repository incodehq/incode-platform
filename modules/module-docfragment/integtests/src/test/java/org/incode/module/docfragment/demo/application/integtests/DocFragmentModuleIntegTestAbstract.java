package org.incode.module.docfragment.demo.application.integtests;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.fakedata.dom.FakeDataService;
import org.isisaddons.module.freemarker.dom.service.FreeMarkerService;

import org.incode.module.docfragment.demo.application.manifest.DocFragmentAppAppManifest;

public abstract class DocFragmentModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(new DocFragmentAppAppManifest() {

            @Override
            protected void overrideConfigurationProperties(final Map<String, String> configurationProperties) {
                configurationProperties.put(FreeMarkerService.JODA_SUPPORT_KEY, "true");
            }

            @Override protected void overrideAdditionalServices(final List<Class<?>> additionalServices) {
                additionalServices.add(FakeDataService.class);
            }

        });
    }

}
