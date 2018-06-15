package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.alias.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.demo.fixture.DemoObjectData;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.alias.dom.AliasForDemoObject;
import org.incode.example.alias.dom.impl.T_addAlias;

public class DemoObject_withAliases_recreate2 extends FixtureScript {

    T_addAlias mixinAddAlias(final Object aliased) {
        return factoryService.mixin(AliasForDemoObject._addAlias.class, aliased);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new DemoObject_withAliases_tearDown());
        executionContext.executeChild(this, new DemoObjectData.PersistScript());

        executionContext.executeChild(this, new DemoObject_withAliases_create2());
    }


}
