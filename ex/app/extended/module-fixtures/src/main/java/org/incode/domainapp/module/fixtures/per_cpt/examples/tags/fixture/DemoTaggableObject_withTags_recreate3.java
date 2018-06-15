package org.incode.domainapp.example.dom.dom.tags.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class DemoTaggableObject_withTags_recreate3 extends FixtureScript {


    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new DemoTaggableObjects_tearDown());
        executionContext.executeChild(this, new DemoTaggableObject_withTags_create3());
    }

}
