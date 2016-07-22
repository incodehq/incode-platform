/*
 *  Copyright 2014~2015 Dan Haywood
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
package org.incode.module.commchannel.integtests.postaladdress;

import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.postaladdress.T_addPostalAddress;
import org.incode.module.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

public class CommunicationChannelOwner_addPostalAddress_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject fredDemoOwner;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
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

            assertThat(communicationChannel.getName()).isEqualTo("45 High St, Oxford OX1, UK");

            assertThat(communicationChannel.getLocation()).isNotNull();

            assertThat(communicationChannel).isInstanceOf(PostalAddress.class);
            final PostalAddress postalAddress = (PostalAddress)communicationChannel;
            assertThat(postalAddress.getFormattedAddress()).isEqualTo("45 High St, Oxford OX1, UK");
            assertThat(postalAddress.getGeocodeApiResponseAsJson()).isNotNull();
            assertThat(postalAddress.getLatLng()).matches("51.75256[\\d][\\d],-1.25011[\\d][\\d]");
            assertThat(postalAddress.getPlaceId()).isEqualTo("Eho0NSBIaWdoIFN0LCBPeGZvcmQgT1gxLCBVSw");
            assertThat(postalAddress.getAddressComponents()).isEqualTo(
                    "street_number: 45\n"+
                    "route: High Street\n"+
                    "locality: Oxford\n"+
                    "administrative_area_level_2: Oxfordshire\n"+
                    "administrative_area_level_1: England\n"+
                    "country: United Kingdom\n"+
                    "postal_code: OX1\n");
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