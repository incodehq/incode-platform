package org.incode.domainapp.extended.integtests.examples.docfragment.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.integtests.examples.docfragment.dom.docfragment.fixture.DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_create;

public class DocFragmentAppAppManifestWithFixtures extends DocFragmentAppAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_create.class);
    }
}
