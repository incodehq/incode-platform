package org.incode.example.commchannel.dom.impl.purpose;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.example.commchannel.dom.spi.CommunicationChannelPurposeRepository;

/**
 * Simple wrapper around {@link CommunicationChannelPurposeRepository}, that always returns a non-null list (including the {@link #DEFAULT_PURPOSE default} if necessary).
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CommunicationChannelPurposeService {

    public String getId() {
        return "incodeCommChannel.CommunicationChannelPurposeService";
    }


    public static final String DEFAULT_PURPOSE = "(default)";

    /**
     * Return the list of objects to act as a "purpose" for the {@link CommunicationChannel}s, as per
     * {@link CommunicationChannelPurposeRepository}, or a default name otherwise.
     *
     * <p>
     *     May return null if there are none (in which case a default name will be used).
     * </p>
     */
    @Programmatic
    public Collection<String> purposesFor(
            final CommunicationChannelType communicationChannelType,
            final Object communicationChannelOwner) {
        final Set<String> fallback = Collections.singleton(DEFAULT_PURPOSE);
        if(communicationChannelPurposeRepository == null) {
            return fallback;
        }
        final Collection<String> purposes =
                communicationChannelPurposeRepository
                        .purposesFor(communicationChannelType, communicationChannelOwner);
        return purposes != null? purposes: fallback;
    }

    @Programmatic
    public String defaultIfNoSpi() {
        if (communicationChannelPurposeRepository == null) {
            return DEFAULT_PURPOSE; // no SPI, so okay to return the default
        }
        return null; // ie there is an SPI; we don't have enough info to guess which should be the default
    }

    @Inject
    CommunicationChannelPurposeRepository communicationChannelPurposeRepository;

}
