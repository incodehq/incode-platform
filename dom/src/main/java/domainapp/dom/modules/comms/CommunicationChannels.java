/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package domainapp.dom.modules.comms;

import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(repositoryFor = CommunicationChannel.class)
@DomainServiceLayout(menuOrder = "10")
public class CommunicationChannels {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<CommunicationChannel> listAll() {
        return container.allInstances(CommunicationChannel.class);
    }
    //endregion


    //region > create (action)
    @MemberOrder(sequence = "3")
    public CommunicationChannel create(
            final @ParameterLayout(named = "Name") String name,
            final CommunicationChannelOwner owner) {
        final CommunicationChannel communicationChannel = container.newTransientInstance(CommunicationChannel.class);
        communicationChannel.setDetails(name);

        final CommunicationChannelOwnerLink ownerLink = container.newTransientInstance(CommunicationChannelOwnerLink.class);
        ownerLink.setFrom(communicationChannel);
        communicationChannel.setOwnerLink(ownerLink);
        ownerLink.join(owner);

        // should persist both
        container.persistIfNotAlready(communicationChannel);
        return communicationChannel;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion
}
