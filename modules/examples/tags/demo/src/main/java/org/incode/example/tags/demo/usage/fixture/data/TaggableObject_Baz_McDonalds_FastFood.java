package org.incode.example.tags.demo.usage.fixture.data;

public class TaggableObject_Baz_McDonalds_FastFood extends AbstractTaggableObjectFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Baz", "McDonalds", "Fast food", executionContext);
    }

}
