package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

public class UserRole_create_Joe_exampleGuestRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Joe_exampleGuestRole() {
        super(ApplicationUser_create_Joe.USER_NAME, RoleAndPermissions_create_exampleGuest.ROLE_NAME);
    }
}
