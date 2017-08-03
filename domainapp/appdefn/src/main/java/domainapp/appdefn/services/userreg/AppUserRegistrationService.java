package domainapp.appdefn.services.userreg;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoleRepository;
import org.isisaddons.module.security.userreg.SecurityModuleAppUserRegistrationServiceAbstract;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleFixtureScriptsRoleAndPermissions;
import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleRegularRoleAndPermissions;

/**
 * An override of the default impl of {@link org.apache.isis.applib.services.userreg.UserRegistrationService}
 * that uses {@link ExampleFixtureScriptsRoleAndPermissions#ROLE_NAME}
 * as initial role
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class AppUserRegistrationService extends SecurityModuleAppUserRegistrationServiceAbstract {

    @Override
    protected ApplicationRole getInitialRole() {
        return findRole(ExampleFixtureScriptsRoleAndPermissions.ROLE_NAME);
    }

    @Override
    protected Set<ApplicationRole> getAdditionalInitialRoles() {
        return Collections.singleton(findRole(ExampleRegularRoleAndPermissions.ROLE_NAME));
    }

    private ApplicationRole findRole(final String roleName) {
        return applicationRoleRepository.findByName(roleName);
    }


    @Inject
    private ApplicationRoleRepository applicationRoleRepository;
}
