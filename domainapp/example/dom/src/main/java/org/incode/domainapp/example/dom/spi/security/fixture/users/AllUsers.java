package org.incode.domainapp.example.dom.spi.security.fixture.users;

public class AllUsers extends AbstractUserFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new ApplicationUser_create_Bob());
        executionContext.executeChild(this, new ApplicationUser_create_Dick());
        executionContext.executeChild(this, new ApplicationUser_create_Guest());
        executionContext.executeChild(this, new ApplicationUser_create_Joe());
        executionContext.executeChild(this, new ApplicationUser_create_Sven());

        executionContext.executeChild(this, new ApplicationUser_Conflicted());

        executionContext.executeChild(this, new ApplicationUser_create_Bill());
        executionContext.executeChild(this, new ApplicationUser_create_Bert_in_Italy());
    }

}
