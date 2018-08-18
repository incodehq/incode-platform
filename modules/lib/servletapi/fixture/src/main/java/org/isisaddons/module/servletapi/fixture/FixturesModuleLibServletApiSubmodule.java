package org.isisaddons.module.servletapi.fixture;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

@XmlRootElement(name = "module")
public class FixturesModuleLibServletApiSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new ServletApiDemoObject_tearDown();
    }
}
