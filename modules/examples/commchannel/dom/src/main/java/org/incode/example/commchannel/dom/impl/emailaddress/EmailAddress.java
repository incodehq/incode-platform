package org.incode.example.commchannel.dom.impl.emailaddress;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;

import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.CommChannelModule;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        schema = "incodeCommChannel"
)
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
        objectType = "incodeCommChannel.EmailAddress"
)
public class EmailAddress extends CommunicationChannel<EmailAddress> {

    //region > events
    public static abstract class PropertyDomainEvent<S,T> extends CommChannelModule.PropertyDomainEvent<S, T> { }
    public static abstract class CollectionDomainEvent<S,T> extends CommChannelModule.CollectionDomainEvent<S, T> { }
    public static abstract class ActionDomainEvent<S> extends CommChannelModule.ActionDomainEvent<S> { }
    //endregion

    //region > title
    public String title() {
        return getEmailAddress();
    }
    //endregion


    public static class EmailAddressDomainEvent extends PropertyDomainEvent<EmailAddress, String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(
            allowsNull = "true", // optional only because of superclass inheritance strategy=SUPERCLASS_TABLE
            length = CommChannelModule.JdoColumnLength.EMAIL_ADDRESS
    )
    @Property(
            domainEvent = EmailAddressDomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.MANDATORY
    )
    private String emailAddress;


}