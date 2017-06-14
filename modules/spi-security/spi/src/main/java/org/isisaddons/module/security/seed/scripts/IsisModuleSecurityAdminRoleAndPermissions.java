package org.isisaddons.module.security.seed.scripts;

import java.util.Objects;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;

public class IsisModuleSecurityAdminRoleAndPermissions extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "isis-module-security-admin";
    public static final String ORG_ISISADDONS_MODULE_SECURITY_APP = "org.isisaddons.module.security.app";
    public static final String ORG_ISISADDONS_MODULE_SECURITY_DOM = "org.isisaddons.module.security.dom";

    public IsisModuleSecurityAdminRoleAndPermissions() {
        super(ROLE_NAME, "Administer security");
    }


    @Override
    protected void execute(ExecutionContext executionContext) {
        newPackagePermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.CHANGING,
                ORG_ISISADDONS_MODULE_SECURITY_APP,
                ORG_ISISADDONS_MODULE_SECURITY_DOM);
    }

    public static boolean oneOf(String featureFqn) {
        return Objects.equals(featureFqn, ORG_ISISADDONS_MODULE_SECURITY_APP) ||
               Objects.equals(featureFqn, ORG_ISISADDONS_MODULE_SECURITY_DOM);
    }
}
