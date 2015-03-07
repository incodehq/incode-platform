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

import domainapp.dom.modules.poly.PolymorphicLinkInstantiateEvent;

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
                name = "findBySubject", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.comms.CommunicationChannelOwnerLink "
                        + "WHERE subject == :subject"),
        @javax.jdo.annotations.Query(
                name = "findByPolymorphicReference", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.comms.CommunicationChannelOwnerLink "
                        + "WHERE polymorphicReferenceObjectType == :polymorphicReferenceObjectType "
                        + "   && polymorphicReferenceIdentifier == :polymorphicReferenceIdentifier ")
})
@javax.jdo.annotations.Unique(name="CommunicationChannelOwnerLink_source_destination_UNQ", members = {"source,destinationObjectType,destinationIdentifier"})
@DomainObject(
        objectType = "comms.CommunicationChannelOwnerLink"
)
public abstract class CommunicationChannelOwnerLinkORIG implements Comparable<CommunicationChannelOwnerLinkORIG> {

    public static class InstantiateEvent extends PolymorphicLinkInstantiateEvent<CommunicationChannelOwnerLinkORIG, CommunicationChannel, CommunicationChannelOwner> {

        public InstantiateEvent(final Object source, final CommunicationChannel subject, final CommunicationChannelOwner owner) {
            super(CommunicationChannelOwnerLinkORIG.class, source, subject, owner);
        }
    }

    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr(
                "{polymorphicReference} owns {subject}",
                "polymorphicReference", container.titleOf(getPolymorphicReference()),
                "subject", container.titleOf(getSubject()));
    }
    //endregion

    //region > subject (property)
    private CommunicationChannel subject;

    @Column(
            allowsNull = "false",
            name = "communicationChannel_id"
    )
    @MemberOrder(sequence = "10")
    public CommunicationChannel getSubject() {
        return subject;
    }

    public void setSubject(final CommunicationChannel CommunicationChannel) {
        this.subject = CommunicationChannel;
    }
    //endregion

    //region > polymorphicReferenceObjectType (property)
    private String polymorphicReferenceObjectType;

    @Column(allowsNull = "false", length = 255)
    @PropertyLayout(
            labelPosition = LabelPosition.TOP
    )
    @MemberOrder(sequence = "20")
    public String getPolymorphicReferenceObjectType() {
        return polymorphicReferenceObjectType;
    }

    public void setPolymorphicReferenceObjectType(final String polymorphicReferenceObjectType) {
        this.polymorphicReferenceObjectType = polymorphicReferenceObjectType;
    }
    //endregion

    //region > polymorphicReferenceIdentifier (property)
    private String polymorphicReferenceIdentifier;

    @Column(allowsNull = "false", length = 255)
    @PropertyLayout(
            labelPosition = LabelPosition.NONE
    )
    @MemberOrder(sequence = "22")
    public String getPolymorphicReferenceIdentifier() {
        return polymorphicReferenceIdentifier;
    }

    public void setPolymorphicReferenceIdentifier(final String polymorphicReferenceIdentifier) {
        this.polymorphicReferenceIdentifier = polymorphicReferenceIdentifier;
    }
    //endregion

    //region > polymorphicReference (derived property)

    @Programmatic
    public CommunicationChannelOwner getPolymorphicReference() {
        final Bookmark bookmark = new Bookmark(getPolymorphicReferenceObjectType(), getPolymorphicReferenceIdentifier());
        return (CommunicationChannelOwner) bookmarkService.lookup(bookmark);
    }

    /**
     * Subclasses should optionally override in order to set the type-safe equivalent.
     */
    @Programmatic
    public void setPolymorphicReference(final CommunicationChannelOwner polymorphicReference) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(polymorphicReference);
        setPolymorphicReferenceObjectType(bookmark.getObjectType());
        setPolymorphicReferenceIdentifier(bookmark.getIdentifier());
    }

    //endregion

    //region > compareTo

    @Override
    public int compareTo(final CommunicationChannelOwnerLinkORIG other) {
        return ObjectContracts.compare(this, other, "subject,polymorphicReferenceObjectType,polymorphicReferenceIdentifier");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    //endregion

}
