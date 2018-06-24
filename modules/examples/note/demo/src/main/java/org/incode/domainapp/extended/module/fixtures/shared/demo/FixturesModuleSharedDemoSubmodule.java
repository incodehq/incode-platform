package org.incode.domainapp.extended.module.fixtures.shared.demo;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObject_tearDown();

    }
}
