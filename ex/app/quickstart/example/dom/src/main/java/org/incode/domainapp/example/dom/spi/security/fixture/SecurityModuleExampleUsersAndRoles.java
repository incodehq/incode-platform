package org.incode.domainapp.example.dom.spi.security.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.example.dom.spi.security.fixture.roles.RolesAndPermissions_create2;
import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleFixtureScripts;
import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleGuest;
import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleHideNonTenantedEntityDescription;
import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPremissions_create_exampleNoGuest;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.ApplicationTenancy_create10;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Bert_exampleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Bert_isisSecurityModuleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Bill_IsisSecurityModuleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Bill_exampleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Bob_exampleHideEntityDescriptionRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Bob_isisSecurityAdminRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Conflicted_has_conflicting_roles;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Dick_exampleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Dick_isisSecurityModuleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Guest_IsisSecurityModuleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Guest_exampleGuestRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Joe_exampleGuestRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_Joe_isisSecurityModuleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.userrole.UserRole_create_SvenUser_IsisSecurityAdminRole;
import org.incode.domainapp.example.dom.spi.security.fixture.users.AllUsers;

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
