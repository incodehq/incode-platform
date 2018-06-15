package org.incode.platform.lib.docx.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

import org.incode.domainapp.module.fixtures.per_cpt.lib.docx.dom.CustomerConfirmation;
import org.incode.domainapp.module.fixtures.shared.order.dom.DemoOrder;
import org.incode.domainapp.module.fixtures.shared.order.dom.DemoOrderMenu;
import org.incode.domainapp.module.fixtures.shared.order.fixture.DemoOrderAndOrderLine_recreate4_hardcodedData;
import org.incode.platform.lib.docx.integtests.DocxModuleIntegTestAbstract;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class CustomerConfirmation_IntegTest extends DocxModuleIntegTestAbstract {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new DemoOrderAndOrderLine_recreate4_hardcodedData());
    }

    @Inject
    private DemoOrderMenu orders;

    @Inject
    private CustomerConfirmation customerConfirmation;

    private DemoOrder order;

    @Before
    public void setUp() throws Exception {
        final List<DemoOrder> all = wrap(orders).listAllDemoOrders();
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