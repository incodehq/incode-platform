package org.incode.domainapp.example.dom.spi.security.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.seed.SeedUsersAndRolesFixtureScript;

import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.example.dom.spi.security.fixture.example.AllExampleEntities;

public class SecurityModuleAppSetUp extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new SecurityModuleAppTearDown());
        executionContext.executeChild(this, new SeedUsersAndRolesFixtureScript());



        // roles and perms
        executionContext.executeChild(this, new SecurityModuleExampleUsersAndRoles());


        //  example entities
        executionContext.executeChild(this, new AllExampleEntities());

    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private NonTenantedEntities exampleNonTenantedEntities;

}
