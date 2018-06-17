package org.incode.domainapp.extended.integtests.spi.security.tests.user;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoleRepository;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancyRepository;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserMenu;
import org.isisaddons.module.security.dom.user.ApplicationUserRepository;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.SecurityModuleAppTearDown;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.roles.RolesAndPermissions_create2;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleRegularRole;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.tenancy.ApplicationTenancy_create10;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_France;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_Sweden;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.users.ApplicationUser_create_Sven;
import org.incode.domainapp.extended.integtests.spi.security.SecurityModuleIntegTestAbstract;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class ApplicationUser_IntegTest extends SecurityModuleIntegTestAbstract {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpData() throws Exception {
        runFixtureScript(
                new SecurityModuleAppTearDown(),
                new IsisModuleSecurityAdminRoleAndPermissions(),
                new ApplicationUser_create_Sven()
        );
    }


    @Inject
    ApplicationUserMenu applicationUserMenu;
    @Inject
    ApplicationUserRepository applicationUserRepository;

    ApplicationUser user;

    @Before
    public void setUp() throws Exception {
        user = wrap(applicationUserRepository.findOrCreateUserByUsername(ApplicationUser_create_Sven.USER_NAME));
        assertThat(unwrap(user).getRoles().size(), is(0));

        assertThat(user, is(not(nullValue())));
        assertThat(user.getUsername(), is(ApplicationUser_create_Sven.USER_NAME));
    }

    public static class Username_and_UpdateUsername extends ApplicationUser_IntegTest {

        public static class Username extends Username_and_UpdateUsername {

            @Test
            public void cannotModifyDirectly() throws Exception {

                // then
                expectedExceptions.expect(DisabledException.class);
                expectedExceptions.expectMessage("Reason: Always disabled. Identifier: org.isisaddons.module.security.dom.user.ApplicationUser#username()");

                // when
                user.setUsername("fred");
            }

        }

        public static class UpdateUsername extends Username_and_UpdateUsername {

            @Test
            public void toNewValue() throws Exception {

                // when
                final ApplicationUser updatedUser = user.updateUsername("fred");

                // then
                assertThat(updatedUser, is(unwrap(user)));
                assertThat(updatedUser.getUsername(), is("fred"));
            }

            @Test
            public void cannotSetToNull() throws Exception {

                // then
                expectedExceptions.expect(InvalidException.class);
                expectedExceptions.expectMessage(allOf(
                            containsString("Invalid action argument."),
                            containsString("Reason: 'Username' is mandatory.")
                        ));

                // when
                user.updateUsername(null);
            }
        }

    }

    public static class AtPath_and_UpdateAtPath extends ApplicationUser_IntegTest {

        @Before
        public void setUpTenancies() throws Exception {
            runFixtureScript(
                    new ApplicationTenancy_create10()
            );
            // necessary to lookup again because above fixtures will be installed in a new xactn
            user = wrap(applicationUserRepository.findOrCreateUserByUsername(ApplicationUser_create_Sven.USER_NAME));

            swedenTenancy = applicationTenancyRepository.findByNameCached(ApplicationTenancy_create_Sweden.TENANCY_NAME);
            franceTenancy = applicationTenancyRepository.findByNameCached(ApplicationTenancy_create_France.TENANCY_NAME);

            assertThat(swedenTenancy, is(notNullValue()));
            assertThat(franceTenancy, is(notNullValue()));
        }

        @Inject
        ApplicationTenancyRepository applicationTenancyRepository;

        ApplicationTenancy swedenTenancy;
        ApplicationTenancy franceTenancy;

        public static class AtPath extends AtPath_and_UpdateAtPath {

            @Test
            public void cannotModifyDirectly() throws Exception {

                // then
                expectedExceptions.expect(DisabledException.class);
                expectedExceptions.expectMessage("Reason: Always disabled. Identifier: org.isisaddons.module.security.dom.user.ApplicationUser#atPath()");

                // when
                user.setAtPath(swedenTenancy.getPath());
            }

        }

        public static class UpdateAtPath extends AtPath_and_UpdateAtPath {

            @Test
            public void fromNullToNewValue() throws Exception {

                // given
                assertThat(user.getAtPath(), is(nullValue()));

                // when
                final ApplicationUser updatedUser = user.updateAtPath(swedenTenancy.getPath());

                // then
                assertThat(updatedUser, is(unwrap(user)));
                assertThat(updatedUser.getAtPath(), is(swedenTenancy.getPath()));
            }

            @Test
            public void fromValueToNewValue() throws Exception {

                // given
                user.updateAtPath(swedenTenancy.getPath());
                assertThat(user.getAtPath(), is(swedenTenancy.getPath()));

                // when
                user.updateAtPath(franceTenancy.getPath());

                // then
                assertThat(user.getAtPath(), is(franceTenancy.getPath()));
            }

            @Test
            public void fromValueToNull() throws Exception {

                // given
                user.updateAtPath(swedenTenancy.getPath());
                assertThat(user.getAtPath(), is(swedenTenancy.getPath()));

                // when
                user.updateAtPath(null);

                // then
                assertThat(user.getAtPath(), is(nullValue()));
            }

        }

    }

    public static class Roles extends ApplicationUser_IntegTest {

        @Before
        public void setUpRoles() throws Exception {
            runFixtureScript(
                    new RolesAndPermissions_create2()
            );

            // necessary to lookup again because above fixtures will be installed in a new xactn
            user = wrap(applicationUserRepository.findOrCreateUserByUsername(ApplicationUser_create_Sven.USER_NAME));

            adminRole = applicationRoleRepository.findByNameCached(IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
            userRole = applicationRoleRepository.findByNameCached(RoleAndPermissions_create_exampleRegularRole.ROLE_NAME);

            assertThat(adminRole, is(notNullValue()));
            assertThat(userRole, is(notNullValue()));
        }

        @Inject
        ApplicationRoleRepository applicationRoleRepository;

        ApplicationRole adminRole;
        ApplicationRole userRole;

        public static class AddRole extends Roles {

            @Test
            public void whenEmpty() throws Exception {

                // given
                assertThat(user.getRoles().size(), is(0));

                // when
                user.addRole(adminRole);

                // then
                assertThat(user.getRoles().size(), is(1));
                assertThat(user.getRoles(), containsInAnyOrder(adminRole));
            }

            @Test
            public void whenNotEmptyAndAddDifferent() throws Exception {
                // given
                user.addRole(adminRole);
                assertThat(user.getRoles(), containsInAnyOrder(adminRole));
                assertThat(user.getRoles().size(), is(1));

                // when
                user.addRole(userRole);

                // then
                assertThat(user.getRoles().size(), is(2));
                assertThat(user.getRoles(), containsInAnyOrder(adminRole, userRole));
            }

            @Test
            public void whenAlreadyContains() throws Exception {

                // given
                user.addRole(adminRole);
                assertThat(user.getRoles(), containsInAnyOrder(adminRole));
                assertThat(user.getRoles().size(), is(1));

                // when
                user.addRole(adminRole);

                // then
                assertThat(user.getRoles(), containsInAnyOrder(adminRole));
                assertThat(user.getRoles().size(), is(1));
            }
        }

        public static class RemoveRole extends Roles {

            @Test
            public void whenContains() throws Exception {

                // given
                user.addRole(adminRole);
                assertThat(user.getRoles().size(), is(1));

                // when
                user.removeRole(adminRole);

                // then
                assertThat(user.getRoles().size(), is(0));
            }

            @Test
            public void whenDoesNotContain() throws Exception {

                // given
                user.addRole(adminRole);
                assertThat(user.getRoles().size(), is(1));

                // when
                user.removeRole(userRole);

                // then
                assertThat(user.getRoles().size(), is(1));
            }

            @Test
            public void cannotAddUsingSupportingMethod() throws Exception {

                // expect
                expectedException.expect(DisabledException.class);

                // when
                user.addToRoles(userRole);
            }

            @Test
            public void cannotAddDirectly() throws Exception {

                // expect
                expectedException.expect(UnsupportedOperationException.class);

                // when
                user.getRoles().add(userRole);
            }

        }
    }
}