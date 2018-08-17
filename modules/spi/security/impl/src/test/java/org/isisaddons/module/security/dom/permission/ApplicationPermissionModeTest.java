package org.isisaddons.module.security.dom.permission;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationPermissionModeTest {

    public static class ToString extends ApplicationPermissionModeTest {

        @Test
        public void happyCase() throws Exception {
            assertThat(ApplicationPermissionMode.CHANGING.toString(), is("CHANGING"));
        }
    }

}
