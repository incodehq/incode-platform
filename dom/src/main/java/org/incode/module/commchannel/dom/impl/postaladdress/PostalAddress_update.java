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
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.api.geocoding.GeocodedAddress;
import org.incode.module.commchannel.dom.api.geocoding.GeocodingService;

@Mixin
public class PostalAddress_update extends PostalAddressMixinAbstract {

    //region > injected services
    @Inject
    GeocodingService geocodingService;
    @Inject
    DomainObjectContainer container;
    //endregion

    //region > mixins
    private PostalAddress_resetGeocode mixinResetGeocode() {
        return container.mixin(PostalAddress_resetGeocode.class, this.postalAddress);
    }
    //endregion

    //region > constructor
    public PostalAddress_update(final PostalAddress postalAddress) {
        super(postalAddress);
    }
    //endregion


    public static class Event extends PostalAddress.ActionDomainEvent<PostalAddress_update> { }
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = Event.class
    )
    public PostalAddress __(
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

        this.postalAddress.setAddressLine1(addressLine1);
        this.postalAddress.setAddressLine2(addressLine2);
        this.postalAddress.setAddressLine3(addressLine3);
        this.postalAddress.setAddressLine4(addressLine4);
        this.postalAddress.setPostalCode(postalCode);
        this.postalAddress.setCountry(country);

        lookupAndUpdateGeocode(
                lookupGeocode,
                addressLine1, addressLine2, addressLine3, addressLine4, postalCode, country);

        return this.postalAddress;
    }

    void lookupAndUpdateGeocode(
            final Boolean lookupGeocode,
            final String... addressParts) {

        if (lookupGeocode == null) {
            return;
        }

        if (lookupGeocode) {
            final String address = geocodingService.combine(GeocodingService.Encoding.ENCODED, addressParts);
            final GeocodedAddress geocodedAddress = geocodingService.lookup(address);

            if (GeocodedAddress.isOk(geocodedAddress)) {
                this.postalAddress.setFormattedAddress(geocodedAddress.getFormattedAddress());
                this.postalAddress.setGeocodeApiResponseAsJson(geocodedAddress.getApiResponseAsJson());
                this.postalAddress.setPlaceId(geocodedAddress.getPlaceId());
                this.postalAddress.setLatLng(geocodedAddress.getLatLng());
                this.postalAddress.setAddressComponents(geocodedAddress.getAddressComponents());
            } else {
                container.warnUser(
                        TranslatableString.tr("Could not lookup geocode for address"),
                        CommunicationChannelOwner_newPostalAddress.class, "newPostal");
            }
        } else {
            mixinResetGeocode().__();
        }
    }

    public String default0__() {
        return this.postalAddress.getAddressLine1();
    }
    public String default1__() {
        return this.postalAddress.getAddressLine2();
    }
    public String default2__() {
        return this.postalAddress.getAddressLine3();
    }
    public String default3__() {
        return this.postalAddress.getAddressLine4();
    }
    public String default4__() {
        return this.postalAddress.getPostalCode();
    }
    public String default5__() {
        return this.postalAddress.getCountry();
    }
    public Boolean default6__() {
        return this.postalAddress.getPlaceId() != null ? true: null;
    }


}
