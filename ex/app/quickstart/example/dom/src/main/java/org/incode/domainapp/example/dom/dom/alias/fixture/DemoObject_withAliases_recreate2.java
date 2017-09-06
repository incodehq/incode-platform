package org.incode.domainapp.example.dom.dom.alias.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.data.DemoObjectData;
import org.incode.domainapp.example.dom.dom.alias.dom.AliasForDemoObject;
import org.incode.module.alias.dom.impl.T_addAlias;

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
