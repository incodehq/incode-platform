package domainapp.modules.exampledom.module.tags.fixture.data;

public class Bip_CocaCola_Drink extends AbstractEntityFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bip", "Coca Cola", "Drink", executionContext);
    }

}
