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
package org.incode.module.communications.demo.module.fixture.scenario;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.incode.module.communications.demo.module.fixture.data.doctypes.DocumentTypesAndTemplatesFixture;
import org.incode.module.communications.demo.module.fixture.data.doctypes.RenderingStrategiesFixture;
import org.incode.module.communications.demo.module.fixture.demodata.DemoCustomersFixture;
import org.incode.module.communications.demo.module.fixture.teardown.DemoModuleTearDown;
import org.incode.module.country.fixture.CountriesRefData;

public class DemoModuleFixture extends DiscoverableFixtureScript {

    public DemoModuleFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // teardown
        executionContext.executeChild(this, new DemoModuleTearDown());

        // prereqs
    	executionContext.executeChild(this, new CountriesRefData());
        executionContext.executeChild(this, new RenderingStrategiesFixture());
        executionContext.executeChild(this, new DocumentTypesAndTemplatesFixture());
    	queryResultsCache.resetForNextTransaction();

    	executionContext.executeChild(this, new DemoCustomersFixture());
    }

    @Inject
    QueryResultsCache queryResultsCache;

}
