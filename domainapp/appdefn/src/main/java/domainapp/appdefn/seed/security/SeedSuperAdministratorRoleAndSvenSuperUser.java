package domainapp.appdefn.seed.security;

import java.util.Arrays;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;
import org.isisaddons.module.security.seed.scripts.AbstractUserAndRolesFixtureScript;
import org.isisaddons.module.security.seed.scripts.GlobalTenancy;

/**
 * This fixture script will be run automatically on start-up by virtue of the fact that the
 * {@link org.isisaddons.module.security.seed.SeedSecurityModuleService} is a
 * {@link org.apache.isis.applib.annotation.DomainService} and calls the setup during its
 * {@link org.isisaddons.module.security.seed.SeedSecurityModuleService#init() init} ({@link javax.annotation.PostConstruct}) method.
 */
public class SeedSuperAdministratorRoleAndSvenSuperUser extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new DomainAppSuperAdministratorRole());
        executionContext.executeChild(this, new SvenSuperUser());
    }


    public static class DomainAppSuperAdministratorRole extends AbstractRoleAndPermissionsFixtureScript {

        public static final String ROLE_NAME = "domainapp-super-admin";

        public DomainAppSuperAdministratorRole() {
            super(ROLE_NAME, "Super administrator");
        }

        @Override
        protected void execute(FixtureScript.ExecutionContext executionContext) {
            newPackagePermissions(
                    ApplicationPermissionRule.ALLOW,
                    ApplicationPermissionMode.CHANGING,
                    "domainapp",
                    "org");
        }

    }

    public static class SvenSuperUser extends AbstractUserAndRolesFixtureScript {

        public SvenSuperUser() {
            super("sven", "pass", null,
                    GlobalTenancy.TENANCY_PATH, AccountType.LOCAL,
                    Arrays.asList(DomainAppSuperAdministratorRole.ROLE_NAME));
        }
    }

}

