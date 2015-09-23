/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
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
package org.isisaddons.module.commchannel.dom;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PhoneOrFaxNumber.class
)
public class PhoneOrFaxNumberRepository {

    //region > newPhoneOrFax (programmatic)

    @Programmatic
    public PhoneOrFaxNumber newPhoneOrFax(
            final CommunicationChannelOwner owner,
            final CommunicationChannelType type,
            final String number,
            final String description) {
        final PhoneOrFaxNumber pn = container.newTransientInstance(PhoneOrFaxNumber.class);
        pn.setType(type);
        pn.setPhoneNumber(number);
        pn.setOwner(owner);

        pn.setDescription(description);

        container.persistIfNotAlready(pn);
        return pn;
    }
    //endregion

    //region > findByPhoneOrFaxNumber (programmatic)
    @Programmatic
    public PhoneOrFaxNumber findByPhoneOrFaxNumber(
            final CommunicationChannelOwner owner,
            final String phoneNumber) {

        final Optional<PhoneOrFaxNumber> phoneNumberIfFound = findByPhoneOrFaxNumber(owner, phoneNumber, CommunicationChannelType.PHONE_NUMBER);
        if(phoneNumberIfFound.isPresent()) {
            return phoneNumberIfFound.get();
        }

        final Optional<PhoneOrFaxNumber> faxNumberIfFound = findByPhoneOrFaxNumber(owner, phoneNumber, CommunicationChannelType.FAX_NUMBER);
        return faxNumberIfFound.orNull();
    }

    private Optional<PhoneOrFaxNumber> findByPhoneOrFaxNumber(final CommunicationChannelOwner owner, final String phoneNumber, final CommunicationChannelType communicationChannelType) {
        final List<CommunicationChannelOwnerLink> links =
                communicationChannelOwnerLinks.findByOwnerAndCommunicationChannelType(owner, communicationChannelType);
        final Iterable<PhoneOrFaxNumber> phoneOrFaxNumbers =
                Iterables.transform(
                        links,
                        CommunicationChannelOwnerLink.Functions.communicationChannel(PhoneOrFaxNumber.class));
        return Iterables.tryFind(phoneOrFaxNumbers, PhoneOrFaxNumber.Predicates.equalTo(phoneNumber, communicationChannelType));
    }
    //endregion

    //region > injected
    @Inject
    CommunicationChannelOwnerLinks communicationChannelOwnerLinks;
    @Inject
    DomainObjectContainer container;
    //endregion

}
