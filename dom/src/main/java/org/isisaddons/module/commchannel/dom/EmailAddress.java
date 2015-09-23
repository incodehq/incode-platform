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
import java.util.Objects;

import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Predicate;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;

import org.isisaddons.module.commchannel.CommChannelModule;

@javax.jdo.annotations.PersistenceCapable()
// identityType=IdentityType.DATASTORE inherited from superclass
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.SUPERCLASS_TABLE
)
// no @DatastoreIdentity nor @Version, since inherited from supertype
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "EmailAddress_emailAddress_IDX",
                members = { "emailAddress" })
})
@DomainObject(
        editing = Editing.DISABLED
)
public class EmailAddress extends CommunicationChannel<EmailAddress> {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends CommChannelModule.PropertyDomainEvent<EmailAddress, T> {
        public PropertyDomainEvent(final EmailAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final EmailAddress source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<EmailAddress, T> {
        public CollectionDomainEvent(final EmailAddress source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final EmailAddress source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<EmailAddress> {
        public ActionDomainEvent(final EmailAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final EmailAddress source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final EmailAddress source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > emailAddress (property)

    public static class EmailAddressEvent extends PropertyDomainEvent<String> {

        public EmailAddressEvent(final EmailAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public EmailAddressEvent(
                final EmailAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String emailAddress;

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.EMAIL_ADDRESS)
    @Title
    @Property(
            domainEvent = EmailAddressEvent.class,
            optionality = Optionality.MANDATORY
    )
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String address) {
        this.emailAddress = address;
    }

    //endregion

    //region > changeEmailAddress (action)

    public static class ChangeEmailAddressEvent extends ActionDomainEvent {
        public ChangeEmailAddressEvent(final EmailAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public ChangeEmailAddressEvent(
                final EmailAddress source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ChangeEmailAddressEvent(
                final EmailAddress source,
                final Identifier identifier,
                final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = ChangeEmailAddressEvent.class
    )
    public EmailAddress changeEmailAddress(
            @ParameterLayout(named = "Email Address")
            @Parameter(regexPattern = CommChannelModule.Regex.EMAIL_ADDRESS)
            final String address) {
        setEmailAddress(address);

        return this;
    }

    public String default0ChangeEmailAddress() {
        return getEmailAddress();
    }

    //endregion

    //region > Predicates

    public static class Predicates {
        private Predicates(){}

        public static Predicate<EmailAddress> equalTo(
                final String emailAddress) {
            return new Predicate<EmailAddress>() {
                @Override
                public boolean apply(final EmailAddress input) {
                    return Objects.equals(emailAddress, input.getEmailAddress());
                }
            };
        }
    }

    //endregion

}