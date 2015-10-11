/*
 *
Copyright 2015 incode.org
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
package org.incode.module.commchannel.dom.impl.emailaddress;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.CommChannelModule;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class EmailAddress_update {

    public static class UpdateEmailAddressEvent extends EmailAddress.ActionDomainEvent<EmailAddress_update> { }
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = UpdateEmailAddressEvent.class
    )
    public EmailAddress_update updateEmailAddress(
            final EmailAddress emailAddress,
            @Parameter(
                    regexPattern = CommChannelModule.Regex.EMAIL_ADDRESS,
                    maxLength = CommChannelModule.JdoColumnLength.EMAIL_ADDRESS
            )
            @ParameterLayout(named = "Email Address")
            final String address) {
        emailAddress.setEmailAddress(address);
        return this;
    }

    public String default1UpdateEmailAddress(final EmailAddress emailAddress) {
        return emailAddress.getEmailAddress();
    }


}