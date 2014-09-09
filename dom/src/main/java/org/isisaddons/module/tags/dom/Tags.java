/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.module.tags.dom;

import java.util.List;
import javax.jdo.Query;
import com.google.common.base.Strings;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

@DomainService
public class Tags {

    /**
     * Returns the list of available values for a particular Tag key for other entities of the same
     * {@link org.apache.isis.applib.services.bookmark.Bookmark#getObjectType() object type} as the provided object.
     *
     * <p>
     * For example, an object with a <i>Sector</i> tag could use the following to determine the Sector
     * values used by other objects of the same type:
     *
     * <pre>
     * public List<String> choicesSector() {
     *     return tags.choices(this, "Sector");
     * }
     * </pre>
     */
    @Programmatic
    public List<String> choices(final Object taggedObject, final String tagKey) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(taggedObject);
        final String objectType = bookmark.getObjectType();

        final Query q = isisJdoSupport.getJdoPersistenceManager().newNamedQuery(Tag.class, "findValuesByObjectTypeAndKey");
        final List<String> results = (List<String>) q.execute(objectType, tagKey);
        return results;
    }

    @Programmatic
    public Tag tagFor(
            final Object taggedObject,
            final Tag existingTag,
            final String tagKey,
            final String tagValue) {

        if(Strings.isNullOrEmpty(tagValue)) {
            return removeExisting(existingTag);
        } else if(existingTag == null) {
            return createNew(taggedObject, tagKey, tagValue);
        } else {
            return updateExisting(existingTag, tagValue);
        }
    }

    private Tag createNew(final Object taggedObject, final String tagKey, final String tagValue) {
        // create new
        Tag newTag = container.newTransientInstance(Tag.class);
        newTag.setTaggedObject(taggedObject);
        newTag.setTaggedObjectType(determineObjectTypeFor(taggedObject));
        newTag.setKey(tagKey);
        newTag.setValue(tagValue);
        container.persist(newTag);
        return newTag;
    }

    private static Tag updateExisting(final Tag tag, final String tagValue) {
        // update existing
        tag.setValue(tagValue);
        return tag;
    }

    private Tag removeExisting(final Tag tag) {
        if(tag != null) {
            // remove existing
            container.remove(tag);
        }
        return null;
    }

    private String determineObjectTypeFor(final Object taggedObject) {
        if (taggedObject == null) {
            return null;
        } 
        final Bookmark bookmark = bookmarkService.bookmarkFor(taggedObject);
        if (bookmark == null) {
            return null;
        }
        return bookmark.getObjectType();

    }

    // //////////////////////////////////////


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    BookmarkService bookmarkService;

    @javax.inject.Inject
    DomainObjectContainer container;

}
