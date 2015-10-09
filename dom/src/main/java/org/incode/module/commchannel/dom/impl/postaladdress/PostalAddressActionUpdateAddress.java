/*
 *
 *  Copyright 2015 incode.org
 *
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
package org.incode.module.commchannel.dom.impl.postaladdress;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.api.geocoding.GeocodedAddress;
import org.incode.module.commchannel.dom.api.geocoding.GeocodingService;


@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class PostalAddressActionUpdateAddress {

    @Inject
    PostalAddressActionResetGeocode postalAddressActionResetGeocode;
    @Inject
    GeocodingService geocodingService;
    @Inject
    DomainObjectContainer container;


    public static class UpdateAddressEvent extends PostalAddress.ActionDomainEvent<PostalAddressActionUpdateAddress> { }
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = UpdateAddressEvent.class
    )
    public PostalAddress updateAddress(
            final PostalAddress postalAddress,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
            @ParameterLayout(named = "Address Line 1")
            final String addressLine1,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address Line 2")
            final String addressLine2,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address Line 3")
            final String addressLine3,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address Line 4")
            final String addressLine4,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.POSTAL_CODE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Postal Code")
            final String postalCode,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.COUNTRY, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Country")
            final String country,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Lookup geocode")
            final Boolean lookupGeocode) {

        postalAddress.setAddressLine1(addressLine1);
        postalAddress.setAddressLine2(addressLine2);
        postalAddress.setAddressLine3(addressLine3);
        postalAddress.setAddressLine4(addressLine4);
        postalAddress.setPostalCode(postalCode);
        postalAddress.setCountry(country);

        lookupAndUpdateGeocode(
                postalAddress,
                lookupGeocode,
                addressLine1, addressLine2, addressLine3, addressLine4, postalCode, country);

        return postalAddress;
    }

    void lookupAndUpdateGeocode(
            final PostalAddress postalAddress,
            final Boolean lookupGeocode,
            final String... addressParts) {

        if (lookupGeocode == null) {
            return;
        }

        if (lookupGeocode) {
            final String address = geocodingService.combine(GeocodingService.Encoding.ENCODED, addressParts);
            final GeocodedAddress geocodedAddress = geocodingService.lookup(address);

            if (GeocodedAddress.isOk(geocodedAddress)) {
                postalAddress.setFormattedAddress(geocodedAddress.getFormattedAddress());
                postalAddress.setGeocodeApiResponseAsJson(geocodedAddress.getApiResponseAsJson());
                postalAddress.setPlaceId(geocodedAddress.getPlaceId());
                postalAddress.setLatLng(geocodedAddress.getLatLng());
                postalAddress.setAddressComponents(geocodedAddress.getAddressComponents());
            } else {
                container.warnUser(
                        TranslatableString.tr("Could not lookup geocode for address"),
                        PostalAddressContributionsToOwner.class, "newPostal");
            }
        } else {
            postalAddressActionResetGeocode.resetGeocode(postalAddress);
        }
    }

    public String default1UpdateAddress(final PostalAddress postalAddress) {
        return postalAddress.getAddressLine1();
    }
    public String default2UpdateAddress(final PostalAddress postalAddress) {
        return postalAddress.getAddressLine2();
    }
    public String default3UpdateAddress(final PostalAddress postalAddress) {
        return postalAddress.getAddressLine3();
    }
    public String default4UpdateAddress(final PostalAddress postalAddress) {
        return postalAddress.getAddressLine4();
    }
    public String default5UpdateAddress(final PostalAddress postalAddress) {
        return postalAddress.getPostalCode();
    }
    public String default6UpdateAddress(final PostalAddress postalAddress) {
        return postalAddress.getCountry();
    }
    public Boolean default7UpdateAddress(final PostalAddress postalAddress) {
        return postalAddress.getPlaceId() != null ? true: null;
    }

}
