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
import org.incode.module.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.module.commchannel.dom.impl.postaladdress.PostalAddress_update;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class PostalAddress_update_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject fredDemoOwner;
    PostalAddress postalAddress;

    PostalAddress_update updatePostalAddress(final PostalAddress postalAddress) {
        return mixin(PostalAddress_update.class, postalAddress);
    }

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");

        wrap(newPostalAddress(fredDemoOwner)).__(
                "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA", "UK", "Work", "Fred Smith's work",
                false);

        final SortedSet<CommunicationChannel> communicationChannels = wrap(communicationChannels(fredDemoOwner)).__();
        postalAddress = (PostalAddress) communicationChannels.first();

    }

    public static class ActionImplementationIntegrationTest extends PostalAddress_update_IntegTest {

        @Test
        public void when_lookup_geocode_or_does_not_loookup() throws Exception {

            // when
            wrap(updatePostalAddress(postalAddress)).__(
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
            wrap(updatePostalAddress(postalAddress)).__(
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

    public static class DefaultsIntegrationTest extends PostalAddress_update_IntegTest {

        @Test
        public void defaults_to_corresponding_property() throws Exception {
            assertThat(updatePostalAddress(postalAddress).default0__())
                    .isEqualTo(postalAddress.getAddressLine1());
            assertThat(updatePostalAddress(postalAddress).default1__())
                    .isEqualTo(postalAddress.getAddressLine2());
            assertThat(updatePostalAddress(postalAddress).default2__())
                    .isEqualTo(postalAddress.getAddressLine3());
            assertThat(updatePostalAddress(postalAddress).default3__())
                    .isEqualTo(postalAddress.getAddressLine4());
            assertThat(updatePostalAddress(postalAddress).default4__())
                    .isEqualTo(postalAddress.getPostalCode());
            assertThat(updatePostalAddress(postalAddress).default5__())
                    .isEqualTo(postalAddress.getCountry());
        }

        @Test
        public void default_for_place_id_parameter_when_not_looked_up() throws Exception {
            // when
            final Boolean defaultPlaceId = updatePostalAddress(postalAddress).default6__();
            // then
            assertThat(defaultPlaceId).isNull();
        }

        @Test
        public void default_for_place_id_parameter_when_has_been_looked_up() throws Exception {

            // given
            wrap(updatePostalAddress(postalAddress)).__(
                    "45", "High Street", "Oxford", null, "OX1",
                    "UK", true);

            // when
            final Boolean defaultPlaceId = updatePostalAddress(postalAddress).default6__();

            // then
            assertThat(defaultPlaceId).isTrue();
        }
    }
}