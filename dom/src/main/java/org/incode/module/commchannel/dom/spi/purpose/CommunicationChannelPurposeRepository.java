/*
 *
 *  Copyright 2015 incode.org
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.commchannel.dom.spi.purpose;

import java.util.Collection;

import org.incode.module.commchannel.dom.api.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

/**
 * Optional SPI service
 */
public interface CommunicationChannelPurposeRepository {

    /**
     * Return a collection of strings to describe the purpose of {@link CommunicationChannel}s, eg 'home', 'work' etc.
     *
     * <p>
     *     Each string should be no longer than
     *     {@value org.incode.module.commchannel.dom.CommChannelModule.JdoColumnLength#PURPOSE} characters in length.
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
            final CommunicationChannelOwner communicationChannel);

}
