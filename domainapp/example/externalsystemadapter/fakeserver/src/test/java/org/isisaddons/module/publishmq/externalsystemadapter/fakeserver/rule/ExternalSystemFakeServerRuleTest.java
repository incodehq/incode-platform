package org.isisaddons.module.publishmq.externalsystemadapter.fakeserver.rule;

import java.util.List;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import org.apache.isis.core.unittestsupport.soap.SoapEndpointPublishingRule;

import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.DemoObject;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.DemoObjectService;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.PostResponse;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.Update;
import org.isisaddons.module.publishmq.externalsystemadapter.fakeserver.ExternalSystemFakeServer;
import org.isisaddons.module.publishmq.externalsystemadapter.wsdl.ExternalSystemWsdl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExternalSystemFakeServerRuleTest {

    @Rule
    public SoapEndpointPublishingRule serverRule =
            new SoapEndpointPublishingRule(ExternalSystemFakeServer.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ExternalSystemFakeServer externalSystemFakeServer;
    private DemoObject externalSystemContract;

    @Before
    public void setUp() throws Exception {

        externalSystemFakeServer = serverRule.getEndpointImplementor(ExternalSystemFakeServer.class);
        final String endpointAddress = serverRule.getEndpointAddress(ExternalSystemFakeServer.class);

        final DemoObjectService externalSystemService =
                new DemoObjectService(ExternalSystemWsdl.getWsdl());
        externalSystemContract = externalSystemService.getDemoObjectOverSOAP();

        BindingProvider provider = (BindingProvider) externalSystemContract;
        provider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                endpointAddress
        );

    }

    @Test
    public void t01_posts() throws Exception {

        // given
        final Update update = new Update();
        update.setMessageId(UUID.randomUUID().toString());
        update.setName("Fred");
        update.setDescription("Freddie Mercury");

        // when
        PostResponse response = externalSystemContract.post(update);

        // then
        final List<Update> updates = externalSystemFakeServer.control().getUpdates();
        assertThat(updates.size(), is(1));

        assertThat(updates.get(0).getName(), is(update.getName()));
        assertThat(updates.get(0).getDescription(), is(update.getDescription()));

    }

    @Test
    public void t02_server_is_reused() throws Exception {

        // given
        final Update update = new Update();
        update.setMessageId(UUID.randomUUID().toString());
        update.setName("Brian");
        update.setDescription("Brian May");

        // when
        PostResponse response = externalSystemContract.post(update);

        // then
        final List<Update> updates = externalSystemFakeServer.control().getUpdates();
        assertThat(updates.size(), is(2));

        assertThat(updates.get(1).getName(), is(update.getName()));
        assertThat(updates.get(1).getDescription(), is(update.getDescription()));
    }

    @Test
    public void t03_can_clear() throws Exception {

        // given
        assertThat(externalSystemFakeServer.control().getUpdates().size(), is(2));

        // when
        externalSystemFakeServer.control().clear();

        // then
        assertThat(externalSystemFakeServer.control().getUpdates().size(), is(0));
    }

}
