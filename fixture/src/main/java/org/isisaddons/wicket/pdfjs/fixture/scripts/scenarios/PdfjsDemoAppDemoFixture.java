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
package org.isisaddons.wicket.pdfjs.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.isisaddons.wicket.pdfjs.fixture.scripts.data.DemoObjectsFixture;
import org.isisaddons.wicket.pdfjs.fixture.scripts.teardown.PdfjsDemoAppTearDownFixture;


public class PdfjsDemoAppDemoFixture extends DiscoverableFixtureScript {

    public PdfjsDemoAppDemoFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new PdfjsDemoAppTearDownFixture());

        ec.executeChild(this, new DemoObjectsFixture());
    }


}
