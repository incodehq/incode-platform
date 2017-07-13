package org.isisaddons.module.tags.fixture.scripts.entities;

import org.isisaddons.module.tags.fixture.dom.ExampleTaggableEntities;
import org.isisaddons.module.tags.fixture.dom.ExampleTaggableEntity;
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
    private ExampleTaggableEntities exampleTaggableEntities;

}
