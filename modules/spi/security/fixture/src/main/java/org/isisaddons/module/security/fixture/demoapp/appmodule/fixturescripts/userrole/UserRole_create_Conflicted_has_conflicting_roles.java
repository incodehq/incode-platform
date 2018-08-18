package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

public class UserRole_create_Conflicted_has_conflicting_roles extends AbstractUserRoleFixtureScript {
    public UserRole_create_Conflicted_has_conflicting_roles() {
        super(ApplicationUser_Conflicted.USER_NAME,
                RoleAndPermissions_create_exampleGuest.ROLE_NAME,
                RoleAndPremissions_create_exampleNoGuest.ROLE_NAME);
    }
}
