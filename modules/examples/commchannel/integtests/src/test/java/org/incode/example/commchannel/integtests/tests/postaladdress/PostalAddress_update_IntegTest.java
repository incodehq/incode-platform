package org.incode.example.commchannel.integtests.tests.postaladdress;

import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomerMenu;
import org.incode.example.commchannel.demo.usage.fixture.CommChannelCustomer_withCommChannels_tearDown;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.example.commchannel.dom.impl.postaladdress.PostalAddress_update;
import org.incode.example.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assume.assumeThat;

public class PostalAddress_update_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    CommChannelCustomerMenu commChannelDemoObjectMenu;

    CommChannelCustomer fredDemoOwner;
    PostalAddress fredPostalAddress;

    PostalAddress_update mixinUpdate(final PostalAddress postalAddress) {
        return mixin(PostalAddress_update.class, postalAddress);
    }

    @Before
    public void setUpData() throws Exception {


        fixtureScripts.runFixtureScript(new CommChannelCustomer_withCommChannels_tearDown(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Fred");

        wrap(mixinNewPostalAddress(fredDemoOwner)).$$(
                "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA", "UK", "Work", "Fred Smith's work",
                false);

        final SortedSet<CommunicationChannel> communicationChannels = wrap(mixinCommunicationChannels(fredDemoOwner)).$$();
        fredPostalAddress = (PostalAddress) communicationChannels.first();

    }

    public static class ActionImplementationIntegrationTest extends PostalAddress_update_IntegTest {

        @Test
        public void when_lookup_geocode_or_does_not_loookup() throws Exception {

            // these tests are too flaky
            assumeThat(false, is(true));

            assumeThat(isInternetReachable(), is(true));

            // when
            wrap(mixinUpdate(fredPostalAddress)).$$(
                    "45", "High Street", "Oxford", null, "OX1",
                    "UK", true);

            // then
            assertThat(fredPostalAddress.getName()).isEqualTo("45 High St, Oxford OX1, UK");
            assertThat(fredPostalAddress.getFormattedAddress()).isEqualTo("45 High St, Oxford OX1, UK");
            assertThat(fredPostalAddress.getGeocodeApiResponseAsJson()).isNotNull();
            assertThat(fredPostalAddress.getLatLng()).matches("51.752[\\d][\\d][\\d][\\d],-1.250[\\d][\\d][\\d][\\d]");
            assertThat(fredPostalAddress.getPlaceId()).isEqualTo("Eho0NSBIaWdoIFN0LCBPeGZvcmQgT1gxLCBVSw");
            assertThat(fredPostalAddress.getAddressComponents()).isNotNull();

            // and when
            wrap(mixinUpdate(fredPostalAddress)).$$(
                    "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA",
                    "UK", false);

            // then
            assertThat(fredPostalAddress.getAddressLine1()).isEqualTo("Flat 2a");
            assertThat(fredPostalAddress.getAddressLine2()).isEqualTo("45 Penny Lane");
            assertThat(fredPostalAddress.getAddressLine3()).isEqualTo("Allerton");
            assertThat(fredPostalAddress.getAddressLine4()).isEqualTo("Liverpool");
            assertThat(fredPostalAddress.getPostalCode()).isEqualTo("L39 5AA");
            assertThat(fredPostalAddress.getCountry()).isEqualTo("UK");
            assertThat(fredPostalAddress.getFormattedAddress()).isNull();
            assertThat(fredPostalAddress.getGeocodeApiResponseAsJson()).isNull();
            assertThat(fredPostalAddress.getLatLng()).isNull();
            assertThat(fredPostalAddress.getPlaceId()).isNull();
            assertThat(fredPostalAddress.getAddressComponents()).isNull();
        }
    }

    public static class DefaultsIntegrationTest extends PostalAddress_update_IntegTest {

        @Test
        public void defaults_to_corresponding_property() throws Exception {
            assertThat(mixinUpdate(fredPostalAddress).default0$$())
                    .isEqualTo(fredPostalAddress.getAddressLine1());
            assertThat(mixinUpdate(fredPostalAddress).default1$$())
                    .isEqualTo(fredPostalAddress.getAddressLine2());
            assertThat(mixinUpdate(fredPostalAddress).default2$$())
                    .isEqualTo(fredPostalAddress.getAddressLine3());
            assertThat(mixinUpdate(fredPostalAddress).default3$$())
                    .isEqualTo(fredPostalAddress.getAddressLine4());
            assertThat(mixinUpdate(fredPostalAddress).default4$$())
                    .isEqualTo(fredPostalAddress.getPostalCode());
            assertThat(mixinUpdate(fredPostalAddress).default5$$())
                    .isEqualTo(fredPostalAddress.getCountry());
        }

        @Test
        public void default_for_place_id_parameter_when_not_looked_up() throws Exception {
            // when
            final Boolean defaultPlaceId = mixinUpdate(fredPostalAddress).default6$$();
            // then
            assertThat(defaultPlaceId).isNull();
        }

        @Test
        public void default_for_place_id_parameter_when_has_been_looked_up() throws Exception {

            assumeThat(isInternetReachable(), is(true));
            assumeThat(fredPostalAddress.getPlaceId(), is(notNullValue()));

            // given
            wrap(mixinUpdate(fredPostalAddress)).$$(
                    "45", "High Street", "Oxford", null, "OX1",
                    "UK", true);

            // when
            final Boolean defaultPlaceId = mixinUpdate(fredPostalAddress).default6$$();

            // then
            assertThat(defaultPlaceId).isTrue();
        }
    }


    public static class RaisesEventIntegrationTest extends PostalAddress_update_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            PostalAddress_update.DomainEvent ev;

            @Subscribe
            public void on(PostalAddress_update.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            final String newLine1 = fakeDataService.addresses().streetAddressNumber();
            final String newLine2 = fakeDataService.addresses().streetName();
            final String newLine3 = fakeDataService.addresses().city();
            final String newCountry = fakeDataService.addresses().country();
            wrap(mixinUpdate(fredPostalAddress)).$$(
                    newLine1, newLine2, newLine3, null, null, newCountry, true);

            assertThat(testSubscriber.ev.getSource().getPostalAddress()).isSameAs(fredPostalAddress);
            assertThat(testSubscriber.ev.getArguments().get(0)).isEqualTo(newLine1);
            assertThat(testSubscriber.ev.getArguments().get(1)).isEqualTo(newLine2);
            assertThat(testSubscriber.ev.getArguments().get(2)).isEqualTo(newLine3);
            assertThat(testSubscriber.ev.getArguments().get(3)).isNull();
            assertThat(testSubscriber.ev.getArguments().get(4)).isNull();
            assertThat(testSubscriber.ev.getArguments().get(5)).isEqualTo(newCountry);
            assertThat(testSubscriber.ev.getArguments().get(6)).isEqualTo(true);
        }
    }

}