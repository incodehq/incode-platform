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
package org.incode.module.commchannel.dom;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

/**
 * Domain service acting as repository for finding existing {@link PostalAddress postal address}es.
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PostalAddress.class
)
public class PostalAddressRepository {

    //region > newPostal (programmatic)
    @Programmatic
    public PostalAddress newPostal(
            final CommunicationChannelOwner owner,
            final String addressLine1,
            final String addressLine2,
            final String addressLine3,
            final String addressLine4,
            final String postalCode,
            final String country,
            final String description,
            final String notes) {

        final PostalAddress pa =
                container.newTransientInstance(PostalAddress.class);
        pa.setType(CommunicationChannelType.POSTAL_ADDRESS);
        pa.setOwner(owner);
        pa.setDescription(description);
        pa.setAddressLine1(addressLine1);
        pa.setAddressLine2(addressLine2);
        pa.setAddressLine3(addressLine3);
        pa.setAddressLine4(addressLine4);
        pa.setPostalCode(postalCode);
        pa.setNotes(notes);
        pa.setCountry(country);

        container.persistIfNotAlready(pa);
        return pa;
    }

    //endregion

    //region > findByAddress (programmatic)
    @Programmatic
    public PostalAddress findByAddress(
            final CommunicationChannelOwner owner, 
            final String placeId) {

        final List<CommunicationChannelOwnerLink> links =
                communicationChannelOwnerLinks.findByOwnerAndCommunicationChannelType(owner, CommunicationChannelType.POSTAL_ADDRESS);
        final Iterable<PostalAddress> postalAddresses =
                Iterables.transform(
                        links,
                        CommunicationChannelOwnerLink.Functions.communicationChannel(PostalAddress.class));
        final Optional<PostalAddress> postalAddressIfFound =
                Iterables.tryFind(postalAddresses, PostalAddress.Predicates.equalTo(placeId));
        return postalAddressIfFound.orNull();
    }
    //endregion

    //region > injected
    @Inject
    CommunicationChannelOwnerLinks communicationChannelOwnerLinks;
    @Inject
    DomainObjectContainer container;
    //endregion

}
