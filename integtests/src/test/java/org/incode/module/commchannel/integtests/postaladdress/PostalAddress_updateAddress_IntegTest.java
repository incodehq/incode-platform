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

import org.junit.Before;
import org.junit.Test;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelOwner_communicationChannels;
import org.incode.module.commchannel.dom.impl.postaladdress.CommunicationChannelOwner_newPostalAddress;
import org.incode.module.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.module.commchannel.dom.impl.postaladdress.PostalAddress_updateAddress;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class PostalAddress_updateAddress_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommunicationChannelOwner_communicationChannels communicationChannelOwner_communicationChannels;
    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;
    @Inject
    CommunicationChannelOwner_newPostalAddress communicationChannelOwner_newPostalAddress;

    CommChannelDemoObject fredDemoOwner;
    PostalAddress postalAddress;

    @Inject
    PostalAddress_updateAddress postalAddress_updateAddress;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");

        wrap(communicationChannelOwner_newPostalAddress)
                .newPostalAddress(fredDemoOwner,
                        "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA",
                        "UK",
                        "Work", "Fred Smith's work",
                        false);

        final SortedSet<CommunicationChannel> communicationChannels = wrap(
                communicationChannelOwner_communicationChannels).communicationChannels(fredDemoOwner);
        postalAddress = (PostalAddress) communicationChannels.first();

    }

    public static class ActionImplementationIntegrationTest extends PostalAddress_updateAddress_IntegTest {

        @Test
        public void when_lookup_geocode_or_does_not_loookup() throws Exception {

            // when
            wrap(postalAddress_updateAddress).updateAddress(postalAddress,
                    "45", "High Street", "Oxford", null, "OX1",
                    "UK", true);

            // then
            assertThat(postalAddress.getName()).isEqualTo("45 High St, Oxford, Oxfordshire OX1, UK");
            assertThat(postalAddress.getFormattedAddress()).isEqualTo("45 High St, Oxford, Oxfordshire OX1, UK");
            assertThat(postalAddress.getGeocodeApiResponseAsJson()).isNotNull();
            assertThat(postalAddress.getLatLng()).isEqualTo("51.7525657,-1.2501133");
            assertThat(postalAddress.getPlaceId()).isEqualTo("Eic0NSBIaWdoIFN0LCBPeGZvcmQsIE94Zm9yZHNoaXJlIE9YMSwgVUs");
            assertThat(postalAddress.getAddressComponents()).isEqualTo(
                    "street_number: 45\n" +
                            "route: High Street\n" +
                            "locality: Oxford\n" +
                            "administrative_area_level_2: Oxfordshire\n" +
                            "country: United Kingdom\n" +
                            "postal_code: OX1\n");

            // and when
            wrap(postalAddress_updateAddress).updateAddress(postalAddress,
                    "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA",
                    "UK", false);

            // then
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
    }

    public static class DefaultsIntegrationTest extends PostalAddress_updateAddress_IntegTest {

        @Test
        public void defaults_to_corresponding_property() throws Exception {
            assertThat(postalAddress_updateAddress.default1UpdateAddress(postalAddress))
                    .isEqualTo(postalAddress.getAddressLine1());
            assertThat(postalAddress_updateAddress.default2UpdateAddress(postalAddress))
                    .isEqualTo(postalAddress.getAddressLine2());
            assertThat(postalAddress_updateAddress.default3UpdateAddress(postalAddress))
                    .isEqualTo(postalAddress.getAddressLine3());
            assertThat(postalAddress_updateAddress.default4UpdateAddress(postalAddress))
                    .isEqualTo(postalAddress.getAddressLine4());
            assertThat(postalAddress_updateAddress.default5UpdateAddress(postalAddress))
                    .isEqualTo(postalAddress.getPostalCode());
            assertThat(postalAddress_updateAddress.default6UpdateAddress(postalAddress))
                    .isEqualTo(postalAddress.getCountry());
        }

        @Test
        public void default_for_place_id_parameter_when_not_looked_up() throws Exception {
            // when
            final Boolean defaultPlaceId = postalAddress_updateAddress.default7UpdateAddress(postalAddress);
            // then
            assertThat(defaultPlaceId).isNull();
        }

        @Test
        public void default_for_place_id_parameter_when_has_been_looked_up() throws Exception {

            // given
            wrap(postalAddress_updateAddress).updateAddress(postalAddress,
                    "45", "High Street", "Oxford", null, "OX1",
                    "UK", true);

            // when
            final Boolean defaultPlaceId = postalAddress_updateAddress.default7UpdateAddress(postalAddress);

            // then
            assertThat(defaultPlaceId).isTrue();
        }
    }
}