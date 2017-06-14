package org.isisaddons.module.security.dom.permission;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationPermissionRuleTest {

    public static class ToString extends ApplicationPermissionModeTest {

        @Test
        public void happyCase() throws Exception {
            assertThat(ApplicationPermissionRule.ALLOW.toString(), is("ALLOW"));
        }
    }

}
