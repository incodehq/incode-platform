package org.incode.platform.dom.docfragment.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.dom.docfragment.fixture.DemoCustomer_and_DemoInvoice_and_DocFragment_recreateSome;

/**
 * Run the app but without setting up any fixtures.
 */
public class DocFragmentAppAppManifestWithFixtures extends DocFragmentAppAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoCustomer_and_DemoInvoice_and_DocFragment_recreateSome.class);
    }
}
