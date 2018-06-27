package org.incode.example.tags.demo.usage.fixture.data;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.tags.demo.usage.dom.demo.TaggableObject;
import org.incode.example.tags.demo.usage.dom.demo.TaggableObjectMenu;

public abstract class AbstractTaggableObjectFixture extends FixtureScript {

    protected TaggableObject create(
            final String name, final String brand, final String sector,
            final ExecutionContext executionContext) {
        final TaggableObject entity = taggableObjectMenu.createTaggableEntity(name, brand, sector);
        executionContext.add(this, name, entity);
        return entity;
    }

    @javax.inject.Inject
    private TaggableObjectMenu taggableObjectMenu;

}
