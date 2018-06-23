package org.incode.example.classification.demo.shared.otherwithatpath;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.classification.demo.shared.otherwithatpath.fixture.OtherObjectWithAtPath_tearDown;

@XmlRootElement(name = "module")
public class ClassificationDemoSharedOtherWithAtPathSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new OtherObjectWithAtPath_tearDown();
    }
}
