package org.incode.domainapp.example.dom.dom.tags.fixture.data;

public class ExampleTaggableEntity_Bip_CocaCola_Drink extends AbstractEntityFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bip", "Coca Cola", "Drink", executionContext);
    }

}
