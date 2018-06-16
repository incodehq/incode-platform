package org.incode.domainapp.extended.integtests.spi.security.integtests.role;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoleRepository;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.SecurityModuleAppTearDown;
import org.incode.domainapp.extended.integtests.spi.security.SecurityModuleAppIntegTestAbstract;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ApplicationRoleRepository_IntegTest extends SecurityModuleAppIntegTestAbstract {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new SecurityModuleAppTearDown());
    }

    @Inject
    ApplicationRoleRepository applicationRoleRepository;


    public static class NewRole extends ApplicationRoleRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            final List<ApplicationRole> before = applicationRoleRepository.allRoles();
            assertThat(before.size(), is(0));

            // when
            final ApplicationRole applicationRole = applicationRoleRepository.newRole("fred", null);
            assertThat(applicationRole.getName(), is("fred"));

            // then
            final List<ApplicationRole> after = applicationRoleRepository.allRoles();
            assertThat(after.size(), is(1));
        }

        @Test
        public void alreadyExists() throws Exception {
            // given
            applicationRoleRepository.newRole("guest", null);

            // when
            applicationRoleRepository.newRole("guest", null);
            
            // then
            assertThat(applicationRoleRepository.allRoles().size(), is(1));
        }

    }

    public static class FindByName extends ApplicationRoleRepository_IntegTest {

        @Before
        public void setUpData() throws Exception {
            scenarioExecution().install(new SecurityModuleAppTearDown());
        }

        @Test
        public void happyCase() throws Exception {

            // given
            applicationRoleRepository.newRole("guest", null);
            applicationRoleRepository.newRole("root", null);

            // when
            nextSession();
            final ApplicationRole guest = applicationRoleRepository.findByNameCached("guest");

            // then
            assertThat(guest, is(not(nullValue())));
            assertThat(guest.getName(), is("guest"));
        }

        @Test
        public void whenDoesntMatch() throws Exception {

            // given
            applicationRoleRepository.newRole("guest", null);
            applicationRoleRepository.newRole("root", null);
            nextSession();

            // when
            final ApplicationRole nonExistent = applicationRoleRepository.findByNameCached("admin");

            // then
            assertThat(nonExistent, is(nullValue()));
        }
    }

    public static class Find extends ApplicationRoleRepository_IntegTest {

        @Before
        public void setUpData() throws Exception {
            scenarioExecution().install(new SecurityModuleAppTearDown());
        }

        @Test
        public void happyCase() throws Exception {

            // given
            applicationRoleRepository.newRole("guest", null);
            applicationRoleRepository.newRole("root", null);

            // when
            nextSession();
            final List<ApplicationRole> result = applicationRoleRepository.findNameContaining("t");

            // then
            assertThat(result.size(), is(2));
            //assertThat(guest.getName(), is("guest"));
        }

        @Test
        public void whenDoesntMatch() throws Exception {

            // given
            applicationRoleRepository.newRole("guest", null);
            applicationRoleRepository.newRole("root", null);
            nextSession();

            // when
            final List<ApplicationRole> result = applicationRoleRepository.findNameContaining("a");

            // then
            assertThat(result.size(), is(0));
            //assertThat(guest.getName(), is("guest"));
        }
    }


    public static class AllTenancies extends ApplicationRoleRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            applicationRoleRepository.newRole("guest", null);
            applicationRoleRepository.newRole("root", null);

            // when
            final List<ApplicationRole> after = applicationRoleRepository.allRoles();

            // then
            assertThat(after.size(), is(2));
        }
    }


}