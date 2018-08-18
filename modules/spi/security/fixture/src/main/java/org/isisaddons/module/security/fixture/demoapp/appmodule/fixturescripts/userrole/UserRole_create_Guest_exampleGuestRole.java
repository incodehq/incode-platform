package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

public class UserRole_create_Guest_exampleGuestRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Guest_exampleGuestRole() {
        super(ApplicationUser_create_Guest.USER_NAME, RoleAndPermissions_create_exampleGuest.ROLE_NAME);
    }
}
