package org.incode.domainapp.example.dom.spi.security.fixture.roles.sub;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntity;

public class RoleAndPermissions_create_exampleHideNonTenantedEntityDescription extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "example-hide-nontenantedentity-description";

    public RoleAndPermissions_create_exampleHideNonTenantedEntityDescription() {
        super(ROLE_NAME, "Hide access to ExampleEntity#description property");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        newMemberPermissions(
                ApplicationPermissionRule.VETO,
                ApplicationPermissionMode.VIEWING,
                NonTenantedEntity.class,
                "description");
    }

}
