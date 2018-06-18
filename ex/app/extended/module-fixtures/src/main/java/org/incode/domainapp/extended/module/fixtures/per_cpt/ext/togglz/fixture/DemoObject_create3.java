package org.incode.domainapp.extended.module.fixtures.per_cpt.ext.togglz.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectRepository;

public class DemoObject_create3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private DemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, demoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private DemoObjectRepository demoObjects;

}
