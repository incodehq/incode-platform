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

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

@Mixin
public class PostalAddress_clearGeocode extends PostalAddressMixinAbstract {

    //region > constructor
    public PostalAddress_clearGeocode(final PostalAddress postalAddress) {
        super(postalAddress);
    }
    //endregion

    public static class Event extends PostalAddress.ActionDomainEvent<PostalAddress_clearGeocode> { }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = Event.class
    )
    public PostalAddress __() {
        this.postalAddress.setFormattedAddress(null);
        this.postalAddress.setPlaceId(null);
        this.postalAddress.setLatLng(null);
        this.postalAddress.setAddressComponents(null);
        this.postalAddress.setGeocodeApiResponseAsJson(null);
        return this.postalAddress;
    }

}
