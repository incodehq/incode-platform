package org.incode.example.classification.demo.shared.demowithatpath;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.classification.demo.shared.demowithatpath.fixture.SomeClassifiedObject_tearDown;

@XmlRootElement(name = "module")
public class ClassificationDemoSharedDemoWithAtPathSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new SomeClassifiedObject_tearDown();
    }
}
