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

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;

/**
 * Domain service that contributes actions to create a new
 * {@link #newPostalAddress(CommunicationChannelOwner, String, String, String, String, String, String, String, String, Boolean) postal address} to a {@link CommunicationChannelOwner}.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CommunicationChannelOwner_newPostalAddress {

    @Inject
    PostalAddressRepository postalAddressRepository;
    @Inject
    PostalAddress_updateAddress postalAddressUpdateAddress;


    public static class NewPostalAddressEvent
            extends CommunicationChannelOwner.ActionDomainEvent<CommunicationChannelOwner_newPostalAddress> { }

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            domainEvent = NewPostalAddressEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "CommunicationChannels", sequence = "1")
    public CommunicationChannelOwner newPostalAddress(
            @ParameterLayout(named = "Owner")
            final CommunicationChannelOwner owner,
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
            @Parameter(maxLength = JdoColumnLength.DESCRIPTION, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Description")
            final String description,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Notes", multiLine = 10)
            final String notes,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Lookup geocode")
            final Boolean lookupGeocode) {

        final PostalAddress postalAddress =
                postalAddressRepository.newPostal(
                        owner,
                        addressLine1, addressLine2, addressLine3, addressLine4,
                        postalCode, country,
                        description, notes
                );

        postalAddressUpdateAddress.lookupAndUpdateGeocode(
                postalAddress,
                lookupGeocode,
                addressLine1, addressLine2, addressLine3, addressLine4, postalCode, country);

        return owner;
    }

}
