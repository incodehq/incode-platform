package org.incode.platform.dom.docfragment.integtests.tests;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.xactn.TransactionService;

import org.incode.domainapp.example.dom.dom.docfragment.fixture.DemoCustomer_and_DemoInvoice_and_DocFragment_recreateSome;
import org.incode.platform.dom.docfragment.integtests.DocFragmentModuleIntegTestAbstract;
import org.incode.domainapp.example.dom.demo.dom.invoicewithatpath.DemoInvoiceWithAtPath;
import org.incode.domainapp.example.dom.demo.fixture.data.DemoInvoiceData;

import static org.assertj.core.api.Assertions.assertThat;

public class DocFragmentService_IntegTest extends DocFragmentModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;

    @Inject
    ServiceRegistry2 serviceRegistry2;


    @Before
    public void setUp() throws Exception {
        // given
        fixtureScripts.runFixtureScript(new DemoCustomer_and_DemoInvoice_and_DocFragment_recreateSome(), null);
        transactionService.nextTransaction();
    }


    public static class Render2 extends DocFragmentService_IntegTest {

        @Test
        public void happy_case() throws Exception {
            // given
            final DemoInvoiceWithAtPath invoice1 = DemoInvoiceData.Invoice1.findUsing(serviceRegistry2);
            assertThat(invoice1.getRendered()).isNull();

            // when
            invoice1.render(invoice1.default0Render());

            // then
            assertThat(invoice1.getRendered()).isEqualTo("The invoice will be due on the 31-Jan-2017, payable in 30 days");
        }
    }



}