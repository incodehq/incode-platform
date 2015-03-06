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

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.eventbus.EventBusService;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByCommunicationChannel", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.comms.CommunicationChannelOwnerLink "
                        + "WHERE communicationChannel == :communicationChannel")
})
@javax.jdo.annotations.Unique(name="CommunicationChannelOwnerLink_source_destination_UNQ", members = {"source,destinationObjectType,destinationIdentifier"})
@DomainObject(
        objectType = "comms.CommunicationChannelOwnerLink"
)
public class CommunicationChannelOwnerLink implements Comparable<CommunicationChannelOwnerLink> {

    //region > CommunicationChannel (property)
    private CommunicationChannel source;

    @MemberOrder(sequence = "10")
    public CommunicationChannel getSource() {
        return source;
    }

    public void setSource(final CommunicationChannel CommunicationChannel) {
        this.source = CommunicationChannel;
    }
    //endregion

    //region > destinationObjectType (property)
    private String destinationObjectType;

    @PropertyLayout(
            labelPosition = LabelPosition.TOP
    )
    @MemberOrder(sequence = "20")
    public String getDestinationObjectType() {
        return destinationObjectType;
    }

    public void setDestinationObjectType(final String destinationObjectType) {
        this.destinationObjectType = destinationObjectType;
    }
    //endregion

    //region > destinationId (property)
    private String destinationIdentifier;

    @PropertyLayout(
            labelPosition = LabelPosition.NONE
    )
    @MemberOrder(sequence = "22")
    public String getDestinationIdentifier() {
        return destinationIdentifier;
    }

    public void setDestinationIdentifier(final String destinationIdentifier) {
        this.destinationIdentifier = destinationIdentifier;
    }
    //endregion

    //region > destination (programmatic property)
    @Programmatic
    public CommunicationChannelOwner getDestination() {
        final Bookmark bookmark = new Bookmark(getDestinationObjectType(), getDestinationIdentifier());
        return (CommunicationChannelOwner) bookmarkService.lookup(bookmark);
    }

    public static class DestinationDomainEvent extends PropertyDomainEvent<CommunicationChannelOwnerLink, CommunicationChannelOwner> {

        public DestinationDomainEvent(final CommunicationChannelOwnerLink source, final Identifier identifier) {
            super(source, identifier);
        }

        public DestinationDomainEvent(final CommunicationChannelOwnerLink source, final Identifier identifier, final CommunicationChannelOwner oldValue, final CommunicationChannelOwner newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @Programmatic
    public void setDestination(final CommunicationChannelOwner destination) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(destination);
        setDestinationObjectType(bookmark.getObjectType());
        setDestinationIdentifier(bookmark.getIdentifier());

        final Identifier identifier = Identifier.propertyOrCollectionIdentifier(CommunicationChannelOwnerLink.class, "destination");
        final DestinationDomainEvent domainEvent =
                new DestinationDomainEvent(this, identifier, null, destination);
        eventBusService.post(domainEvent);
    }

    //endregion

    //region > compareTo

    @Override
    public int compareTo(final CommunicationChannelOwnerLink other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private EventBusService eventBusService;
    //endregion

}
