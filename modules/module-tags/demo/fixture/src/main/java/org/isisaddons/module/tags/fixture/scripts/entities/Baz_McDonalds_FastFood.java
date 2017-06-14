package org.isisaddons.module.tags.fixture.scripts.entities;

public class Baz_McDonalds_FastFood extends AbstractEntityFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Baz", "McDonalds", "Fast food", executionContext);
    }

}
