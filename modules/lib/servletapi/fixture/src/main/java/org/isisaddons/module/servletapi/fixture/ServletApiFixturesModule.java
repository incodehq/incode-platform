package org.isisaddons.module.servletapi.fixture;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.servletapi.fixture.scripts.ServletApiDemoObject_tearDown;

@XmlRootElement(name = "module")
public class ServletApiFixturesModule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new ServletApiDemoObject_tearDown();
    }
}
