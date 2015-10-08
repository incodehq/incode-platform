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

import org.apache.isis.applib.DomainObjectContainer;
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

import org.incode.module.commchannel.CommChannelModule;
import org.incode.module.commchannel.dom.geocoding.GeocodingService;

/**
 * Domain service that contributes actions to create a new
 * {@link #newPostalAddress(CommunicationChannelOwner, String, String, String, String, String, String, String, String, Boolean) postal address} to a {@link CommunicationChannelOwner}.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class PostalAddressContributions {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends
            CommChannelModule.PropertyDomainEvent<PostalAddressContributions, T> {
        public PropertyDomainEvent(final PostalAddressContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final PostalAddressContributions source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<PostalAddressContributions, T> {
        public CollectionDomainEvent(final PostalAddressContributions source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final PostalAddressContributions source, final Identifier identifier, final Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<PostalAddressContributions> {
        public ActionDomainEvent(final PostalAddressContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final PostalAddressContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final PostalAddressContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > newPostal (contributed action)

    public static class NewPostalAddressEvent extends ActionDomainEvent {

        public NewPostalAddressEvent(
                final PostalAddressContributions source,
                final Identifier identifier) {
            super(source, identifier);
        }

        public NewPostalAddressEvent(
                final PostalAddressContributions source,
                final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public NewPostalAddressEvent(
                final PostalAddressContributions source,
                final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

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

        postalAddress.lookupAndUpdateGeocode(
                lookupGeocode,
                addressLine1, addressLine2, addressLine3, addressLine4, postalCode, country);

        return owner;
    }
    //endregion

    //region > injected services
    @Inject
    DomainObjectContainer container;
    @Inject
    PostalAddressRepository postalAddressRepository;
    @Inject
    GeocodingService geocodingService;
    //endregion

}
