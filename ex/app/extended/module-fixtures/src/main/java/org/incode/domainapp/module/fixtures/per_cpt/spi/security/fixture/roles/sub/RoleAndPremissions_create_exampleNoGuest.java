package org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.roles.sub;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

import org.incode.domainapp.module.fixtures.per_cpt.spi.security.dom.demo.nontenanted.NonTenantedEntity;

/**
 * Intended to conflict with {@link RoleAndPermissions_create_exampleGuest}
 */
public class RoleAndPremissions_create_exampleNoGuest extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "example-no-guest";

    public RoleAndPremissions_create_exampleNoGuest() {
        super(ROLE_NAME, "No access to example dom");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        newPackagePermissions(
                ApplicationPermissionRule.VETO,
                ApplicationPermissionMode.VIEWING,
                NonTenantedEntity.class.getPackage().getName());
    }

}
