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
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import com.google.common.collect.Lists;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.eventbus.EventBusService;
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

    //region > from (property)
    private CommunicationChannel from;

    @Column(allowsNull = "false", name = "communicationChannel_id")
    @MemberOrder(sequence = "10")
    public CommunicationChannel getFrom() {
        return from;
    }

    public void setFrom(final CommunicationChannel CommunicationChannel) {
        this.from = CommunicationChannel;
    }
    //endregion

    //region > toObjectType (property)
    private String toObjectType;

    @PropertyLayout(
            labelPosition = LabelPosition.TOP
    )
    @MemberOrder(sequence = "20")
    public String getToObjectType() {
        return toObjectType;
    }

    public void setToObjectType(final String toObjectType) {
        this.toObjectType = toObjectType;
    }
    //endregion

    //region > toIdentifier (property)
    private String toIdentifier;

    @PropertyLayout(
            labelPosition = LabelPosition.NONE
    )
    @MemberOrder(sequence = "22")
    public String getToIdentifier() {
        return toIdentifier;
    }

    public void setToIdentifier(final String toIdentifier) {
        this.toIdentifier = toIdentifier;
    }
    //endregion

    //region > to (derived property)

    @javax.jdo.annotations.NotPersistent
    private transient CommunicationChannelOwner to;

    @Programmatic
    public CommunicationChannelOwner getTo() {
        if (this.to == null) {
            final Bookmark bookmark = new Bookmark(getToObjectType(), getToIdentifier());
            this.to = (CommunicationChannelOwner) bookmarkService.lookup(bookmark);
        }
        return to;
    }

    @Programmatic
    public void setTo(final CommunicationChannelOwner to) {
        this.to = to;
        final Bookmark bookmark = bookmarkService.bookmarkFor(to);
        setToObjectType(bookmark.getObjectType());
        setToIdentifier(bookmark.getIdentifier());
    }

    public static class PersistingEvent extends java.util.EventObject {

        private final List<Runnable> runnables = Lists.newArrayList();
        private final CommunicationChannelOwnerLink link;
        private final CommunicationChannel from;
        private final CommunicationChannelOwner to;

        public PersistingEvent(
                final CommunicationChannelOwnerLink source,
                final CommunicationChannel from,
                final CommunicationChannelOwner to) {
            super(source);
            link = source;
            this.from = from;
            this.to = to;
        }

        public CommunicationChannelOwnerLink getLink() {
            return link;
        }

        public CommunicationChannel getFrom() {
            return from;
        }

        public CommunicationChannelOwner getTo() {
            return to;
        }

        void runRunnables(final DomainObjectContainer container) {
            for (Runnable runnable : runnables) {
                container.injectServicesInto(runnable);
                runnable.run();
            }
        }

        /**
         * Provides a mechanism for subscribers to attach additional behaviour to be exercised on the
         * persist.
         *
         * <p>
         *     The publisher of this event guarantees to all runnables, and will also automatically inject services
         *     into any provided runnables using the
         *     {@link org.apache.isis.applib.DomainObjectContainer#injectServicesInto(Object) domain object container}.
         * </p>
         * <p>
         *     Subscribers should make no assumptions as
         *     to the order in which any runnables are run.
         * </p>
         */
        public void addRunnable(final Runnable runnable) {
            runnables.add(runnable);
        }
    }


    @Programmatic
    public void persisting() {
        final PersistingEvent event = new PersistingEvent(this, getFrom(), getTo());
        eventBusService.post(event);

        event.runRunnables(container);
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
    private DomainObjectContainer container;

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private EventBusService eventBusService;
    //endregion

}
