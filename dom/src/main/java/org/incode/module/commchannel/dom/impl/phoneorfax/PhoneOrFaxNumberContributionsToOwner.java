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
package org.incode.module.commchannel.dom.impl.phoneorfax;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.Identifier;
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
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

/**
 * Domain service that contributes actions to create a new
 * {@link #newPhoneOrFaxNumber(CommunicationChannelOwner, CommunicationChannelType, String, String, String) phone or fax number}
 * for a particular {@link CommunicationChannelOwner}.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class PhoneOrFaxNumberContributionsToOwner {

    @Inject
    PhoneOrFaxNumberRepository phoneOrFaxNumberRepository;


    public static class NewPhoneOrFaxEvent extends CommunicationChannelOwner.ActionDomainEvent<PhoneOrFaxNumberContributionsToOwner>  {
        public NewPhoneOrFaxEvent( final PhoneOrFaxNumberContributionsToOwner source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }


    @Action(
            domainEvent = NewPhoneOrFaxEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            named = "New Phone/Fax",
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "CommunicationChannels", sequence = "3")
    public CommunicationChannelOwner newPhoneOrFaxNumber(
            @ParameterLayout(named = "Owner")
            final CommunicationChannelOwner owner,
            @ParameterLayout(named = "Type")
            final CommunicationChannelType type,
            @ParameterLayout(named = "Phone Number")
            @Parameter(
                    maxLength = CommChannelModule.JdoColumnLength.PHONE_NUMBER,
                    regexPattern = CommChannelModule.Regex.PHONE_NUMBER
            )
            final String phoneNumber,
            @Parameter(maxLength = JdoColumnLength.DESCRIPTION, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Description")
            final String description,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Notes", multiLine = 10)
            final String notes) {
        phoneOrFaxNumberRepository.newPhoneOrFax(owner, type, phoneNumber, description, notes);
        return owner;
    }

    public List<CommunicationChannelType> choices1NewPhoneOrFaxNumber() {
        return CommunicationChannelType.matching(PhoneOrFaxNumber.class);
    }

    public CommunicationChannelType default1NewPhoneOrFaxNumber() {
        return choices1NewPhoneOrFaxNumber().get(0);
    }



}
