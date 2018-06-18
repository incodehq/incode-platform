package org.incode.example.commchannel.dom.spi;

import java.util.Collection;

import org.incode.example.commchannel.CommChannelModule;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;

/**
 * Optional SPI service
 */
public interface CommunicationChannelPurposeRepository {

    /**
     * Return a collection of strings to describe the purpose of {@link CommunicationChannel}s, eg 'home', 'work' etc.
     *
     * <p>
     *     Each string should be no longer than
     *     {@value CommChannelModule.JdoColumnLength#PURPOSE} characters in length.
     * </p>
     *
     * <p>
     *     May return <tt>null</tt> if there are none (in which case a default will be used).
     * </p>
     * @param communicationChannelType
     * @param communicationChannel
     */
    Collection<String> purposesFor(
            final CommunicationChannelType communicationChannelType,
            final Object communicationChannel);

}
