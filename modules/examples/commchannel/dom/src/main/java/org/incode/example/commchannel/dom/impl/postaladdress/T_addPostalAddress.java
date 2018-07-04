package org.incode.example.commchannel.dom.impl.postaladdress;

import java.util.Collection;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;

import org.incode.example.commchannel.dom.impl.purpose.CommunicationChannelPurposeService;
import org.incode.example.commchannel.CommChannelModule;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;

public abstract class T_addPostalAddress<T> {


    //region > constructor
    private final T communicationChannelOwner;
    public T_addPostalAddress(final T communicationChannelOwner) {
        this.communicationChannelOwner = communicationChannelOwner;
    }
    //endregion

    //region > $$

    public static class DomainEvent extends CommChannelModule.ActionDomainEvent<T_addPostalAddress> { }

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            domainEvent = DomainEvent.class
    )
    @ActionLayout(
            named = "Postal",
            cssClassFa = "fa-plus",
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "CommunicationChannels", sequence = "1")
    public Object $$(
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
            @ParameterLayout(named = "Address Line 1")
            final String addressLine1,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address Line 2")
            final String addressLine2,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address Line 3")
            final String addressLine3,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address Line 4")
            final String addressLine4,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.POSTAL_CODE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Postal Code")
            final String postalCode,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.COUNTRY, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Country")
            final String country,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.PURPOSE, optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Purpose")
            final String purpose,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Notes", multiLine = 10)
            final String notes,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Lookup geocode")
            final Boolean lookupGeocode) {

        final PostalAddress postalAddress =
                postalAddressRepository.newPostal(
                        this.communicationChannelOwner,
                        addressLine1, addressLine2, addressLine3, addressLine4,
                        postalCode, country,
                        purpose, notes
                );

        mixinUpdate(postalAddress).lookupAndUpdateGeocode(
                lookupGeocode, addressLine1, addressLine2, addressLine3, addressLine4, postalCode, country);

        return this.communicationChannelOwner;
    }

    public Collection<String> choices6$$() {
        return communicationChannelPurposeService.purposesFor(CommunicationChannelType.POSTAL_ADDRESS,
                this.communicationChannelOwner);
    }

    public String default6$$() {
        final Collection<String> purposes = choices6$$();
        return purposes.isEmpty()? null : purposes.iterator().next();
    }

    //endregion

    //region > mixins
    private PostalAddress_update mixinUpdate(final PostalAddress postalAddress) {
        return factoryService.mixin(PostalAddress_update.class, postalAddress);
    }
    //endregion

    //region > injected services
    @Inject
    PostalAddressRepository postalAddressRepository;
    @Inject
    CommunicationChannelPurposeService communicationChannelPurposeService;
    @Inject
    FactoryService factoryService;
    //endregion


}
