package org.incode.domainapp.example.dom.dom.tags.fixture.data;

public class Bar_Pepsi_Drink extends AbstractEntityFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bar", "Pepsi", "Drink", executionContext);
    }

}
