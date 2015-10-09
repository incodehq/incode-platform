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

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class PhoneOrFaxNumberActionUpdate {

    public static class UpdatePhoneOrFaxNumberEvent extends PhoneOrFaxNumber.ActionDomainEvent<PhoneOrFaxNumberActionUpdate> {
        public UpdatePhoneOrFaxNumberEvent( final PhoneOrFaxNumberActionUpdate source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdatePhoneOrFaxNumberEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public PhoneOrFaxNumber updatePhoneOrFaxNumber(
            final PhoneOrFaxNumber phoneOrFaxNumber,
            @ParameterLayout(named = "Type")
            final CommunicationChannelType type,
            @ParameterLayout(named = "Phone Number")
            @Parameter(
                    maxLength = CommChannelModule.JdoColumnLength.PHONE_NUMBER,
                    regexPattern = CommChannelModule.Regex.PHONE_NUMBER
            )
            final String phoneNumber) {
        phoneOrFaxNumber.setType(type);
        phoneOrFaxNumber.setPhoneNumber(phoneNumber);

        return phoneOrFaxNumber;
    }

    public List<CommunicationChannelType> choices1UpdatePhoneOrFaxNumber( final PhoneOrFaxNumber phoneOrFaxNumber) {
        return CommunicationChannelType.matching(PhoneOrFaxNumber.class);
    }

    public CommunicationChannelType default1UpdatePhoneOrFaxNumber( final PhoneOrFaxNumber phoneOrFaxNumber) {
        return phoneOrFaxNumber.getType();
    }
    public String default2UpdatePhoneOrFaxNumber( final PhoneOrFaxNumber phoneOrFaxNumber) {
        return phoneOrFaxNumber.getPhoneNumber();
    }


}
