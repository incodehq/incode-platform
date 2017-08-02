package org.incode.domainapp.example.dom.ext.flywaydb.fixture.scenario;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.ext.flywaydb.dom.FlywayDemoObject;
import org.incode.domainapp.example.dom.ext.flywaydb.fixture.data.FlywayDemoObjectMenu_create;
import org.incode.domainapp.example.dom.ext.flywaydb.fixture.teardown.FlywayDemoModuleTearDown;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class RecreateFlywayDemoObjects extends FixtureScript {

    public final List<String> NAMES = Collections.unmodifiableList(Arrays.asList(
            "Foo", "Bar", "Baz", "Frodo", "Froyo", "Fizz", "Bip", "Bop", "Bang", "Boo"));

    /**
     * The number of objects to create, up to 10; optional, defaults to 3.
     */
    @Getter @Setter
    private Integer number;

    /**
     * The simpleobjects created by this fixture (output).
     */
    @Getter
    private final List<FlywayDemoObject> flywayDemoObjects = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 3);

        // validate
        if(number < 0 || number > NAMES.size()) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", NAMES.size()));
        }

        // execute
        ec.executeChild(this, new FlywayDemoModuleTearDown());
        for (int i = 0; i < number; i++) {
            final String name = NAMES.get(i);
            final FlywayDemoObjectMenu_create fs = new FlywayDemoObjectMenu_create().setName(name);
            ec.executeChild(this, fs.getName(), fs);
            flywayDemoObjects.add(fs.getFlywayDemoObject());
        }
    }

}
