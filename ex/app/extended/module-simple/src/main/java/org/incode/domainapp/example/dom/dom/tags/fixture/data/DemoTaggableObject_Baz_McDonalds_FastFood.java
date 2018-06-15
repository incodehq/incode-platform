package org.incode.domainapp.example.dom.dom.tags.fixture.data;

public class DemoTaggableObject_Baz_McDonalds_FastFood extends AbstractTaggableObjectFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Baz", "McDonalds", "Fast food", executionContext);
    }

}
