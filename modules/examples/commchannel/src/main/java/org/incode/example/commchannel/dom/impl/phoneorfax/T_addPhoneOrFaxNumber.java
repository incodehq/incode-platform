package org.incode.example.commchannel.dom.impl.phoneorfax;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.example.commchannel.dom.CommChannelModule;
import org.incode.example.commchannel.dom.impl.purpose.CommunicationChannelPurposeService;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;

public abstract class T_addPhoneOrFaxNumber<T> {

    //region > constructor and mixedIn accessor
    private final T communicationChannelOwner;
    public T_addPhoneOrFaxNumber(final T communicationChannelOwner) {
        this.communicationChannelOwner = communicationChannelOwner;
    }

    @Programmatic
    public T getCommunicationChannelOwner() {
        return communicationChannelOwner;
    }

    //endregion

    //region > $$

    public static class DomainEvent extends CommChannelModule.ActionDomainEvent
                                            <T_addPhoneOrFaxNumber>  { }
    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            named = "Phone/Fax",
            cssClassFa = "fa-plus",
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "CommunicationChannels", sequence = "3")
    public Object $$(
            @ParameterLayout(named = "Type")
            final CommunicationChannelType type,
            @ParameterLayout(named = "Phone Number")
            @Parameter(
                    maxLength = CommChannelModule.JdoColumnLength.PHONE_NUMBER,
                    regexPattern = CommChannelModule.Regex.PHONE_NUMBER
            )
            final String phoneNumber,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.PURPOSE, optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Purpose")
            final String purpose,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Notes", multiLine = 10)
            final String notes) {
        phoneOrFaxNumberRepository.newPhoneOrFax(this.communicationChannelOwner, type, phoneNumber, purpose, notes);
        return this.communicationChannelOwner;
    }

    public String validate0$$(final CommunicationChannelType type) {
        final List<CommunicationChannelType> validChoices = choices0$$();
        return validChoices.contains(type)? null: "Communication type must be " + validChoices;
    }

    public List<CommunicationChannelType> choices0$$() {
        return CommunicationChannelType.matching(PhoneOrFaxNumber.class);
    }

    public CommunicationChannelType default0$$() {
        return choices0$$().get(0);
    }

    public Collection<String> choices2$$(final CommunicationChannelType type) {
        return communicationChannelPurposeService.purposesFor(type, this.communicationChannelOwner);
    }

    public String default2$$() {
        return communicationChannelPurposeService.defaultIfNoSpi();
    }

    //endregion

    //region > injected services
    @Inject
    CommunicationChannelPurposeService communicationChannelPurposeService;
    @Inject
    PhoneOrFaxNumberRepository phoneOrFaxNumberRepository;
    //endregion

}
