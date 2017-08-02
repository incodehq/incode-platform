package domainapp.modules.exampledom.spi.security.fixture.example.tenanted;

public class ItMilTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it/mil", "/it/mil", executionContext);
    }

}
