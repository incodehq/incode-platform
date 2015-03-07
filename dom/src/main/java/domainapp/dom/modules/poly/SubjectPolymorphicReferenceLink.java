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
package domainapp.dom.modules.poly;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

public abstract class SubjectPolymorphicReferenceLink<S, PR, L extends SubjectPolymorphicReferenceLink<S,PR,L>>  implements Comparable<L> {

    private final String titlePattern;

    protected SubjectPolymorphicReferenceLink(final String titlePattern) {
        this.titlePattern = titlePattern;
    }

    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr(
                titlePattern,
                "polymorphicReference", container.titleOf(getPolymorphicReference()),
                "subject", container.titleOf(getSubjectObj()));
    }
    //endregion

    //region > subject (property)
    @Programmatic
    public abstract S getSubjectObj();
    @Programmatic
    public abstract void setSubjectObj(S subjectObj);
    //endregion

    //region > polymorphicReferenceObjectType (property)
    public abstract String getPolymorphicReferenceObjectType();

    public abstract void setPolymorphicReferenceObjectType(final String polymorphicReferenceObjectType);
    //endregion

    //region > polymorphicReferenceIdentifier (property)
    public abstract String getPolymorphicReferenceIdentifier();

    public abstract void setPolymorphicReferenceIdentifier(final String polymorphicReferenceIdentifier);
    //endregion

    //region > polymorphicReference (derived property)

    @Programmatic
    public PR getPolymorphicReference() {
        final Bookmark bookmark = new Bookmark(getPolymorphicReferenceObjectType(), getPolymorphicReferenceIdentifier());
        return (PR) bookmarkService.lookup(bookmark);
    }

    /**
     * Subclasses should optionally override in order to set the type-safe equivalent.
     */
    @Programmatic
    public void setPolymorphicReference(final PR polymorphicReference) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(polymorphicReference);
        setPolymorphicReferenceObjectType(bookmark.getObjectType());
        setPolymorphicReferenceIdentifier(bookmark.getIdentifier());
    }

    //endregion

    //region > compareTo

    @Override
    public int compareTo(final SubjectPolymorphicReferenceLink other) {
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
