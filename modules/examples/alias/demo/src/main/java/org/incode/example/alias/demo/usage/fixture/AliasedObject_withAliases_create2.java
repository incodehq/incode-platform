package org.incode.example.alias.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.alias.demo.shared.dom.AliasedObject;
import org.incode.example.alias.demo.shared.fixture.AliasedObjectData;
import org.incode.example.alias.demo.usage.dom.AliasForAliasDemoObject;
import org.incode.example.alias.demo.usage.spi.aliastype.AliasTypeDemoEnum;
import org.incode.example.alias.dom.impl.T_addAlias;

public class AliasedObject_withAliases_create2 extends FixtureScript {

    T_addAlias mixinAddAlias(final Object aliased) {
        return factoryService.mixin(AliasForAliasDemoObject._addAlias.class, aliased);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        final AliasedObject foo = AliasedObjectData.Foo.findUsing(serviceRegistry);
        final AliasedObject bar = AliasedObjectData.Bar.findUsing(serviceRegistry);

        wrap(mixinAddAlias(foo)).$$("/uk", AliasTypeDemoEnum.GENERAL_LEDGER, "12345");
        wrap(mixinAddAlias(foo)).$$("/uk", AliasTypeDemoEnum.DOCUMENT_MANAGEMENT, "http://docserver.mycompany/url/12345");
        wrap(mixinAddAlias(foo)).$$("/uk", AliasTypeDemoEnum.PERSONNEL_SYSTEM, "12345");

        wrap(mixinAddAlias(bar)).$$("/uk", AliasTypeDemoEnum.GENERAL_LEDGER, "98765");
        wrap(mixinAddAlias(bar)).$$("/nl", AliasTypeDemoEnum.GENERAL_LEDGER, "12345");
        wrap(mixinAddAlias(foo)).$$("/nl", AliasTypeDemoEnum.DOCUMENT_MANAGEMENT, "http://docserver.mycompany/url/12345");
    }


}
