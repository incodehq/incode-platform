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
package org.incode.module.commchannel.dom.impl.phoneorfax;

import java.util.Objects;

import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Predicate;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
    schema = "incodeCommChannel"
)
// identityType=IdentityType.DATASTORE inherited from superclass
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
// no @DatastoreIdentity nor @Version, since inherited from supertype
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "PhoneNumber_phoneNumber_IDX",
                members = { "phoneNumber" })
})
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "incodeCommChannel.PhoneOrFaxNumber"
)
public class PhoneOrFaxNumber extends CommunicationChannel<PhoneOrFaxNumber> {

    //region > events
    public static abstract class PropertyDomainEvent<S, T> extends CommChannelModule.PropertyDomainEvent<S, T> { }
    public static abstract class CollectionDomainEvent<S, T> extends CommChannelModule.CollectionDomainEvent<S, T> { }
    public static abstract class ActionDomainEvent<S> extends CommChannelModule.ActionDomainEvent<S> { }
    //endregion

    //region > title
    public String title() {
        return getPhoneNumber();
    //endregion
    }
    //endregion

    public static class PhoneNumberDomainEvent extends PropertyDomainEvent<PhoneOrFaxNumber, String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(
            allowsNull = "true",
            length = CommChannelModule.JdoColumnLength.PHONE_NUMBER
    )
    @Property(
            domainEvent = PhoneNumberDomainEvent.class,
            optionality = Optionality.MANDATORY
    )
    private String phoneNumber;



    public static class Predicates {
        private Predicates(){}

        public static Predicate<PhoneOrFaxNumber> equalTo(
                final String phoneNumber,
                final CommunicationChannelType communicationChannelType) {
            return input -> Objects.equals(phoneNumber, input.getPhoneNumber()) &&
                    Objects.equals(communicationChannelType, input.getType());
        }
    }

}
