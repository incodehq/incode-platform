package org.incode.domainapp.example.dom.ext.togglz.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectRepository;

public class DemoObject_recreate3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new DemoObject_tearDown3());

        // create
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
