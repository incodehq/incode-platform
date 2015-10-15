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

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

@Mixin
public class CommunicationChannelOwner_newPhoneOrFaxNumber {

    //region > injected services
    @Inject
    PhoneOrFaxNumberRepository phoneOrFaxNumberRepository;
    //endregion

    //region > constructor
    private final CommunicationChannelOwner communicationChannelOwner;
    public CommunicationChannelOwner_newPhoneOrFaxNumber(final CommunicationChannelOwner communicationChannelOwner) {
        this.communicationChannelOwner = communicationChannelOwner;
    }
    //endregion

    public static class Event extends CommunicationChannelOwner.ActionDomainEvent
                                            <CommunicationChannelOwner_newPhoneOrFaxNumber>  { }
    @Action(
            domainEvent = Event.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            named = "New Phone/Fax",
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "CommunicationChannels", sequence = "3")
    public CommunicationChannelOwner __(
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
        phoneOrFaxNumberRepository.newPhoneOrFax(this.communicationChannelOwner, type, phoneNumber, description, notes);
        return this.communicationChannelOwner;
    }

    public String validate0__(final CommunicationChannelType type) {
        final List<CommunicationChannelType> validChoices = choices0__();
        return validChoices.contains(type)? null: "Communication type must be " + validChoices;
    }

    public List<CommunicationChannelType> choices0__() {
        return CommunicationChannelType.matching(PhoneOrFaxNumber.class);
    }

    public CommunicationChannelType default0__() {
        return choices0__().get(0);
    }



}
