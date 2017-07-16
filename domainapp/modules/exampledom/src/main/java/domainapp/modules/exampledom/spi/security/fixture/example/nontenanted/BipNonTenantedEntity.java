package domainapp.modules.exampledom.spi.security.fixture.example.nontenanted;

public class BipNonTenantedEntity extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bip", executionContext);
    }

}
