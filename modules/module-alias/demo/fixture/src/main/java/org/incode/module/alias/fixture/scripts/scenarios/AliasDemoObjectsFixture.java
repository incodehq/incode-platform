package org.incode.module.alias.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.module.alias.dom.impl.T_addAlias;
import org.incode.module.alias.fixture.app.alias.AliasForDemoObject;
import org.incode.module.alias.fixture.app.spiimpl.aliastype.AliasTypeDemoEnum;
import org.incode.module.alias.fixture.dom.demo.DemoObject;
import org.incode.module.alias.fixture.dom.demo.DemoObjectMenu;
import org.incode.module.alias.fixture.scripts.teardown.AliasDemoObjectsTearDownFixture;

public class AliasDemoObjectsFixture extends DiscoverableFixtureScript {

    //region > injected services
    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;
    //endregion

    //region > constructor
    public AliasDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }
    //endregion

    //region > mixins
    T_addAlias mixinAddAlias(final Object aliased) {
        return container.mixin(AliasForDemoObject._addAlias.class, aliased);
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new AliasDemoObjectsTearDownFixture());

        final DemoObject foo = create("Foo", executionContext);
        wrap(mixinAddAlias(foo)).$$("/uk", AliasTypeDemoEnum.GENERAL_LEDGER, "12345");
        wrap(mixinAddAlias(foo)).$$("/uk", AliasTypeDemoEnum.DOCUMENT_MANAGEMENT, "http://docserver.mycompany/url/12345");
        wrap(mixinAddAlias(foo)).$$("/uk", AliasTypeDemoEnum.PERSONNEL_SYSTEM, "12345");

        final DemoObject bar = create("Bar", executionContext);
        wrap(mixinAddAlias(bar)).$$("/uk", AliasTypeDemoEnum.GENERAL_LEDGER, "98765");

        final DemoObject baz = create("Baz", executionContext);
        wrap(mixinAddAlias(bar)).$$("/nl", AliasTypeDemoEnum.GENERAL_LEDGER, "12345");
        wrap(mixinAddAlias(foo)).$$("/nl", AliasTypeDemoEnum.DOCUMENT_MANAGEMENT, "http://docserver.mycompany/url/12345");
    }


    // //////////////////////////////////////

    private DemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(demoObjectMenu).create(name));
    }


}
