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

import org.isisaddons.module.commchannel.CommChannelModule;

/**
 * Domain service that contributes actions to create a new
 * {@link #newEmail(CommunicationChannelOwner, String, String)
 * email} for a particular {@link CommunicationChannelOwner}.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class EmailAddressContributions {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends CommChannelModule.PropertyDomainEvent<EmailAddressContributions, T> {
        public PropertyDomainEvent(final EmailAddressContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final EmailAddressContributions source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<EmailAddressContributions, T> {
        public CollectionDomainEvent(final EmailAddressContributions source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final EmailAddressContributions source, final Identifier identifier, final Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<EmailAddressContributions> {
        public ActionDomainEvent(final EmailAddressContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final EmailAddressContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final EmailAddressContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > newEmail (contributed action)

    public static class NewEmailEvent extends ActionDomainEvent {

        public NewEmailEvent(
                final EmailAddressContributions source,
                final Identifier identifier) {
            super(source, identifier);
        }

        public NewEmailEvent(
                final EmailAddressContributions source,
                final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public NewEmailEvent(
                final EmailAddressContributions source,
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
            @ParameterLayout(named = "Email")
            final String email,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Description")
            final String description) {
        communicationChannelRepository.newEmail(owner, email, description);
        return owner;
    }



    //endregion

    //region > injected services

    @Inject
    EmailAddressRepository communicationChannelRepository;

    //endregion

}
