package org.isisaddons.module.tags.fixture.scripts.entities;

public class Bar_Pepsi_Drink extends AbstractEntityFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bar", "Pepsi", "Drink", executionContext);
    }

}
