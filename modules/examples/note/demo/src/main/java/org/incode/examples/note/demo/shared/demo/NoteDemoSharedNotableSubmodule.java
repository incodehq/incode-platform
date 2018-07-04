package org.incode.examples.note.demo.shared.demo;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.note.demo.shared.demo.fixture.NotableObject_tearDown;

@XmlRootElement(name = "module")
public class NoteDemoSharedNotableSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new NotableObject_tearDown();

    }
}
