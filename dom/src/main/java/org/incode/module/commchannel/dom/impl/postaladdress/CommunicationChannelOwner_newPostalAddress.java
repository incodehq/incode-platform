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

import java.util.Collection;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.api.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.purpose.CommunicationChannelPurposeService;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

@Mixin
public class CommunicationChannelOwner_newPostalAddress {

    //region > injected services
    @Inject
    PostalAddressRepository postalAddressRepository;
    @Inject
    CommunicationChannelPurposeService communicationChannelPurposeService;
    @Inject
    DomainObjectContainer container;
    //endregion

    //region > mixins
    private PostalAddress_update mixinUpdate(final PostalAddress postalAddress) {
        return container.mixin(PostalAddress_update.class, postalAddress);
    }
    //endregion

    //region > constructor
    private final CommunicationChannelOwner communicationChannelOwner;
    public CommunicationChannelOwner_newPostalAddress(final CommunicationChannelOwner communicationChannelOwner) {
        this.communicationChannelOwner = communicationChannelOwner;
    }
    //endregion

    public static class DomainEvent extends CommunicationChannelOwner.ActionDomainEvent
                                            <CommunicationChannelOwner_newPostalAddress> { }

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            domainEvent = DomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "CommunicationChannels", sequence = "1")
    public CommunicationChannelOwner __(
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
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.PURPOSE, optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Purpose")
            final String purpose,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Notes", multiLine = 10)
            final String notes,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Lookup geocode")
            final Boolean lookupGeocode) {

        final PostalAddress postalAddress =
                postalAddressRepository.newPostal(
                        this.communicationChannelOwner,
                        addressLine1, addressLine2, addressLine3, addressLine4,
                        postalCode, country,
                        purpose, notes
                );

        mixinUpdate(postalAddress).lookupAndUpdateGeocode(
                lookupGeocode, addressLine1, addressLine2, addressLine3, addressLine4, postalCode, country);

        return this.communicationChannelOwner;
    }

    public Collection<String> choices6__() {
        return communicationChannelPurposeService.purposesFor(this.communicationChannelOwner, CommunicationChannelType.POSTAL_ADDRESS);
    }


}
