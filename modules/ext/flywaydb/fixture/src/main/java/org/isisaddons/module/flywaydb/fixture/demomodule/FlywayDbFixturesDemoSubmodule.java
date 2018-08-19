package org.isisaddons.module.flywaydb.fixture.demomodule;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.flywaydb.fixture.demomodule.fixturescripts.FlywayDbDemoObject_tearDown;

@XmlRootElement(name = "module")
public class FlywayDbFixturesDemoSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new FlywayDbDemoObject_tearDown();

    }
}
