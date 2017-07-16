package domainapp.modules.exampledom.spi.security.fixture.example.tenanted;

public class NullTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Null tenanted", null, executionContext);
    }

}
