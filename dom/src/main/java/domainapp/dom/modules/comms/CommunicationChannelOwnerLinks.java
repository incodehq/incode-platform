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

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY,
        repositoryFor = CommunicationChannelOwnerLink.class
)
public class CommunicationChannelOwnerLinks {

    //region > findBySubject (programmatic)
    @Programmatic
    public CommunicationChannelOwnerLink findBySubject(final CommunicationChannel communicationChannel) {
        return container.firstMatch(
                new QueryDefault<>(CommunicationChannelOwnerLink.class,
                        "findBySubject",
                        "subject", communicationChannel));
    }
    //endregion

    //region > findByPolymorphicReference (programmatic)
    @Programmatic
    public CommunicationChannelOwnerLink findByPolymorphicReference(final CommunicationChannelOwner polymorphicReference) {
        if(polymorphicReference == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(polymorphicReference);
        if(bookmark == null) {
            return null;
        }
        return container.firstMatch(
                new QueryDefault<>(CommunicationChannelOwnerLink.class,
                        "findByPolymorphicReference",
                        "polymorphicReferenceObjectType", bookmark.getObjectType(),
                        "polymorphicReferenceIdentifier", bookmark.getIdentifier()));
    }
    //endregion


    //region > injected services
    @javax.inject.Inject
    private CommunicationChannelsContributions communicationChannels;

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    //endregion

}
