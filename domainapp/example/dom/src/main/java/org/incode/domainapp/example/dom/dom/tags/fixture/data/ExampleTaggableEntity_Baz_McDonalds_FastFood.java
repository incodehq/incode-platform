package org.incode.domainapp.example.dom.dom.tags.fixture.data;

public class ExampleTaggableEntity_Baz_McDonalds_FastFood extends AbstractEntityFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Baz", "McDonalds", "Fast food", executionContext);
    }

}
