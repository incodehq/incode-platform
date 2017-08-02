package org.incode.domainapp.example.dom.ext.flywaydb.fixture.data;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.ext.flywaydb.dom.FlywayDemoObject;
import org.incode.domainapp.example.dom.ext.flywaydb.dom.FlywayDemoObjectMenu;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class FlywayDemoObjectMenu_create extends FixtureScript {

    /**
     * Name of the object (required)
     */
    @Getter @Setter
    private String name;

    /**
     * The created simple object (output).
     */
    @Getter
    private FlywayDemoObject flywayDemoObject;


    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.flywayDemoObject = wrap(flywayDemoObjectMenu).create(name);
        ec.addResult(this, flywayDemoObject);
    }

    @javax.inject.Inject
    FlywayDemoObjectMenu flywayDemoObjectMenu;

}
