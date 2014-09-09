/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.module.docx.fixture.scripts;

import java.math.BigDecimal;
import org.isisaddons.module.docx.fixture.dom.Order;
import org.isisaddons.module.docx.fixture.dom.Orders;
import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

public class DocxModuleAppSetupFixture extends FixtureScript {

    public DocxModuleAppSetupFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        execute(new DocxModuleAppTeardownFixture(), executionContext);

        // create
        final Order order = create("1234", "Joe Smith", clockService.now().minusDays(5), "leave in the porch if out, don't deliver after 5pm, expedite if possible", executionContext);

        order.add("TV", BigDecimal.valueOf(543.21), 1);
        order.add("X-Men", BigDecimal.valueOf(12.34), 1);
        order.add("Battery pack", BigDecimal.valueOf(9.99), 3);
        order.add("LED lamp", BigDecimal.valueOf(2.99), 12);
    }

    // //////////////////////////////////////

    private Order create(
            final String number,
            final String customerName,
            final LocalDate date,
            final String preferences,
            final ExecutionContext executionContext) {
        return executionContext.add(this, orders.create(number, customerName, date, preferences));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private Orders orders;

    @javax.inject.Inject
    private ClockService clockService;

}
