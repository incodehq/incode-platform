package org.isisaddons.module.flywaydb.fixture.demomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.flywaydb.fixture.demomodule.dom.FlywayDbDemoObject;
import org.isisaddons.module.flywaydb.fixture.demomodule.dom.FlywayDbDemoObjectMenu;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class FlywayDbDemoObject_create extends FixtureScript {

    @Getter @Setter
    private String name;

    /**
     * The created simple object (output).
     */
    @Getter
    private FlywayDbDemoObject demoObject;


    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.demoObject = wrap(demoObjectMenu).createDemoObject(name);
        ec.addResult(this, demoObject);
    }

    @javax.inject.Inject
    FlywayDbDemoObjectMenu demoObjectMenu;

}
