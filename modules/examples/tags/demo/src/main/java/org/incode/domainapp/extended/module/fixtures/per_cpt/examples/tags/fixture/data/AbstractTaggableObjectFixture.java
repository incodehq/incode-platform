package org.incode.example.alias.demo.examples.tags.fixture.data;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.alias.demo.examples.tags.dom.demo.DemoTaggableObject;
import org.incode.example.alias.demo.examples.tags.dom.demo.DemoTaggableObjectMenu;

public abstract class AbstractTaggableObjectFixture extends FixtureScript {

    protected DemoTaggableObject create(
            final String name, final String brand, final String sector,
            final ExecutionContext executionContext) {
        final DemoTaggableObject entity = demoTaggableObjectMenu.createTaggableEntity(name, brand, sector);
        executionContext.add(this, name, entity);
        return entity;
    }

    @javax.inject.Inject
    private DemoTaggableObjectMenu demoTaggableObjectMenu;

}
