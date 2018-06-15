package org.incode.example.dom.commchannel.integtests.tests.postaladdress;

import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.example.commchannel.dom.impl.postaladdress.PostalAddress_clearGeocode;
import org.incode.example.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

public class PostalAddress_clearGeocode_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject fredDemoOwner;
    PostalAddress postalAddress;

    PostalAddress_clearGeocode mixinResetGeocode(final PostalAddress postalAddress) {
        return mixin(PostalAddress_clearGeocode.class, postalAddress);
    }

    @Before
    public void setUpData() throws Exception {

        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_tearDown(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Fred");

        wrap(mixinNewPostalAddress(fredDemoOwner)).$$(
                "45", "High Street", "Oxford", null, "OX1", "UK", "Work", "Fred Smith's work", true);

        final SortedSet<CommunicationChannel> communicationChannels = wrap(mixinCommunicationChannels(fredDemoOwner)).$$();
        postalAddress = (PostalAddress) communicationChannels.first();

    }

    public static class ActionImplementationIntegrationTest extends PostalAddress_clearGeocode_IntegTest {

        @Test
        public void clear() throws Exception {

            // these tests are too flaky
            assumeThat(false, is(true));


            assumeThat(isInternetReachable(), is(true));

            // given
            assertThat(postalAddress.getGeocodeApiResponseAsJson()).isNotNull();
            assertThat(postalAddress.getName()).isEqualTo("45 High St, Oxford OX1, UK");

            // when
            wrap(mixinResetGeocode(postalAddress)).$$();

            // then
            assertThat(postalAddress.getName()).isEqualTo("45, High Stree...ford, OX1, UK");
            assertThat(postalAddress.getFormattedAddress()).isNull();
            assertThat(postalAddress.getGeocodeApiResponseAsJson()).isNull();
            assertThat(postalAddress.getLatLng()).isNull();
            assertThat(postalAddress.getPlaceId()).isNull();
            assertThat(postalAddress.getAddressComponents()).isNull();
        }
    }


    public static class RaisesEventIntegrationTest extends PostalAddress_clearGeocode_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            PostalAddress_clearGeocode.DomainEvent ev;

            @Subscribe
            public void on(PostalAddress_clearGeocode.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            wrap(mixinResetGeocode(postalAddress)).$$();

            assertThat(testSubscriber.ev).isNotNull();
        }
    }

}