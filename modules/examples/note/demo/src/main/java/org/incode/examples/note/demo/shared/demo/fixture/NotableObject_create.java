package org.incode.examples.note.demo.shared.demo.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.note.demo.shared.demo.dom.NotableObject;
import org.incode.examples.note.demo.shared.demo.dom.NotableObjectMenu;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class NotableObject_create extends FixtureScript {

    @Getter @Setter
    private String name;

    /**
     * The created simple object (output).
     */
    @Getter
    private NotableObject notableObject;


    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.notableObject = wrap(demoObjectMenu).createDemoObject(name);
        ec.addResult(this, notableObject);
    }

    @javax.inject.Inject
    NotableObjectMenu demoObjectMenu;

}
