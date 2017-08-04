package org.incode.domainapp.example.dom.demo.fixture.setup;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class DemoObjectMenu_create extends FixtureScript {

    /**
     * Name of the object (required)
     */
    @Getter @Setter
    private String name;

    /**
     * The created simple object (output).
     */
    @Getter
    private DemoObject flywayDemoObject;


    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.flywayDemoObject = wrap(flywayDemoObjectMenu).create(name);
        ec.addResult(this, flywayDemoObject);
    }

    @javax.inject.Inject
    DemoObjectMenu flywayDemoObjectMenu;

}
