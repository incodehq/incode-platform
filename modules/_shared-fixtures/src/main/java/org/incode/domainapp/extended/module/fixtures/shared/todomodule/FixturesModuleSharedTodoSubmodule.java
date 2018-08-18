package org.incode.domainapp.extended.module.fixtures.shared.todo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.FullCalendar2ApplibModule;

import org.incode.domainapp.extended.module.fixtures.shared.todo.fixture.DemoToDoItem_tearDown;

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
