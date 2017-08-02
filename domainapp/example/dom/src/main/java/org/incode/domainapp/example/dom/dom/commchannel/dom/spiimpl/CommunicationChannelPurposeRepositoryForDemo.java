package org.incode.domainapp.example.dom.dom.commchannel.dom.spiimpl;

import java.util.Arrays;
import java.util.Collection;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.dom.spi.CommunicationChannelPurposeRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class CommunicationChannelPurposeRepositoryForDemo implements CommunicationChannelPurposeRepository {

    @Override
    public Collection<String> purposesFor(
            final CommunicationChannelType communicationChannelType,
            final Object owner) {

        switch (communicationChannelType) {
        case EMAIL_ADDRESS:
            return Arrays.asList("Home Email", "Work Email", "Other Email");
        case POSTAL_ADDRESS:
            return Arrays.asList("Billing Address", "Shipping Address");
        case PHONE_NUMBER:
            return Arrays.asList("Home Number", "Work Number", "Mobile Number");
        case FAX_NUMBER:
            return Arrays.asList("Home Fax", "Work Fax");
        }

        return null;
    }
}
