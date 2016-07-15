/*
 *  Copyright 2014~2015 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.alias.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.module.alias.dom.impl.alias.T_addAlias;
import org.incode.module.alias.fixture.dom.aliasdemoobject.AliasDemoObject;
import org.incode.module.alias.fixture.dom.aliasdemoobject.AliasDemoObjectMenu;
import org.incode.module.alias.fixture.dom.aliaslink.AliasLinkForDemoObject;
import org.incode.module.alias.fixture.dom.aliastype.AliasTypeDemoEnum;
import org.incode.module.alias.fixture.scripts.teardown.AliasDemoObjectsTearDownFixture;

public class AliasDemoObjectsFixture extends DiscoverableFixtureScript {

    //region > injected services
    @javax.inject.Inject
    AliasDemoObjectMenu aliasDemoObjectMenu;
    //endregion

    //region > constructor
    public AliasDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }
    //endregion

    //region > mixins
    T_addAlias mixinAddAlias(final Object aliased) {
        return container.mixin(AliasLinkForDemoObject._addAlias.class, aliased);
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new AliasDemoObjectsTearDownFixture());

        final AliasDemoObject foo = create("Foo", executionContext);
        wrap(mixinAddAlias(foo)).$$("/uk", AliasTypeDemoEnum.GENERAL_LEDGER, "12345");
        wrap(mixinAddAlias(foo)).$$("/uk", AliasTypeDemoEnum.DOCUMENT_MANAGEMENT, "http://docserver.mycompany/url/12345");
        wrap(mixinAddAlias(foo)).$$("/uk", AliasTypeDemoEnum.PERSONNEL_SYSTEM, "12345");

        final AliasDemoObject bar = create("Bar", executionContext);
        wrap(mixinAddAlias(bar)).$$("/uk", AliasTypeDemoEnum.GENERAL_LEDGER, "98765");

        final AliasDemoObject baz = create("Baz", executionContext);
        wrap(mixinAddAlias(bar)).$$("/nl", AliasTypeDemoEnum.GENERAL_LEDGER, "12345");
        wrap(mixinAddAlias(foo)).$$("/nl", AliasTypeDemoEnum.DOCUMENT_MANAGEMENT, "http://docserver.mycompany/url/12345");
    }


    // //////////////////////////////////////

    private AliasDemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(aliasDemoObjectMenu).create(name));
    }


}
