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
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.api.geocoding.GeocodingService;

@Mixin
public class PostalAddress_lookupGeocode {

    //region > injected services
    @Inject
    GeocodingService geocodingService;
    @Inject
    DomainObjectContainer container;
    //endregion

    //region > mixins
    PostalAddress_update mixinUpdatePostalAddress(final PostalAddress postalAddress) {
        return container.mixin(PostalAddress_update.class, postalAddress);
    }
    //endregion

    //region > constructor
    private final PostalAddress postalAddress;
    public PostalAddress_lookupGeocode(final PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Programmatic
    public PostalAddress getPostalAddress() {
        return postalAddress;
    }
    //endregion

    public static class Event extends PostalAddress.ActionDomainEvent<PostalAddress_lookupGeocode> { }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = Event.class
    ) public PostalAddress __(
            @ParameterLayout(named = "Address")
            final String address) {

        mixinUpdatePostalAddress(this.postalAddress).lookupAndUpdateGeocode(true, address);

        return this.postalAddress;
    }

    public String default0__(
    ) {
        return geocodingService.combine(
                GeocodingService.Encoding.NOT_ENCODED,
                this.postalAddress.getAddressLine1(), this.postalAddress.getAddressLine2(),
                this.postalAddress.getAddressLine3(), this.postalAddress.getAddressLine4(),
                this.postalAddress.getPostalCode(), this.postalAddress.getCountry());
    }


}
