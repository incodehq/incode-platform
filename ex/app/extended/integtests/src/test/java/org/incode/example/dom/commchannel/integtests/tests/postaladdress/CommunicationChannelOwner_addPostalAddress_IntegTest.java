package org.incode.example.dom.commchannel.integtests.tests.postaladdress;

import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.config.ConfigurationService;

import org.incode.domainapp.module.fixtures.per_cpt.examples.commchannel.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.domainapp.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.example.commchannel.dom.api.GeocodingService;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.example.commchannel.dom.impl.postaladdress.T_addPostalAddress;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.example.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assume.assumeThat;

public class CommunicationChannelOwner_addPostalAddress_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;
    @Inject
    ConfigurationService configurationService;

    DemoObject fredDemoOwner;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_tearDown(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Fred");
    }


    public static class ActionImplementationIntegrationTest extends
            CommunicationChannelOwner_addPostalAddress_IntegTest {

        @Test
        public void can_create_postal_address_without_looking_up_geocode() throws Exception {

            // given
            final SortedSet<CommunicationChannel> channelsBefore = wrap(mixinCommunicationChannels(fredDemoOwner)).$$();
            assertThat(channelsBefore).hasSize(0);

            // when
            wrap(mixinNewPostalAddress(fredDemoOwner)).$$(
                    "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA", "UK", "Home", "Fred Smith's home",
                    false);

            // then
            final SortedSet<CommunicationChannel> channelsAfter = wrap(mixinCommunicationChannels(fredDemoOwner)).$$();

            assertThat(channelsAfter).hasSize(1);
            final CommunicationChannel communicationChannel = channelsAfter.first();

            assertThat(communicationChannel.getName()).isEqualTo("Flat 2a, 45 Pe..., L39 5AA, UK");
            assertThat(communicationChannel.getPurpose()).isEqualTo("Home");
            assertThat(communicationChannel.getNotes()).isEqualTo("Fred Smith's home");
            assertThat(communicationChannel.getType()).isEqualTo(CommunicationChannelType.POSTAL_ADDRESS);
            assertThat(communicationChannel.getId()).isNotNull();

            assertThat(communicationChannel.getLocation()).isNull();

            assertThat(communicationChannel).isInstanceOf(PostalAddress.class);
            final PostalAddress postalAddress = (PostalAddress)communicationChannel;
            assertThat(postalAddress.getAddressLine1()).isEqualTo("Flat 2a");
            assertThat(postalAddress.getAddressLine2()).isEqualTo("45 Penny Lane");
            assertThat(postalAddress.getAddressLine3()).isEqualTo("Allerton");
            assertThat(postalAddress.getAddressLine4()).isEqualTo("Liverpool");
            assertThat(postalAddress.getPostalCode()).isEqualTo("L39 5AA");
            assertThat(postalAddress.getCountry()).isEqualTo("UK");
            assertThat(postalAddress.getFormattedAddress()).isNull();
            assertThat(postalAddress.getGeocodeApiResponseAsJson()).isNull();
            assertThat(postalAddress.getLatLng()).isNull();
            assertThat(postalAddress.getPlaceId()).isNull();
            assertThat(postalAddress.getAddressComponents()).isNull();
        }

        @Test
        public void can_create_postal_address_and_also_look_up_geocode() throws Exception {

            assumeThat(isInternetReachable(), is(true));
            assumeThat(configurationService.getProperty(GeocodingService.class.getName() + ".apiKey"), is(notNullValue()));

            // given
            final SortedSet<CommunicationChannel> channelsBefore = wrap(mixinCommunicationChannels(fredDemoOwner)).$$();
            assertThat(channelsBefore).hasSize(0);

            // when
            wrap(mixinNewPostalAddress(fredDemoOwner)).$$(
                    "45", "High Street", "Oxford", "Oxfordshire", "OX1", "UK", "Work", "Fred Smith's work", true);

            // then
            final SortedSet<CommunicationChannel> channelsAfter = wrap(mixinCommunicationChannels(fredDemoOwner)).$$();

            assertThat(channelsAfter).hasSize(1);
            final CommunicationChannel communicationChannel = channelsAfter.first();

            //assertThat(communicationChannel.getName()).isEqualTo("45 High St, Oxford OX1, UK");
            assertThat(communicationChannel.getName()).startsWith("45");

            assertThat(communicationChannel.getLocation()).isNotNull();

            assertThat(communicationChannel).isInstanceOf(PostalAddress.class);
            final PostalAddress postalAddress = (PostalAddress)communicationChannel;
            assertThat(postalAddress.getFormattedAddress()).isEqualTo("45 High St, Oxford OX1, UK");
            assertThat(postalAddress.getGeocodeApiResponseAsJson()).isNotNull();
            assertThat(postalAddress.getLatLng()).matches("51.752[\\d][\\d][\\d][\\d],-1.250[\\d][\\d][\\d][\\d]");
            assertThat(postalAddress.getPlaceId()).isEqualTo("Eho0NSBIaWdoIFN0LCBPeGZvcmQgT1gxLCBVSw");
            assertThat(postalAddress.getAddressComponents()).isNotNull();
        }
    }

    public static class RaisesEventIntegrationTest extends CommunicationChannelOwner_addPostalAddress_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            T_addPostalAddress.DomainEvent ev;

            @Subscribe
            public void on(T_addPostalAddress.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            wrap(mixinNewPostalAddress(fredDemoOwner)).$$(
                    "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA", "UK", "Home", "Fred Smith's home",
                    false);

            assertThat(testSubscriber.ev).isNotNull();
        }

    }

}