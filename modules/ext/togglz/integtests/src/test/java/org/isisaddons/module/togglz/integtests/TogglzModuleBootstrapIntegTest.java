package org.isisaddons.module.togglz.integtests;

import javax.inject.Inject;

import org.junit.Test;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.metamodel.MetaModelService4;

public class TogglzModuleBootstrapIntegTest extends TogglzModuleIntegTestAbstract {

    @Test
    public void serializes_module() throws Exception {

        final Module module = metaModelService4.getAppManifest2().getModule();

        final String s = jaxbService.toXml(module);
        System.out.println(s);
    }

    @Inject
    MetaModelService4 metaModelService4;

    @Inject
    JaxbService jaxbService;
}