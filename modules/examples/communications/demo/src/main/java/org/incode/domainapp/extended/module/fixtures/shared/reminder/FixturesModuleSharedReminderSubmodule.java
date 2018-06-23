package org.incode.examples.commchannel.demo.shared.reminder;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.reminder.fixture.DemoReminder_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedReminderSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoReminder_tearDown();
    }
}
