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

import java.util.Collection;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.purpose.CommunicationChannelPurposeService;

@Mixin
public class CommunicationChannel_updatePurpose {

    //region > injected services
    @Inject
    private CommunicationChannelPurposeService communicationChannelPurposeService;
    @Inject
    private DomainObjectContainer container;
    //endregion

    //region > constructor
    private final CommunicationChannel<?> communicationChannel;
    public CommunicationChannel_updatePurpose(final CommunicationChannel<?> communicationChannel) {
        this.communicationChannel = communicationChannel;
    }

    @Programmatic
    public CommunicationChannel<?> getCommunicationChannel() {
        return communicationChannel;
    }
    //endregion

    //region > mixins
    private CommunicationChannel_owner mixinOwner() {
        return container.mixin(CommunicationChannel_owner.class, getCommunicationChannel());
    }
    //endregion

    public static class DomainEvent extends CommunicationChannel.ActionDomainEvent<CommunicationChannel_updatePurpose> { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public CommunicationChannel<?> $$(
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.PURPOSE, optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Purpose")
            final String purpose) {
        communicationChannel.setPurpose(purpose);
        return communicationChannel;
    }

    public <T extends CommunicationChannel<T>> String default0$$() {
        return communicationChannel.getPurpose();
    }

    public Collection<String> choices0$$() {
        return communicationChannelPurposeService.purposesFor(getCommunicationChannel().getType(), mixinOwner().$$());
    }


}
