package org.incode.domainapp.example.dom.spi.security.fixture.roles;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.incode.domainapp.example.dom.spi.security.dom.demonontenanted.NonTenantedEntity;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

/**
 * Intended to conflict with {@link ExampleGuestRoleAndPermissions}
 */
public class ExampleNoGuestRoleAndPremissions extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "example-no-guest";

    public ExampleNoGuestRoleAndPremissions() {
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
