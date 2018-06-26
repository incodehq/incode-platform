package org.incode.example.alias.demo.shared.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.alias.demo.shared.dom.AliasDemoObject;
import org.incode.example.alias.demo.shared.dom.AliasDemoObjectMenu;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class AliasDemoObject_create extends FixtureScript {

    @Getter @Setter
    private String name;

    /**
     * The created simple object (output).
     */
    @Getter
    private AliasDemoObject aliasDemoObject;


    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.aliasDemoObject = wrap(aliasDemoObjectMenu).createDemoObject(name);
        ec.addResult(this, aliasDemoObject);
    }

    @javax.inject.Inject
    AliasDemoObjectMenu aliasDemoObjectMenu;

}
