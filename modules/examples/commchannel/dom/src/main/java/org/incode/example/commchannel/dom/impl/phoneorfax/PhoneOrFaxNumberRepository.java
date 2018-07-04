package org.incode.example.commchannel.dom.impl.phoneorfax;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel_owner;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PhoneOrFaxNumber.class
)
public class PhoneOrFaxNumberRepository {

    public String getId() {
        return "incodeCommChannel.PhoneOrFaxNumberRepository";
    }

    //region > newPhoneOrFax (programmatic)

    @Programmatic
    public PhoneOrFaxNumber newPhoneOrFax(
            final Object owner,
            final CommunicationChannelType type,
            final String number,
            final String description,
            final String notes) {
        final PhoneOrFaxNumber pn = container.newTransientInstance(PhoneOrFaxNumber.class);
        pn.setType(type);
        pn.setPhoneNumber(number);
        owner(pn).setOwner(owner);

        pn.setPurpose(description);
        pn.setNotes(notes);

        container.persistIfNotAlready(pn);
        return pn;
    }

    //endregion

    //region > findByPhoneOrFaxNumber (programmatic)
    @Programmatic
    public PhoneOrFaxNumber findByPhoneOrFaxNumber(
            final Object owner,
            final String phoneNumber) {

        final Optional<PhoneOrFaxNumber> phoneNumberIfFound = findByPhoneOrFaxNumber(owner, phoneNumber, CommunicationChannelType.PHONE_NUMBER);
        if(phoneNumberIfFound.isPresent()) {
            return phoneNumberIfFound.get();
        }

        final Optional<PhoneOrFaxNumber> faxNumberIfFound = findByPhoneOrFaxNumber(owner, phoneNumber, CommunicationChannelType.FAX_NUMBER);
        return faxNumberIfFound.orNull();
    }

    private Optional<PhoneOrFaxNumber> findByPhoneOrFaxNumber(final Object owner, final String phoneNumber, final CommunicationChannelType communicationChannelType) {
        final List<CommunicationChannelOwnerLink> links =
                linkRepository.findByOwnerAndCommunicationChannelType(owner, communicationChannelType);
        final Iterable<PhoneOrFaxNumber> phoneOrFaxNumbers =
                Iterables.transform(
                        links,
                        CommunicationChannelOwnerLink.Functions.communicationChannel(PhoneOrFaxNumber.class));
        return Iterables.tryFind(phoneOrFaxNumbers, PhoneOrFaxNumber.Predicates.equalTo(phoneNumber, communicationChannelType));
    }
    //endregion


    private CommunicationChannel_owner owner(final CommunicationChannel<?> cc) {
        return container.mixin(CommunicationChannel_owner.class, cc);
    }

    @Inject
    CommunicationChannelOwnerLinkRepository linkRepository;
    @Inject
    DomainObjectContainer container;


}
