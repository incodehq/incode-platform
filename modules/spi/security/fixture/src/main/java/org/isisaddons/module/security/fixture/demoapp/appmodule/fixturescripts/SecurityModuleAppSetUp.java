package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.demo.Tenanted_and_NonTenanted_Entities;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.dom.NonTenantedEntities;
import org.isisaddons.module.security.seed.SeedUsersAndRolesFixtureScript;

public class SecurityModuleAppSetUp extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new SecurityModuleAppTearDown());
        executionContext.executeChild(this, new SeedUsersAndRolesFixtureScript());

        // roles and perms
        executionContext.executeChild(this, new SecurityModuleExampleUsersAndRoles());


        //  example entities
        executionContext.executeChild(this, new Tenanted_and_NonTenanted_Entities());

    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private NonTenantedEntities exampleNonTenantedEntities;

}
