package org.isisaddons.module.security.seed.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class GlobalTenancy extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Global";
    public static final String TENANCY_PATH = "/";

    @Override
    protected void execute(FixtureScript.ExecutionContext executionContext) {
        create(TENANCY_NAME, TENANCY_PATH, null, executionContext);
    }

}
