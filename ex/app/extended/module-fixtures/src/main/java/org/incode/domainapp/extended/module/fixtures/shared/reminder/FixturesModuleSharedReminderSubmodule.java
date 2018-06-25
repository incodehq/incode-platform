package org.incode.domainapp.extended.module.fixtures.shared.reminder;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.reminder.fixture.DemoReminder_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedReminderSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoReminder_tearDown();
    }
}
