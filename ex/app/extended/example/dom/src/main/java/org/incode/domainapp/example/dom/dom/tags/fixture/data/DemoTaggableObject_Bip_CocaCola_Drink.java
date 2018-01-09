package org.incode.domainapp.example.dom.dom.tags.fixture.data;

public class DemoTaggableObject_Bip_CocaCola_Drink extends AbstractTaggableObjectFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bip", "Coca Cola", "Drink", executionContext);
    }

}
