/*
 *
Copyright 2015 incode.org
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
package org.incode.module.commchannel.dom.impl.channel;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CommunicationChannel_updateDescription {

    public static class UpdateDescriptionEvent
            extends CommunicationChannel.ActionDomainEvent<CommunicationChannel_updateDescription> { }

    @Action(
            domainEvent = UpdateDescriptionEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public <T extends CommunicationChannel<T>> CommunicationChannel<T> updateDescription(
            CommunicationChannel<T> communicationChannel,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Description")
            final String description) {
        communicationChannel.setDescription(description);
        return communicationChannel;
    }

    public <T extends CommunicationChannel<T>> String default1UpdateDescription( CommunicationChannel<T> communicationChannel) {
        return communicationChannel.getDescription();
    }

}
