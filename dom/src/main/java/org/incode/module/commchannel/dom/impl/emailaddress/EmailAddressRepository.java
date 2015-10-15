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

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_owner;
import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = EmailAddress.class
)
public class EmailAddressRepository {

    //region > injected services
    @Inject
    CommunicationChannelOwnerLinkRepository communicationChannelOwnerLinkRepository;
    @Inject
    DomainObjectContainer container;
    //endregion

    @Programmatic
    public EmailAddress newEmail(
            final CommunicationChannelOwner owner,
            final String address,
            final String description,
            final String notes) {

        final EmailAddress ea = container.newTransientInstance(EmailAddress.class);
        ea.setType(CommunicationChannelType.EMAIL_ADDRESS);
        ea.setEmailAddress(address);
        owner(ea).setOwner(owner);

        ea.setDescription(description);
        ea.setNotes(notes);

        container.persistIfNotAlready(ea);

        return ea;
    }

    @Programmatic
    public EmailAddress findByEmailAddress(
            final CommunicationChannelOwner owner, 
            final String emailAddress) {

        final List<CommunicationChannelOwnerLink> links =
                communicationChannelOwnerLinkRepository.findByOwnerAndCommunicationChannelType(
                                                            owner, CommunicationChannelType.EMAIL_ADDRESS);
        final Iterable<EmailAddress> emailAddresses =
                Iterables.transform(
                        links,
                        CommunicationChannelOwnerLink.Functions.communicationChannel(EmailAddress.class));
        final Optional<EmailAddress> emailAddressIfFound =
                Iterables.tryFind(emailAddresses, input -> Objects.equals(emailAddress, input.getEmailAddress()));
        return emailAddressIfFound.orNull();
    }

    private CommunicationChannel_owner owner(final CommunicationChannel<?> cc) {
        return container.mixin(CommunicationChannel_owner.class, cc);
    }

}
