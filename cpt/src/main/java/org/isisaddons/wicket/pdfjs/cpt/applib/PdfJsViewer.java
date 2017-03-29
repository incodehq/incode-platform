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
package org.isisaddons.wicket.pdfjs.cpt.applib;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.wicketstuff.pdfjs.PdfJsConfig;

import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.value.Blob;

/**
 * An annotation that could be applied on a property or parameter
 * of type {@link org.apache.isis.applib.value.Blob}. Such property/parameter will be visualized
 * with <a href="https://github.com/mozilla/pdf.js">PDF.js</a> viewer.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD})
public @interface PdfJsViewer {

    int initialPageNum() default 1;
    PdfJsConfig.Scale initialScale() default PdfJsConfig.Scale._1_00;
    int initialHeight() default 800;

    /**
     * Identifier for the rendering of a specific property of an object for an named individual.
     *
     * <p>
     *     This is a (serializable) value type so that, for example, implementations can use as a key within a hash structure.
     * </p>
     */
    class RenderKey implements Serializable {

        private final Bookmark bookmark;
        private final String propertyId;
        private final String username;

        public RenderKey(final Bookmark bookmark, final String propertyId, final String username) {
            this.bookmark = bookmark;
            this.propertyId = propertyId;
            this.username = username;
        }

        /**
         * The object being rendered.
         */
        public Bookmark getBookmark() {
            return bookmark;
        }

        /**
         * The property of the object (a {@link Blob} containing a PDF) being rendered.
         */
        public String getPropertyId() {
            return propertyId;
        }

        /**
         * The user for whom the object's property is being rendered.
         */
        public String getUsername() {
            return username;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            final RenderKey renderKey = (RenderKey) o;

            if (bookmark != null ? !bookmark.equals(renderKey.bookmark) : renderKey.bookmark != null)
                return false;
            if (propertyId != null ? !propertyId.equals(renderKey.propertyId) : renderKey.propertyId != null)
                return false;
            return username != null ? username.equals(renderKey.username) : renderKey.username == null;
        }

        @Override
        public int hashCode() {
            int result = bookmark != null ? bookmark.hashCode() : 0;
            result = 31 * result + (propertyId != null ? propertyId.hashCode() : 0);
            result = 31 * result + (username != null ? username.hashCode() : 0);
            return result;
        }

        @Override public String toString() {
            return "RenderKey{" +
                    "bookmark=" + bookmark +
                    ", propertyId='" + propertyId + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

}
