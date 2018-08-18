package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.security.dom.permission.ApplicationPermission;
import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.user.ApplicationUser;

public class SecurityModuleAppTearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(ApplicationPermission.class);

        // a different design would be to make ApplicationUser#roles cascade delete (ie @Persistent(dependent=true)
        final String schema = schemaOf(ApplicationUser.class);
        deleteFrom(schema, "ApplicationUserRoles");

        deleteFrom(ApplicationRole.class);
        deleteFrom(ApplicationUser.class);

        deleteFrom(ApplicationTenancy.class);
        deleteFrom(NonTenantedEntity.class);
        deleteFrom(TenantedEntity.class);
    }

}
