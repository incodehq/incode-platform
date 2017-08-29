package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoleRepository;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserRepository;

public abstract class AbstractUserRoleFixtureScript extends FixtureScript {

    private final String userName;
    private final List<String> roleNames;

    public AbstractUserRoleFixtureScript(
            final String userName,
            final String... roleNames) {
        this.userName = userName;
        this.roleNames = Collections.unmodifiableList(Arrays.asList(roleNames));
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        for (String roleName : roleNames) {
            addUserToRole(userName, roleName, executionContext);
        }
    }

    protected ApplicationUser addUserToRole(
            final String userName,
            final String roleName,
            final ExecutionContext executionContext) {
        final ApplicationUser user = applicationUserRepository.findOrCreateUserByUsername(userName);
        final ApplicationRole applicationRole = applicationRoles.findByName(roleName);
        if(applicationRole != null) {
            user.addRole(applicationRole);
        }
        executionContext.addResult(this, roleName, applicationRole);
        return user;
    }

    @javax.inject.Inject
    private ApplicationUserRepository applicationUserRepository;
    @javax.inject.Inject
    private ApplicationRoleRepository applicationRoles;

}
