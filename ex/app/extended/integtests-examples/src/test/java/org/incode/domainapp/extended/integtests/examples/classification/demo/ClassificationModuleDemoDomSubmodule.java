package org.incode.domainapp.extended.integtests.examples.classification.demo;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.demowithatpath.dom.DemoObjectWithAtPath;
import org.incode.domainapp.extended.module.fixtures.shared.otherwithatpath.dom.OtherObjectWithAtPath;

@XmlRootElement(name = "module")
public class ClassificationModuleDemoDomSubmodule extends ModuleAbstract {

    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override
            protected void execute(final ExecutionContext executionContext) {
                deleteFrom(DemoObjectWithAtPath.class);
                deleteFrom(OtherObjectWithAtPath.class);
            }
        };
    }
}
