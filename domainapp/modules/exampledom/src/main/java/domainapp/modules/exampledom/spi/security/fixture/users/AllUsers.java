package domainapp.modules.exampledom.spi.security.fixture.users;

public class AllUsers extends AbstractUserFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new BobUser());
        executionContext.executeChild(this, new DickUser());
        executionContext.executeChild(this, new GuestUser());
        executionContext.executeChild(this, new JoeUser());
        executionContext.executeChild(this, new SvenUser());

        executionContext.executeChild(this, new ConflictedUser());

        executionContext.executeChild(this, new BillNonTenantedUser());
        executionContext.executeChild(this, new BertTenantedUser());
    }

}
