package org.isisaddons.module.security.integtests.permission;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionRepository;
import domainapp.modules.exampledom.spi.security.fixture.SecurityModuleAppTearDown;
import org.isisaddons.module.security.integtests.SecurityModuleAppIntegTest;

public class ApplicationPermissionsIntegTest extends SecurityModuleAppIntegTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new SecurityModuleAppTearDown());
    }

    @Inject
    ApplicationPermissionRepository applicationPermissionRepository;

    public static class Xxx extends ApplicationPermissionsIntegTest {

        @Ignore("TODO")
        @Test
        public void happyCase() throws Exception {

            // when

            // then

        }
    }

}