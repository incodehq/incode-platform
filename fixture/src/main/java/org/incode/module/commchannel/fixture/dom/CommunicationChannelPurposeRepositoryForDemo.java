/*
 *  Copyright 2014 Dan Haywood
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
package org.incode.module.commchannel.fixture.dom;

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
