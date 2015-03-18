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
package org.isisaddons.module.poly.dom;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.inject.Inject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.NonRecoverableException;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.eventbus.EventBusService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

public abstract class PolymorphicAssociationLink<S, P, L extends PolymorphicAssociationLink<S, P, L>>  implements Comparable<L> {

    public abstract static class InstantiateEvent<S, P, L extends PolymorphicAssociationLink<S, P, L>> extends java.util.EventObject {

        private final Class<L> linkType;
        private final S subject;
        private final P polymorphicReference;

        private Class<? extends L> subtype;

        protected InstantiateEvent(final Class<L> linkType, final Object source, final S subject, final P polymorphicReference) {
            super(source);
            this.linkType = linkType;
            this.subject = subject;
            this.polymorphicReference = polymorphicReference;
        }

        public S getSubject() {
            return subject;
        }

        public P getPolymorphicReference() {
            return polymorphicReference;
        }

        /**
         * For the factory (that will actually instantiate the link) to call.
         */
        public Class<? extends L> getSubtype() {
            return subtype;
        }

        /**
         * For the subscriber (that wishes to specify the subtype to use) to call.
         * @param subtype
         */
        public void setSubtype(final Class<? extends L> subtype) {
            if(this.subtype != null) {
                throw new IllegalArgumentException(String.format("A subtype ('%s') has already been set", subtype.getName()));
            }
            this.subtype = subtype;
        }
    }


    public static class Factory<S,PR,L extends PolymorphicAssociationLink<S,PR,L>,E extends InstantiateEvent<S,PR,L>> {

        public enum PersistStrategy {
            AUTOMATIC,
            MANUAL
        }

        private PersistStrategy persistStrategy;

        private final Object eventSource;
        private final Class<E> eventType;
        private final Class<L> linkType;
        private final Class<S> subjectType;
        private final Class<PR> polymorphicReferenceType;

        final Constructor<E> eventConstructor;

        public Factory(
                final Object eventSource,
                final Class<S> subjectType,
                final Class<PR> polymorphicReferenceType,
                final Class<L> linkType, final Class<E> eventType) {
            this(eventSource, subjectType, polymorphicReferenceType, linkType, eventType, PersistStrategy.AUTOMATIC);
        }

        public Factory(
                final Object eventSource,
                final Class<S> subjectType,
                final Class<PR> polymorphicReferenceType,
                final Class<L> linkType, final Class<E> eventType,
                final PersistStrategy persistStrategy) {
            this.eventSource = eventSource;
            this.subjectType = subjectType;
            this.polymorphicReferenceType = polymorphicReferenceType;
            this.eventType = eventType;
            this.linkType = linkType;
            this.persistStrategy = persistStrategy;

            try {
                eventConstructor = eventType.getConstructor(Object.class, subjectType, polymorphicReferenceType);
            } catch (final NoSuchMethodException e) {
                throw new IllegalArgumentException(String.format(
                        "Could not locate constructor in eventType '%s' accepting (%s, %s, %s)",
                        eventType.getName(),
                        Object.class.getName(),
                        subjectType.getName(),
                        polymorphicReferenceType.getName()));
            }
        }

        /**
         * Instantiates the appropriate link entity (using the subtype specified by subscriber) and persists.
         *
         * <p>
         *     Note that although the link is persisted (using {@link org.apache.isis.applib.DomainObjectContainer#persist(Object)}), the transaction is <i>NOT</i> flushed.
         *     This means that the caller can set additional state on the object.
         * </p>
         */
        public L createLink(final S subject, final PR polymorphicReference) {

            final E event = instantiateEvent(eventSource, subject, polymorphicReference);
            eventBusService.post(event);

            final Class<? extends L> subtype = event.getSubtype();
            if(subtype == null) {
                throw new NonRecoverableException("Cannot create link to " + container.titleOf(polymorphicReference) + ", no subtype provided");
            }

            if(persistStrategy == PersistStrategy.AUTOMATIC) {
                if(!container.isPersistent(subject) || !container.isPersistent(polymorphicReference)) {
                    // in case there are persists pending
                    container.flush();

                    if(!container.isPersistent(subject)) {
                        throw new NonRecoverableException("Link's subject " +  container.titleOf(subject) + " is not persistent");
                    }
                    if(!container.isPersistent(polymorphicReference)) {
                        throw new NonRecoverableException("Link's polymorphic reference " +  container.titleOf(polymorphicReference) + " is not persistent");
                    }
                }
            }

            final L link = container.newTransientInstance(subtype);
            link.setPolymorphicReference(polymorphicReference);

            link.setSubject(subject);

            if(persistStrategy == PersistStrategy.AUTOMATIC) {
                container.persist(link);
            }

            return link;
        }

        private E instantiateEvent(final Object eventSource, final S subject, final PR polymorphicReference) {
            try {
                return eventConstructor.newInstance(eventSource, subject, polymorphicReference);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        @Inject
        DomainObjectContainer container;
        @Inject
        EventBusService eventBusService;
    }


    private final String titlePattern;

    protected PolymorphicAssociationLink(final String titlePattern) {
        this.titlePattern = titlePattern;
    }

    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr(
                titlePattern,
                "polymorphicReference", container.titleOf(getPolymorphicReference()),
                "subject", container.titleOf(getSubject()));
    }
    //endregion

    //region > subject (property)
    @Programmatic
    public abstract S getSubject();
    @Programmatic
    public abstract void setSubject(S subject);
    //endregion

    //region > polymorphicObjectType (property)
    @Programmatic
    public abstract String getPolymorphicObjectType();

    @Programmatic
    public abstract void setPolymorphicObjectType(final String polymorphicObjectType);
    //endregion

    //region > polymorphicIdentifier (property)
    @Programmatic
    public abstract String getPolymorphicIdentifier();

    @Programmatic
    public abstract void setPolymorphicIdentifier(final String polymorphicIdentifier);
    //endregion

    //region > polymorphicReference (derived property)

    @Programmatic
    public P getPolymorphicReference() {
        final Bookmark bookmark = new Bookmark(getPolymorphicObjectType(), getPolymorphicIdentifier());
        return (P) bookmarkService.lookup(bookmark);
    }

    /**
     * Subclasses should optionally override in order to set the type-safe equivalent.
     */
    @Programmatic
    public void setPolymorphicReference(final P polymorphicReference) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(polymorphicReference);
        setPolymorphicObjectType(bookmark.getObjectType());
        setPolymorphicIdentifier(bookmark.getIdentifier());
    }

    //endregion

    //region > compareTo

    @Override
    public int compareTo(final PolymorphicAssociationLink other) {
        return ObjectContracts.compare(this, other, "subject,polymorphicObjectType,polymorphicIdentifier");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    //endregion


}
