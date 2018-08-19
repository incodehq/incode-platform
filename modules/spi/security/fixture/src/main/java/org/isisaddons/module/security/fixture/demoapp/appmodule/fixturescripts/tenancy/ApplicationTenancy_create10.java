package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy;

import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.AbstractTenancyFixtureScript;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.ApplicationTenancy_create_France;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.ApplicationTenancy_create_FranceLyon;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.ApplicationTenancy_create_FranceNice;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.ApplicationTenancy_create_FranceParis;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.ApplicationTenancy_create_Italy;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.ApplicationTenancy_create_ItalyMilan;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.ApplicationTenancy_create_ItalyRome;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.ApplicationTenancy_create_Sweden;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub.ApplicationTenancy_create_SwedenStockholm;
import org.isisaddons.module.security.seed.scripts.GlobalTenancy;

public class ApplicationTenancy_create10 extends AbstractTenancyFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new GlobalTenancy());

        executionContext.executeChild(this, new ApplicationTenancy_create_Italy());
        executionContext.executeChild(this, new ApplicationTenancy_create_ItalyMilan());
        executionContext.executeChild(this, new ApplicationTenancy_create_ItalyRome());

        executionContext.executeChild(this, new ApplicationTenancy_create_France());
        executionContext.executeChild(this, new ApplicationTenancy_create_FranceParis());
        executionContext.executeChild(this, new ApplicationTenancy_create_FranceLyon());
        executionContext.executeChild(this, new ApplicationTenancy_create_FranceNice());

        executionContext.executeChild(this, new ApplicationTenancy_create_Sweden());
        executionContext.executeChild(this, new ApplicationTenancy_create_SwedenStockholm());
    }

}
