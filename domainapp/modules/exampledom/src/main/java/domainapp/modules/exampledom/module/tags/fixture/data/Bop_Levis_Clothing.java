package domainapp.modules.exampledom.module.tags.fixture.data;

public class Bop_Levis_Clothing extends AbstractEntityFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", "Levi's", "Clothing", executionContext);
    }

}
