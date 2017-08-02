package org.incode.module.commchannel.dom.impl.emailaddress;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.CommChannelModule;

@Mixin
public class EmailAddress_update {

    //region > constructor, mixedIn accessor
    private final EmailAddress emailAddress;
    public EmailAddress_update(final EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Programmatic
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }
    //endregion


    public static class DomainEvent extends EmailAddress.ActionDomainEvent<EmailAddress_update> { }
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = DomainEvent.class
    )
    public EmailAddress $$(
            @Parameter(
                    regexPattern = CommChannelModule.Regex.EMAIL_ADDRESS,
                    maxLength = CommChannelModule.JdoColumnLength.EMAIL_ADDRESS
            )
            @ParameterLayout(named = "Email Address")
            final String address) {
        this.emailAddress.setEmailAddress(address);
        return this.emailAddress;
    }

    public String default0$$() {
        return this.emailAddress.getEmailAddress();
    }


}