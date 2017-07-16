package domainapp.modules.exampledom.lib.docx.fixture;

import java.math.BigDecimal;
import domainapp.modules.exampledom.lib.docx.dom.demo.Order;
import domainapp.modules.exampledom.lib.docx.dom.demo.Orders;
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
