package org.incode.example.tags.demo.shared.fixture.data;

public class TaggableObject_Bop_Levis_Clothing extends AbstractTaggableObjectFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", "Levi's", "Clothing", executionContext);
    }

}
