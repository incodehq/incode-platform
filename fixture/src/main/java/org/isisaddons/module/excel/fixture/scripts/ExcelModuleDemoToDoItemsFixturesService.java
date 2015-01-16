/*
 *  Copyright 2014 Dan Haywood
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
package org.isisaddons.module.excel.fixture.scripts;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

@DomainService
@DomainServiceLayout(named = "Prototyping", menuBar = DomainServiceLayout.MenuBar.SECONDARY)
public class ExcelModuleDemoToDoItemsFixturesService extends FixtureScripts {

    public ExcelModuleDemoToDoItemsFixturesService() {
        super("org.isisaddons.module.excel.fixture.scripts");
    }

    @Override
    public FixtureScript default0RunFixtureScript() {
        return findFixtureScriptFor(RecreateToDoItems.class);
    }

    @Override
    public List<FixtureScript> choices0RunFixtureScript() {
        return super.choices0RunFixtureScript();
    }


    @Prototype
    @MemberOrder(sequence="20")
    public Object installFixturesAndReturnFirst() {
        final List<FixtureResult> results = runFixtureScript(new RecreateToDoItems(), null);
        return results.get(0).getObject();
    }

    @Prototype
    @MemberOrder(sequence="30")
    public void deleteAll() {
        runFixtureScript(new DeleteAllToDoItems(), null);
    }


}
