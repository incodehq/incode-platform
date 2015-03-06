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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByFrom", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.comms.CommunicationChannelOwnerLink "
                        + "WHERE from == :from"),
        @javax.jdo.annotations.Query(
                name = "findByTo", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.comms.CommunicationChannelOwnerLink "
                        + "WHERE toObjectType == :toObjectType "
                        + "   && toIdentifier == :toIdentifier ")
})
@javax.jdo.annotations.Unique(name="CommunicationChannelOwnerLink_source_destination_UNQ", members = {"source,destinationObjectType,destinationIdentifier"})
@DomainObject(
        objectType = "comms.CommunicationChannelOwnerLink"
)
public abstract class CommunicationChannelOwnerLink implements Comparable<CommunicationChannelOwnerLink> {

    public static class InstantiateEvent extends java.util.EventObject {

        private final CommunicationChannel from;
        private final CommunicationChannelOwner to;

        private Class<? extends CommunicationChannelOwnerLink> subtype;

        public InstantiateEvent(final Object source, final CommunicationChannel from, final CommunicationChannelOwner to) {
            super(source);
            this.from = from;
            this.to = to;
        }

        public CommunicationChannel getFrom() {
            return from;
        }

        public CommunicationChannelOwner getTo() {
            return to;
        }

        /**
         * For the factory (that will actually instantiate the link) to call.
         */
        public Class<? extends CommunicationChannelOwnerLink> getSubtype() {
            return subtype;
        }

        /**
         * For the subscriber (that wishes to specify the subtype to use) to call.
         */
        public void setSubtype(final Class<? extends CommunicationChannelOwnerLink> subtype) {
            if(this.subtype != null) {
                throw new IllegalArgumentException(String.format("A subtype ('%s') has already been set", subtype.getName()));
            }
            this.subtype = subtype;
        }

    }

    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr(
                "{from} owns {to}",
                "from", container.titleOf(getTo()),
                "to", container.titleOf(getFrom()));
    }
    //endregion


    //region > from (property)
    private CommunicationChannel from;

    @Column(
            allowsNull = "true", // only because we have a 1:1 relationship between two tables; one must be optional
            name = "communicationChannel_id"
    )
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

    @Column(allowsNull = "false", length = 255)
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

    @Column(allowsNull = "false", length = 255)
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

    @Programmatic
    public CommunicationChannelOwner getTo() {
        final Bookmark bookmark = new Bookmark(getToObjectType(), getToIdentifier());
        CommunicationChannelOwner to = (CommunicationChannelOwner) bookmarkService.lookup(bookmark);
        return to;
    }

    /**
     * Subclasses should optionally override in order to set the type-safe equivalent.
     */
    @Programmatic
    public void setTo(final CommunicationChannelOwner to) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(to);
        setToObjectType(bookmark.getObjectType());
        setToIdentifier(bookmark.getIdentifier());
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

    //endregion

}
