package domainapp.modules.exampledom.spi.security.fixture.example.nontenanted;

public class BopNonTenantedEntity extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", executionContext);
    }

}
