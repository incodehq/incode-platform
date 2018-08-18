package org.isisaddons.module.security.integtests.tests.tenancy;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancyRepository;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ApplicationTenancyRepository_IntegTest extends SecurityModuleIntegTestAbstract {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    ApplicationTenancyRepository applicationTenancyRepository;

    ApplicationTenancy globalTenancy;

    @Before
    public void setUpData() throws Exception {
        runFixtureScript(new SecurityModuleAppTearDown());

        globalTenancy = applicationTenancyRepository.findByPathCached("/");
    }

    public static class NewTenancy extends ApplicationTenancyRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            final List<ApplicationTenancy> before = applicationTenancyRepository.allTenancies();
            assertThat(before.size(), is(0));
            sessionManagementService.nextSession();

            // when
            final ApplicationTenancy applicationTenancy = applicationTenancyRepository.newTenancy("uk", "/uk", globalTenancy);
            assertThat(applicationTenancy.getName(), is("uk"));
            sessionManagementService.nextSession();

            // then
            final List<ApplicationTenancy> after = applicationTenancyRepository.allTenancies();
            assertThat(after.size(), is(1));
        }

        @Test
        public void alreadyExists() throws Exception {

            // given
            applicationTenancyRepository.newTenancy("UK", "/uk", globalTenancy);
            sessionManagementService.nextSession();

            // when
            applicationTenancyRepository.newTenancy("UK", "/uk", globalTenancy);
            sessionManagementService.nextSession();

            //then
            assertThat(applicationTenancyRepository.allTenancies().size(), is(1));
        }
    }

    public static class FindByName extends ApplicationTenancyRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            applicationTenancyRepository.newTenancy("portugal", "/po", globalTenancy);
            applicationTenancyRepository.newTenancy("uk", "/uk", globalTenancy);
            applicationTenancyRepository.newTenancy("zambia", "/za", globalTenancy);
            sessionManagementService.nextSession();

            // when
            final ApplicationTenancy uk = applicationTenancyRepository.findByNameCached("uk");

            // then
            Assert.assertThat(uk, is(not(nullValue())));
            Assert.assertThat(uk.getName(), is("uk"));
        }

        @Test
        public void whenDoesntMatch() throws Exception {

            // given
            applicationTenancyRepository.newTenancy("portugal", "/po", globalTenancy);
            applicationTenancyRepository.newTenancy("uk", "/uk", globalTenancy);

            // when
            final ApplicationTenancy nonExistent = applicationTenancyRepository.findByNameCached("france");

            // then
            Assert.assertThat(nonExistent, is(nullValue()));
        }
    }


    public static class FindByNameOrPathMatching extends ApplicationTenancyRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            applicationTenancyRepository.newTenancy("portugal", "/po", globalTenancy);
            applicationTenancyRepository.newTenancy("uk", "/uk", globalTenancy);
            applicationTenancyRepository.newTenancy("zambia", "/za", globalTenancy);

            // when, then
            Assert.assertThat(applicationTenancyRepository.findByNameOrPathMatchingCached("*").size(), is(3));
            Assert.assertThat(applicationTenancyRepository.findByNameOrPathMatchingCached("u").size(), is(2));
            Assert.assertThat(applicationTenancyRepository.findByNameOrPathMatchingCached("k").size(), is(1));
        }

        @Test
        public void whenDoesntMatch() throws Exception {

            // given
            applicationTenancyRepository.newTenancy("portugal", "/po", globalTenancy);
            applicationTenancyRepository.newTenancy("uk", "/uk", globalTenancy);

            // when
            final List<ApplicationTenancy> results = applicationTenancyRepository.findByNameOrPathMatchingCached(
                    "goat");

            // then
            Assert.assertThat(results.size(), is(0));
        }
    }


    public static class AllTenancyRepository extends ApplicationTenancyRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            applicationTenancyRepository.newTenancy("portugal", "/po", globalTenancy);
            applicationTenancyRepository.newTenancy("uk", "/uk", globalTenancy);

            // when
            final List<ApplicationTenancy> after = applicationTenancyRepository.allTenancies();

            // then
            Assert.assertThat(after.size(), is(2));
        }
    }


}