package domainapp.modules.simple.fixture.data;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dom.impl.FlywayDemoObject;
import domainapp.modules.simple.dom.impl.FlywayDemoObjectMenu;
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
