package domainapp.modules.exampledom.spi.security.fixture.example.nontenanted;

public class BazNonTenantedEntity extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Baz", executionContext);
    }

}
