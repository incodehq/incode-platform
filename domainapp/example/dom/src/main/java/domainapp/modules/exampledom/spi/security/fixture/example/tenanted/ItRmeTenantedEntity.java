package domainapp.modules.exampledom.spi.security.fixture.example.tenanted;

public class ItRmeTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it/rme", "/it/rme", executionContext);
    }

}
