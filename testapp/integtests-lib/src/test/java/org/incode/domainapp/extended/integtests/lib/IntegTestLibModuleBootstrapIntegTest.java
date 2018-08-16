package org.incode.domainapp.extended.integtests.lib;

import javax.inject.Inject;

import org.junit.Test;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.metamodel.MetaModelService4;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public class IntegTestLibModuleBootstrapIntegTest extends IntegrationTestAbstract3 {

    public IntegTestLibModuleBootstrapIntegTest() {
        super(new IntegTestsLibModule());
    }


    @Test
    public void xxx() throws Exception {
        serializes_module();
    }

    @Test
    public void yyy() throws Exception {
        serializes_module();
    }


    private void serializes_module() throws Exception {

        final Module module = metaModelService4.getAppManifest2().getModule();

        final String s = jaxbService.toXml(module);
        System.out.println(s);
    }

    @Inject
    MetaModelService4 metaModelService4;

    @Inject
    JaxbService jaxbService;
}
