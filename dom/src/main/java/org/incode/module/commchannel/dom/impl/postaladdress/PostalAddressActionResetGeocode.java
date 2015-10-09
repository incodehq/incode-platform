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

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class PostalAddressActionResetGeocode {

    public static class ResetGeocodeEvent extends PostalAddress.ActionDomainEvent<PostalAddressActionResetGeocode> {
        public ResetGeocodeEvent( final PostalAddressActionResetGeocode source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }



    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = ResetGeocodeEvent.class
    )
    public PostalAddress resetGeocode( final PostalAddress postalAddress) {
        postalAddress.setFormattedAddress(null);
        postalAddress.setPlaceId(null);
        postalAddress.setLatLng(null);
        postalAddress.setAddressComponents(null);
        postalAddress.setGeocodeApiResponseAsJson(null);
        return postalAddress;
    }

}
