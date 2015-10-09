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

import java.util.Objects;

import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Predicate;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;

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
    public static abstract class PropertyDomainEvent<S,T> extends CommChannelModule.PropertyDomainEvent<S, T> {
        public PropertyDomainEvent(final S source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<S,T> extends CommChannelModule.CollectionDomainEvent<S, T> {
        public CollectionDomainEvent(final S source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent<S> extends CommChannelModule.ActionDomainEvent<S> {
        public ActionDomainEvent(final S source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > emailAddress (property)

    public static class EmailAddressEvent extends PropertyDomainEvent<EmailAddress, String> {
        public EmailAddressEvent( final EmailAddress source, final Identifier identifier, final String oldValue, final String newValue) {
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