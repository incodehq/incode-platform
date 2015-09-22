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
import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.commchannel.CommChannelModule;

/**
 * Domain service that contributes actions to create a new
 * {@link #newPostal(CommunicationChannelOwner, CommunicationChannelType, String, String, String, String, String, String, String)
 * postal address},
 * {@link #newEmail(CommunicationChannelOwner, CommunicationChannelType, String)
 * email} or
 * {@link #newPhoneOrFax(CommunicationChannelOwner, CommunicationChannelType, String)
 * phone/fax}, and contributes a collection to list the
 * {@link #communicationChannels(CommunicationChannelOwner) communication
 * channels} of a particular {@link CommunicationChannelOwner}.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CommunicationChannelContributions  {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends CommChannelModule.PropertyDomainEvent<CommunicationChannelContributions, T> {
        public PropertyDomainEvent(final CommunicationChannelContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<CommunicationChannelContributions, T> {
        public CollectionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<CommunicationChannelContributions> {
        public ActionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > newPostal (contributed action)

    public static class NewPostalEvent extends ActionDomainEvent {

        public NewPostalEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier) {
            super(source, identifier);
        }

        public NewPostalEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public NewPostalEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            domainEvent = NewPostalEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "CommunicationChannels", sequence = "1")
    public CommunicationChannelOwner newPostal(
            @ParameterLayout(named = "Owner")
            final CommunicationChannelOwner owner,
            @ParameterLayout(named = "Type")
            final CommunicationChannelType type,
            @ParameterLayout(named = "Address line 1")
            final String address1,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address line 2")
            final String address2,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address line 3")
            final String address3,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "City")
            final String city,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "State")
            final String state,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Postal Code")
            final String postalCode,
            @ParameterLayout(named = "Country")
            final String country
            ) {
        communicationChannelRepository.newPostal(owner, type, address1, address2, address3, city, state, postalCode, country);
        return owner;
    }

    public List<CommunicationChannelType> choices1NewPostal() {
        return CommunicationChannelType.matching(PostalAddress.class);
    }

    public CommunicationChannelType default1NewPostal() {
        return choices1NewPostal().get(0);
    }
    //endregion

    //region > newEmail (contributed action)

    public static class NewEmailEvent extends ActionDomainEvent {

        public NewEmailEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier) {
            super(source, identifier);
        }

        public NewEmailEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public NewEmailEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = NewEmailEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "CommunicationChannels", sequence = "2")
    public CommunicationChannelOwner newEmail(
            @ParameterLayout(named = "Owner")
            final CommunicationChannelOwner owner,
            @ParameterLayout(named = "Type")
            final CommunicationChannelType type,
            @ParameterLayout(named = "Address")
            final String address) {
        communicationChannelRepository.newEmail(owner, type, address);
        return owner;
    }

    public List<CommunicationChannelType> choices1NewEmail() {
        return CommunicationChannelType.matching(EmailAddress.class);
    }

    public CommunicationChannelType default1NewEmail() {
        return choices1NewEmail().get(0);
    }


    //endregion

    //region > newPhoneOrFax (contributed action)

    public static class NewPhoneOrFaxEvent extends ActionDomainEvent {

        public NewPhoneOrFaxEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier) {
            super(source, identifier);
        }

        public NewPhoneOrFaxEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public NewPhoneOrFaxEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final List<Object> arguments) {
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
    public CommunicationChannelOwner newPhoneOrFax(
            @ParameterLayout(named = "Owner")
            final CommunicationChannelOwner owner,
            @ParameterLayout(named = "Type")
            final CommunicationChannelType type,
            @ParameterLayout(named = "Number")
            final String number) {
        communicationChannelRepository.newPhoneOrFax(owner, type, number);
        return owner;
    }

    public List<CommunicationChannelType> choices1NewPhoneOrFax() {
        return CommunicationChannelType.matching(PhoneOrFaxNumber.class);
    }

    public CommunicationChannelType default1NewPhoneOrFax() {
        return choices1NewPhoneOrFax().get(0);
    }

    //endregion

    //region > communicationChannels (contributed collection)

    public static class CommunicationChannelsEvent extends CollectionDomainEvent<CommunicationChannel> {
        public CommunicationChannelsEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CommunicationChannelsEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final Of of, final CommunicationChannel value) {
            super(source, identifier, of, value);
        }
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    @Collection(
            domainEvent = CommunicationChannelsEvent.class
    )
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public SortedSet<CommunicationChannel> communicationChannels(final CommunicationChannelOwner owner) {
        return communicationChannelRepository.findByOwner(owner);
    }

    //endregion

    //region > injected services

    @Inject
    CommunicationChannelRepository communicationChannelRepository;

    //endregion

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class CommsChannelsSubscriber extends AbstractSubscriber {

        @Subscribe
        public void on(CommunicationChannelsEvent event) {
            ;
        }
    }

}
