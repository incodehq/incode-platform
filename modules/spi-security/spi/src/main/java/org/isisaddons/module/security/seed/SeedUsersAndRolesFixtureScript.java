package org.isisaddons.module.security.seed;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.dom.role.ApplicationRoleRepository;
import org.isisaddons.module.security.dom.user.ApplicationUserRepository;
import org.isisaddons.module.security.seed.scripts.GlobalTenancy;
import org.isisaddons.module.security.seed.scripts.IsisApplibFixtureResultsRoleAndPermissions;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminUser;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityFixtureRoleAndPermissions;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

/**
 * This fixture script will be run automatically on start-up by virtue of the fact that the
 * {@link org.isisaddons.module.security.seed.SeedSecurityModuleService} is a
 * {@link org.apache.isis.applib.annotation.DomainService} and calls the setup during its
 * {@link org.isisaddons.module.security.seed.SeedSecurityModuleService#init() init} ({@link javax.annotation.PostConstruct}) method.
 */
public class SeedUsersAndRolesFixtureScript extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        // global tenancy
        executionContext.executeChild(this, new GlobalTenancy());

        // security module
        executionContext.executeChild(this, new IsisModuleSecurityAdminRoleAndPermissions());

        executionContext.executeChild(this, new IsisModuleSecurityFixtureRoleAndPermissions());
        executionContext.executeChild(this, new IsisModuleSecurityRegularUserRoleAndPermissions());

        executionContext.executeChild(this, new IsisModuleSecurityAdminUser());

        // isis applib
        executionContext.executeChild(this, new IsisApplibFixtureResultsRoleAndPermissions());
    }

    //region > injected
    @Inject
    ApplicationRoleRepository applicationRoleRepository;
    @Inject
    ApplicationUserRepository applicationUserRepository;
    //endregion
}
