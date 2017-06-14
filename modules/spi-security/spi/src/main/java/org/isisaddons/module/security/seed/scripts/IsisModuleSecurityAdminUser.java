package org.isisaddons.module.security.seed.scripts;

import java.util.Arrays;

import org.isisaddons.module.security.dom.user.AccountType;

public class IsisModuleSecurityAdminUser extends AbstractUserAndRolesFixtureScript {

    public static final String USER_NAME = "isis-module-security-admin";
    public static final String PASSWORD = "pass";

    public IsisModuleSecurityAdminUser() {
        super(USER_NAME, PASSWORD, null,
                GlobalTenancy.TENANCY_PATH, AccountType.LOCAL,
                Arrays.asList(IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME));
    }
}
