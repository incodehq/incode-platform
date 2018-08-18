package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SecurityModuleExampleUsersAndRoles extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {


        // roles and perms
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleGuest());
        executionContext.executeChild(this, new RoleAndPremissions_create_exampleNoGuest());
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleRegularRole());
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleFixtureScripts());
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleHideNonTenantedEntityDescription());

        executionContext.executeChild(this, new RolesAndPermissions_create2());

        // users, tenancies
        executionContext.executeChild(this, new ApplicationTenancy_create10());
        executionContext.executeChild(this, new AllUsers());

        // user/role
        executionContext.executeChild(this, new UserRole_create_SvenUser_IsisSecurityAdminRole());

        executionContext.executeChild(this, new UserRole_create_Bob_isisSecurityAdminRole());
        executionContext.executeChild(this, new UserRole_create_Bob_exampleHideEntityDescriptionRole());

        executionContext.executeChild(this, new UserRole_create_Dick_exampleRegularRole());
        executionContext.executeChild(this, new UserRole_create_Dick_isisSecurityModuleRegularRole());

        executionContext.executeChild(this, new UserRole_create_Guest_exampleGuestRole());
        executionContext.executeChild(this, new UserRole_create_Guest_IsisSecurityModuleRegularRole());

        executionContext.executeChild(this, new UserRole_create_Joe_exampleGuestRole());
        executionContext.executeChild(this, new UserRole_create_Joe_isisSecurityModuleRegularRole());


        executionContext.executeChild(this, new UserRole_create_Conflicted_has_conflicting_roles());

        executionContext.executeChild(this, new UserRole_create_Bert_exampleRegularRole());
        executionContext.executeChild(this, new UserRole_create_Bert_isisSecurityModuleRegularRole());
        executionContext.executeChild(this, new UserRole_create_Bill_exampleRegularRole());
        executionContext.executeChild(this, new UserRole_create_Bill_IsisSecurityModuleRegularRole());


    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private NonTenantedEntities exampleNonTenantedEntities;

}
