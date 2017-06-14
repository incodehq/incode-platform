package org.isisaddons.module.security.fixture.scripts.tenancy;

import org.isisaddons.module.security.seed.scripts.GlobalTenancy;

public class AllTenancies extends AbstractTenancyFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new GlobalTenancy());

        executionContext.executeChild(this, new ItalyTenancy());
        executionContext.executeChild(this, new MilanTenancy());
        executionContext.executeChild(this, new RomeTenancy());

        executionContext.executeChild(this, new FranceTenancy());
        executionContext.executeChild(this, new ParisTenancy());
        executionContext.executeChild(this, new LyonTenancy());
        executionContext.executeChild(this, new NiceTenancy());

        executionContext.executeChild(this, new SwedenTenancy());
        executionContext.executeChild(this, new StockholmTenancy());
    }

}
