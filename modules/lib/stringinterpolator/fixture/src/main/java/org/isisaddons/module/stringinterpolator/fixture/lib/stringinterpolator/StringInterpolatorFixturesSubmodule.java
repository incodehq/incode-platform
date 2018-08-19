package org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.scripts.OgnlDemoReminder_tearDown;

@XmlRootElement(name = "module")
public class StringInterpolatorFixturesSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new OgnlDemoReminder_tearDown();
    }

}
