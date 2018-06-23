package org.incode.examples.commchannel.demo.shared.demo.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.demo.dom.DemoObject;
import org.incode.examples.commchannel.demo.shared.demo.dom.DemoObjectMenu;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class DemoObject_create extends FixtureScript {

    @Getter @Setter
    private String name;

    /**
     * The created simple object (output).
     */
    @Getter
    private DemoObject demoObject;


    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.demoObject = wrap(demoObjectMenu).createDemoObject(name);
        ec.addResult(this, demoObject);
    }

    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;

}
