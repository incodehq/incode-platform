package org.isisaddons.module.docx.integtests;

import java.util.List;
import javax.inject.Inject;
import org.isisaddons.module.docx.fixture.dom.Order;
import org.isisaddons.module.docx.fixture.dom.Orders;
import org.isisaddons.module.docx.fixture.dom.templates.CustomerConfirmation;
import org.isisaddons.module.docx.fixture.scripts.DocxModuleAppSetupFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class CustomerConfirmationTest extends DocxModuleIntegTest {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new DocxModuleAppSetupFixture());
    }

    @Inject
    private Orders orders;

    @Inject
    private CustomerConfirmation customerConfirmation;

    private Order order;

    @Before
    public void setUp() throws Exception {
        final List<Order> all = wrap(orders).listAll();
        assertThat(all.size(), is(1));

        order = all.get(0);
    }

    @Test
    public void downloadCustomerConfirmation() throws Exception {
        final Blob blob = customerConfirmation.downloadCustomerConfirmation(order);
        Assert.assertThat(blob.getName(), is("customerConfirmation-1234.docx"));
        Assert.assertThat(blob.getMimeType().getBaseType(), is("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        Assert.assertThat(blob.getBytes().length, is(greaterThan(20000)));
    }

    @Test
    public void downloadCustomerConfirmationInputHtml() throws Exception {
        final Clob blob = customerConfirmation.downloadCustomerConfirmationInputHtml(order);
        Assert.assertThat(blob.getName(), is("customerConfirmation-1234.html"));
        Assert.assertThat(blob.getMimeType().getBaseType(), is("text/html"));
        Assert.assertThat(blob.getChars().length(), is(greaterThan(800)));
    }

}