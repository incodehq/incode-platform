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
package org.incode.module.commchannel.dom.impl.purpose;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.commchannel.dom.api.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.dom.spi.purpose.CommunicationChannelPurposeRepository;

/**
 * Simple wrapper around {@link org.incode.module.commchannel.dom.spi.purpose.CommunicationChannelPurposeRepository}, that always returns a non-null list (including the {@link #DEFAULT_PURPOSE default} if necessary).
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CommunicationChannelPurposeService {

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
            final CommunicationChannelOwner communicationChannelOwner,
            final CommunicationChannelType communicationChannelType) {
        final Collection<String> purposes =
                communicationChannelPurposeRepository.purposesFor(communicationChannelOwner, communicationChannelType);
        return purposes != null? purposes: Collections.singleton(DEFAULT_PURPOSE);
    }

    @Inject
    CommunicationChannelPurposeRepository communicationChannelPurposeRepository;

}
