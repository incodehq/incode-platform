package org.incode.domainapp.module.fixtures.per_cpt.examples.tags.fixture.data;

public class DemoTaggableObject_Bar_Pepsi_Drink extends AbstractTaggableObjectFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bar", "Pepsi", "Drink", executionContext);
    }

}
