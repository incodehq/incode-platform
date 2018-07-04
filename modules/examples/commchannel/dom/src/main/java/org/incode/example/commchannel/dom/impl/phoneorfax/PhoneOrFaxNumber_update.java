package org.incode.example.commchannel.dom.impl.phoneorfax;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.example.commchannel.CommChannelModule;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;

@Mixin
public class PhoneOrFaxNumber_update {

    //region > constructor, mixedIn accessor
    private final PhoneOrFaxNumber phoneOrFaxNumber;
    public PhoneOrFaxNumber_update(final PhoneOrFaxNumber phoneOrFaxNumber) {
        this.phoneOrFaxNumber = phoneOrFaxNumber;
    }

    @Programmatic
    public PhoneOrFaxNumber getPhoneOrFaxNumber() {
        return phoneOrFaxNumber;
    }

    //endregion

    public static class DomainEvent extends PhoneOrFaxNumber.ActionDomainEvent<PhoneOrFaxNumber_update> { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public PhoneOrFaxNumber $$(
            @ParameterLayout(named = "Type")
            final CommunicationChannelType type,
            @ParameterLayout(named = "Phone Number")
            @Parameter(
                    maxLength = CommChannelModule.JdoColumnLength.PHONE_NUMBER,
                    regexPattern = CommChannelModule.Regex.PHONE_NUMBER
            )
            final String phoneNumber) {
        this.phoneOrFaxNumber.setType(type);
        this.phoneOrFaxNumber.setPhoneNumber(phoneNumber);

        return this.phoneOrFaxNumber;
    }

    public List<CommunicationChannelType> choices1$$() {
        return CommunicationChannelType.matching(PhoneOrFaxNumber.class);
    }

    public CommunicationChannelType default0$$() {
        return this.phoneOrFaxNumber.getType();
    }

    public String default1$$() {
        return this.phoneOrFaxNumber.getPhoneNumber();
    }


}
