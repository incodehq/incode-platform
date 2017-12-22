package org.incode.example.commchannel.dom.impl.phoneorfax;

import java.util.Objects;

import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Predicate;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;

import org.incode.example.commchannel.dom.CommChannelModule;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;

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
            editing = Editing.DISABLED,
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
