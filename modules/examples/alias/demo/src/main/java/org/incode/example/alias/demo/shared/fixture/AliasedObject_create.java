package org.incode.example.alias.demo.shared.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.alias.demo.shared.dom.AliasedObject;
import org.incode.example.alias.demo.shared.dom.AliasedObjectMenu;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class AliasedObject_create extends FixtureScript {

    @Getter @Setter
    private String name;

    /**
     * The created simple object (output).
     */
    @Getter
    private AliasedObject aliasedObject;


    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.aliasedObject = wrap(aliasDemoObjectMenu).createDemoObject(name);
        ec.addResult(this, aliasedObject);
    }

    @javax.inject.Inject
    AliasedObjectMenu aliasDemoObjectMenu;

}
