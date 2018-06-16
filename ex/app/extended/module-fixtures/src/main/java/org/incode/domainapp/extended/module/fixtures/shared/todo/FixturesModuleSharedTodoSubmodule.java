package org.incode.domainapp.extended.module.fixtures.shared.todo;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.todo.fixture.DemoToDoItem_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedTodoSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoToDoItem_tearDown();
    }
}
