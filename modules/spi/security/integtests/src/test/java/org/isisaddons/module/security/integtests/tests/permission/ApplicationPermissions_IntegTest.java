package org.isisaddons.module.security.integtests.tests.permission;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionRepository;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.SecurityModuleAppTearDown;
import org.isisaddons.module.security.integtests.SecurityModuleIntegTestAbstract;

public class ApplicationPermissions_IntegTest extends SecurityModuleIntegTestAbstract {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpData() throws Exception {
        runFixtureScript(new SecurityModuleAppTearDown());
    }

    @Inject
    ApplicationPermissionRepository applicationPermissionRepository;

    public static class Xxx extends ApplicationPermissions_IntegTest {

        @Ignore("TODO")
        @Test
        public void happyCase() throws Exception {

            // when

            // then

        }
    }

}