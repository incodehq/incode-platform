package org.isisaddons.module.flywaydb.fixture.todomodule;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.flywaydb.fixture.todomodule.fixturescripts.DemoToDoItem_tearDown;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.FullCalendar2ApplibModule;

@XmlRootElement(name = "module")
public class FixturesModuleSharedTodoSubmodule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new FullCalendar2ApplibModule() // because ToDoItem implements FullCalendarable
        );
    }

    @Override public FixtureScript getTeardownFixture() {
        return new DemoToDoItem_tearDown();
    }

}
