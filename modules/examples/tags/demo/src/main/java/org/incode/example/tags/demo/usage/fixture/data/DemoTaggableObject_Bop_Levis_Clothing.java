package org.incode.example.tags.demo.usage.fixture.data;

public class DemoTaggableObject_Bop_Levis_Clothing extends AbstractTaggableObjectFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", "Levi's", "Clothing", executionContext);
    }

}
