package org.isisaddons.module.docx.fixture.fixturescripts;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.isisaddons.module.docx.fixture.dom.demoorder.DocxDemoOrder;
import org.isisaddons.module.docx.fixture.dom.demoorder.DocxDemoOrderMenu;

public class DocxDemoOrderAndOrderLine_create4_hardcodedData extends FixtureScript {

    @javax.inject.Inject
    DocxDemoOrderMenu demoOrderMenu;

    @javax.inject.Inject
    ClockService clockService;


    public DocxDemoOrderAndOrderLine_create4_hardcodedData() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // create
        final DocxDemoOrder order = create("1234", "Joe Smith", clockService.now().minusDays(5), "leave in the porch if out, don't deliver after 5pm, expedite if possible", executionContext);

        order.add("TV", BigDecimal.valueOf(543.21), 1);
        order.add("X-Men", BigDecimal.valueOf(12.34), 1);
        order.add("Battery pack", BigDecimal.valueOf(9.99), 3);
        order.add("LED lamp", BigDecimal.valueOf(2.99), 12);
    }

    private DocxDemoOrder create(
            final String number,
            final String customerName,
            final LocalDate date,
            final String preferences,
            final ExecutionContext executionContext) {
        return executionContext.add(this, demoOrderMenu.createDemoOrder(number, customerName, date, preferences));
    }


}
