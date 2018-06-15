package org.incode.domainapp.example.dom.spi.security.fixture.roles.sub;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntity;
import org.incode.domainapp.example.dom.spi.security.dom.demo.tenanted.TenantedEntity;

public class RoleAndPermissions_create_exampleGuest extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "example-guest";

    public RoleAndPermissions_create_exampleGuest() {
        super(ROLE_NAME, "Read only access to example dom");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        newPackagePermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.VIEWING,
                NonTenantedEntity.class.getPackage().getName(),
                TenantedEntity.class.getPackage().getName()
                );
    }

}
