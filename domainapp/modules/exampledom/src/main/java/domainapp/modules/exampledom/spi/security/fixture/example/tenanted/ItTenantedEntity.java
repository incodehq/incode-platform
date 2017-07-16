package domainapp.modules.exampledom.spi.security.fixture.example.tenanted;

public class ItTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it", "/it", executionContext);
    }

}
