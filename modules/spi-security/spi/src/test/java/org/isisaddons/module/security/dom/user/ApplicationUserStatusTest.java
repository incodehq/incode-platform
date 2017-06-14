package org.isisaddons.module.security.dom.user;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionModeTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationUserStatusTest {

    public static class ToString extends ApplicationPermissionModeTest {

        @Test
        public void happyCase() throws Exception {
            assertThat(ApplicationUserStatus.DISABLED.toString(), is("DISABLED"));
        }
    }

    public static class Parse extends ApplicationPermissionModeTest {

        @Test
        public void whenTrue() throws Exception {
            assertThat(ApplicationUserStatus.parse(Boolean.TRUE), is(ApplicationUserStatus.ENABLED));
        }

        @Test
        public void whenFalse() throws Exception {
            assertThat(ApplicationUserStatus.parse(Boolean.FALSE), is(ApplicationUserStatus.DISABLED));
        }

        @Test
        public void whenNull() throws Exception {
            assertThat(ApplicationUserStatus.parse(Boolean.FALSE), is(ApplicationUserStatus.DISABLED));
        }
    }

}