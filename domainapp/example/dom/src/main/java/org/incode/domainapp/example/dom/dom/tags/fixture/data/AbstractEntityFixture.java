package org.incode.domainapp.example.dom.dom.tags.fixture.data;

import org.incode.domainapp.example.dom.dom.tags.dom.demo.ExampleTaggableEntityMenu;
import org.incode.domainapp.example.dom.dom.tags.dom.demo.ExampleTaggableEntity;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class AbstractEntityFixture extends FixtureScript {

    protected ExampleTaggableEntity create(
            final String name, final String brand, final String sector,
            final ExecutionContext executionContext) {
        final ExampleTaggableEntity entity = exampleTaggableEntities.create(name, brand, sector);
        executionContext.add(this, name, entity);
        return entity;
    }

    @javax.inject.Inject
    private ExampleTaggableEntityMenu exampleTaggableEntities;

}
