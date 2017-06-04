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

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_owner;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

/**
 * Domain service acting as repository for finding existing {@link PostalAddress postal address}es.
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PostalAddress.class
)
public class PostalAddressRepository {

    public String getId() {
        return "incodeCommChannel.PostalAddressRepository";
    }


    //region > newPostal (programmatic)
    @Programmatic
    public PostalAddress newPostal(
            final Object owner,
            final String addressLine1,
            final String addressLine2,
            final String addressLine3,
            final String addressLine4,
            final String postalCode,
            final String country,
            final String purpose,
            final String notes) {

        final PostalAddress pa = factoryService.instantiate(PostalAddress.class);
        pa.setType(CommunicationChannelType.POSTAL_ADDRESS);
        owner(pa).setOwner(owner);
        pa.setPurpose(purpose);
        pa.setAddressLine1(addressLine1);
        pa.setAddressLine2(addressLine2);
        pa.setAddressLine3(addressLine3);
        pa.setAddressLine4(addressLine4);
        pa.setPostalCode(postalCode);
        pa.setNotes(notes);
        pa.setCountry(country);

        repositoryService.persist(pa);
        return pa;
    }

    //endregion

    //region > findByAddress (programmatic)
    @Programmatic
    public PostalAddress findByAddress(
            final Object owner,
            final String placeId) {

        final List<CommunicationChannelOwnerLink> links =
                linkRepository.findByOwnerAndCommunicationChannelType(owner, CommunicationChannelType.POSTAL_ADDRESS);
        final Iterable<PostalAddress> postalAddresses =
                Iterables.transform(
                        links,
                        CommunicationChannelOwnerLink.Functions.communicationChannel(PostalAddress.class));
        final Optional<PostalAddress> postalAddressIfFound =
                Iterables.tryFind(postalAddresses, input -> Objects.equals(placeId, input.getPlaceId()));
        return postalAddressIfFound.orNull();
    }

    private CommunicationChannel_owner owner(final CommunicationChannel<?> cc) {
        return factoryService.mixin(CommunicationChannel_owner.class, cc);
    }
    //endregion

    //region > injected services

    @Inject
    CommunicationChannelOwnerLinkRepository linkRepository;
    @Inject
    RepositoryService repositoryService;
    @Inject
    FactoryService factoryService;

    //endregion

}
