/*
 *  Copyright 2014 Dan Haywood
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
package org.isisaddons.wicket.pdfjs.fixture.dom.demo;

import java.io.Serializable;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.bookmark.Bookmark;

import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewerAdvisor;

@DomainService(nature = NatureOfService.DOMAIN)
public class PdfJsViewerAdvisorForDemoApp implements PdfJsViewerAdvisor {

    public static class Key implements Serializable {
        private final Bookmark bookmark;
        private final String propertyId;
        private final String username;

        public Key(final Bookmark bookmark, final String propertyId, final String username) {
            this.bookmark = bookmark;
            this.propertyId = propertyId;
            this.username = username;
        }

        public Bookmark getBookmark() {
            return bookmark;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public String getUsername() {
            return username;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            final Key key = (Key) o;

            if (bookmark != null ? !bookmark.equals(key.bookmark) : key.bookmark != null)
                return false;
            if (propertyId != null ? !propertyId.equals(key.propertyId) : key.propertyId != null)
                return false;
            return username != null ? username.equals(key.username) : key.username == null;
        }

        @Override public int hashCode() {
            int result = bookmark != null ? bookmark.hashCode() : 0;
            result = 31 * result + (propertyId != null ? propertyId.hashCode() : 0);
            result = 31 * result + (username != null ? username.hashCode() : 0);
            return result;
        }
    }

    public static class Data implements Serializable {
        private final Integer pageNum;
        private final Double scale;
        private final Integer height;

        public Data(final Integer pageNum, final Double scale, final Integer height) {
            this.pageNum = pageNum;
            this.scale = scale;
            this.height = height;
        }

        public Integer getPageNum() {
            return pageNum;
        }

        public Double getScale() {
            return scale;
        }

        public Integer getHeight() {
            return height;
        }

        public Data withPageNum(Integer pageNum) {
            return new Data(pageNum, this.scale, this.height);
        }

        public Data withScale(Double scale) {
            return new Data(this.pageNum, scale, this.height);
        }

        public Data withHeight(Integer height) {
            return new Data(this.pageNum, this.scale, height);
        }

        public Advice asAdvice() {
            return new Advice(this.pageNum, this.scale, this.height);
        }
    }

    // singleton version for now
    private Data data = new Data(1, 1.0d, 800);

    @Override
    public Advice advise() {
        return data.asAdvice();
    }

    @Override
    public void pageNumChangedTo(final int pageNum) {
        this.data = this.data.withPageNum(pageNum);
    }

    @Override
    public void scaleChangedTo(final double scale) {
        this.data = this.data.withScale(scale);
    }

    @Override
    public void heightChangedTo(final int height) {
        this.data = this.data.withHeight(height);
    }

}
