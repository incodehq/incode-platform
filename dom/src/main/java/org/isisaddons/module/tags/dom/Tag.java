/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
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
package org.isisaddons.module.tags.dom;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import com.google.common.base.Function;
import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.util.ObjectContracts;

/**
 * Represents a general purpose mechanism for tagging (or labelling) any entity with a key:value pair.
 * The associated {@link org.isisaddons.module.tags.dom.Tags}
 * domain service provides functionality to infer the available values from the existing {@link Tag}s of a particular
 * key.
 *
 * <p>
 * For example, the tag key could be <i>Brand</i>, with different {@link #getTaggedObject() object}s tagged as
 * (have a {@link #getValue() value of} &quot;Coca-Cola&quot;, &quot;Pepsi&quot;, &quot;McDonalds&quot;,
 * &quot;Levi's&quot; and so on.  A different tag key would be used to represent some other domain of values, eg to
 * represent the <i>Sector</i>.
 *
 * <pre>
 * entity1 -brandTag -> Tag: name="Brand", value="Coca-Cola"
 *         -sectorTag-> Tag: name="Sector", value="Drink"

 * entity2 -brandTag -> Tag: name="Brand", value="Pepsi"
 *         -sectorTag-> Tag: name="Sector", value="Drink"

 * entity3 -brandTag -> Tag: name="Brand", value="McDonalds"
 *         -sectorTag-> Tag: name="Sector", value="Fast Food"
 *
 * entity4 -brandTag -> Tag: name="Brand", value="Levi's"
 *         -sectorTag-> Tag: name="Sector", value="Clothing"
 * </pre>
 */
@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        table = "IsisAddonsTag")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=IdGeneratorStrategy.NATIVE, 
        column="id")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findValuesByObjectTypeAndKey", language = "JDOQL",
                value = "SELECT DISTINCT value "
                        + "FROM org.isisaddons.module.tags.dom.Tag "
                        + "WHERE taggedObjectType == :objectType"
                        + "   && key              == :key"),
})
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER, 
        column = "version")
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name = "Tag_taggedObject_key_UNQ",
            // this column order supports the findValuesByObjectTypeAndKey query
            members = { "taggedObjectType", "key", "taggedIdentifier" })
})
@MemberGroupLayout(columnSpans = {4,4,4,12},
    left = {"General"},
    middle = {"Tagged"},
    right = {"Value"}
)
@Immutable
public class Tag extends AbstractDomainObject
        implements Comparable<Tag>, WithNameGetter {

    // //////////////////////////////////////

    private String key;

    /**
     * The key of this tag, for example <tt>Brand</tt> or <tt>Sector</tt>.
     *
     * <p>
     * The combination of ({@link #getTaggedObjectType() objectType}, {@link #getKey() key}) is unique.
     */
    @MemberOrder(name = "General", sequence = "1")
    @javax.jdo.annotations.Column(allowsNull = "false", length=JdoColumnLength.NAME)
    @Disabled
    public String getKey() {
        return key;
    }

    public void setKey(final String tagName) {
        this.key = tagName;
    }

    // //////////////////////////////////////

    /**
     * Derived (polymorphic) association to any object.
     */
    @javax.jdo.annotations.NotPersistent
    @MemberOrder(name = "Tagged", sequence = "1")
    @Named("Object")
    public Object getTaggedObject() {
        return bookmarkService.lookup(new Bookmark(getTaggedObjectType(), getTaggedIdentifier()));
    }

    public void setTaggedObject(final Object domainObject) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(domainObject);
        this.setTaggedObjectType(bookmark.getObjectType());
        this.setTaggedIdentifier(bookmark.getIdentifier());
    }

    // //////////////////////////////////////

    private String taggedObjectType;

    @javax.jdo.annotations.Column(allowsNull = "false", length=JdoColumnLength.FQCN)
    @Hidden
    public String getTaggedObjectType() {
        return taggedObjectType;
    }

    public void setTaggedObjectType(final String objectType) {
        this.taggedObjectType = objectType;
    }

    // //////////////////////////////////////

    private String taggedIdentifier;

    @javax.jdo.annotations.Column(allowsNull = "false", length=JdoColumnLength.IDENTIFIER)
    @Hidden
    public String getTaggedIdentifier() {
        return taggedIdentifier;
    }

    public void setTaggedIdentifier(final String taggedIdentifier) {
        this.taggedIdentifier = taggedIdentifier;
    }

    // //////////////////////////////////////

    private String value;

    @javax.jdo.annotations.Column(allowsNull = "false", length=JdoColumnLength.VALUE)
    @Title
    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    // //////////////////////////////////////

    private final static class JdoColumnLength {
        final static int NAME = 50;
        final static int VALUE = 128;
        final static int FQCN = 254;
        final static int IDENTIFIER = 254;
    }

    private static ObjectContracts OBJECT_CONTRACTS = new ObjectContracts().with(WithNameGetter.ToString.evaluator());

    private final static String KEY_PROPERTIES = "taggedObjectType, taggedIdentifier, key";

    // //////////////////////////////////////

    @Hidden
    public String getId() {
        Object objectId = JDOHelper.getObjectId(this);
        if(objectId == null) {
            return "";
        }
        String objectIdStr = objectId.toString();
        final String id = objectIdStr.split("\\[OID\\]")[0];
        return id;
    }

    @Hidden
    public Long getVersionSequence() {
        final Long version = (Long) JDOHelper.getVersion(this);
        return version;
    }

    // //////////////////////////////////////

    @Override
    public int compareTo(final Tag other) {
        return OBJECT_CONTRACTS.compare(this, other, KEY_PROPERTIES);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    BookmarkService bookmarkService;

    // //////////////////////////////////////

    public static class Functions {
        private Functions(){}

        public static final Function<Tag, String> GET_KEY = new Function<Tag, String>() {
            public String apply(final Tag tag) {
                return tag != null ? tag.getKey() : null;
            }
        };

        public static final Function<Tag, String> GET_VALUE = new Function<Tag, String>() {
            public String apply(final Tag tag) {
                return tag != null ? tag.getValue() : null;
            }
        };
    }

}
