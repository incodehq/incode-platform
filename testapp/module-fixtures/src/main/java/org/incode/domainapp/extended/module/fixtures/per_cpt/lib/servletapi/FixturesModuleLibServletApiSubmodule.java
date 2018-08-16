package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.fixture.ServletApiDemoObject_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleLibServletApiSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new ServletApiDemoObject_tearDown();
    }
}
