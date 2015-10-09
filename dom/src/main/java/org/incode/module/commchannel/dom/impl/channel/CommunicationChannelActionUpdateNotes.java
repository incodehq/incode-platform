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

import org.apache.isis.applib.Identifier;
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
public class CommunicationChannelActionUpdateNotes {

    public static class UpdateNotes
            extends CommunicationChannel.ActionDomainEvent<CommunicationChannelActionUpdateNotes> { }

    @Action(
            domainEvent = UpdateNotes.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public <T extends CommunicationChannel<T>> CommunicationChannel<T> updateNotes(
            CommunicationChannel<T> communicationChannel,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Notes", multiLine = 10)
            final String Notes) {
        communicationChannel.setNotes(Notes);
        return communicationChannel;
    }

    public <T extends CommunicationChannel<T>> String default1UpdateNotes(
            CommunicationChannel<T> communicationChannel
            ) {
        return communicationChannel.getNotes();
    }

}
