package org.incode.domainapp.example.dom.spi.security.fixture.roles;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntity;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

public class ExampleHideNonTenantedEntityDescriptionRoleAndPermissions extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "example-hide-nontenantedentity-description";

    public ExampleHideNonTenantedEntityDescriptionRoleAndPermissions() {
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
