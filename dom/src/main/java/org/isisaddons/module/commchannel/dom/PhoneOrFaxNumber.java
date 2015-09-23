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
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;

import org.isisaddons.module.commchannel.CommChannelModule;

@javax.jdo.annotations.PersistenceCapable
// identityType=IdentityType.DATASTORE inherited from superclass
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
// no @DatastoreIdentity nor @Version, since inherited from supertype
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "PhoneNumber_phoneNumber_IDX",
                members = { "phoneNumber" })
})
@DomainObject(editing = Editing.DISABLED)
public class PhoneOrFaxNumber extends CommunicationChannel<PhoneOrFaxNumber> {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends CommChannelModule.PropertyDomainEvent<PhoneOrFaxNumber, T> {
        public PropertyDomainEvent(final PhoneOrFaxNumber source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final PhoneOrFaxNumber source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<PhoneOrFaxNumber, T> {
        public CollectionDomainEvent(final PhoneOrFaxNumber source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final PhoneOrFaxNumber source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<PhoneOrFaxNumber> {
        public ActionDomainEvent(final PhoneOrFaxNumber source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final PhoneOrFaxNumber source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final PhoneOrFaxNumber source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > phoneNumber (property)

    public static class PhoneNumberEvent extends PropertyDomainEvent<String> {

        public PhoneNumberEvent(final PhoneOrFaxNumber source, final Identifier identifier) {
            super(source, identifier);
        }

        public PhoneNumberEvent(
                final PhoneOrFaxNumber source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }
    private String phoneNumber;

    @javax.jdo.annotations.Column(
            allowsNull = "true",
            length = CommChannelModule.JdoColumnLength.PHONE_NUMBER
    )
    @Title()
    @Property(
            domainEvent = PhoneNumberEvent.class,
            optionality = Optionality.MANDATORY
    )
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String number) {
        this.phoneNumber = number;
    }

    //endregion

    //region > changePhoneOrFaxNumber

    public static class ChangePhoneOrFaxNumberEvent extends ActionDomainEvent {

        public ChangePhoneOrFaxNumberEvent(final PhoneOrFaxNumber source, final Identifier identifier) {
            super(source, identifier);
        }

        public ChangePhoneOrFaxNumberEvent(
                final PhoneOrFaxNumber source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ChangePhoneOrFaxNumberEvent(
                final PhoneOrFaxNumber source,
                final Identifier identifier,
                final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = ChangePhoneOrFaxNumberEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    @ActionLayout(named = "Change Number")
    public PhoneOrFaxNumber changePhoneOrFaxNumber(
            @ParameterLayout(named = "Phone Number")
            @Parameter(regexPattern = CommChannelModule.Regex.PHONE_NUMBER)
            final String phoneNumber) {
        setPhoneNumber(phoneNumber);

        return this;
    }

    public String default0ChangePhoneOrFaxNumber() {
        return getPhoneNumber();
    }


    //endregion

    //region > Predicates

    public static class Predicates {
        private Predicates(){}

        public static Predicate<PhoneOrFaxNumber> equalTo(
                final String phoneNumber,
                final CommunicationChannelType communicationChannelType) {
            return new Predicate<PhoneOrFaxNumber>() {
                @Override
                public boolean apply(final PhoneOrFaxNumber input) {
                    return  Objects.equals(phoneNumber, input.getPhoneNumber()) &&
                            Objects.equals(communicationChannelType, input.getType());
                }
            };
        }
    }

    //endregion

}
