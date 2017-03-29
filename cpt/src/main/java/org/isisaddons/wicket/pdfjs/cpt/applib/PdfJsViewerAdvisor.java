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

import org.wicketstuff.pdfjs.PdfJsConfig;

import org.apache.isis.applib.annotation.Programmatic;

/**
 * SPI service interface.
 */
public interface PdfJsViewerAdvisor {

    @Programmatic
    Advice advise(final PdfJsViewer.RenderKey renderKey);

    @Programmatic
    void pageNumChangedTo(final PdfJsViewer.RenderKey renderKey, final int pageNum);
    @Programmatic
    void scaleChangedTo(final PdfJsViewer.RenderKey renderKey, final PdfJsConfig.Scale scale);
    @Programmatic
    void heightChangedTo(final PdfJsViewer.RenderKey renderKey, final int height);

    /**
     * Immutable value type.
     *
     * <p>
     *     The <code>withXxx</code> allow clones of the value to be created, for convenience of implementors.
     * </p>
     */
    class Advice implements Serializable {

        private final Integer pageNum;
        private final PdfJsConfig.Scale scale;
        private final Integer height;

        public Advice(final Integer pageNum, final PdfJsConfig.Scale scale, final Integer height) {
            this.pageNum = pageNum;
            this.scale = scale;
            this.height = height;
        }


        public Integer getPageNum() {
            return pageNum;
        }

        public PdfJsConfig.Scale getScale() {
            return scale;
        }

        public Integer getHeight() {
            return height;
        }

        public Advice withPageNum(Integer pageNum) {
            return new Advice(pageNum, this.scale, this.height);
        }

        public Advice withScale(PdfJsConfig.Scale scale) {
            return new Advice(this.pageNum, scale, this.height);
        }

        public Advice withHeight(Integer height) {
            return new Advice(this.pageNum, this.scale, height);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            final Advice config = (Advice) o;

            if (pageNum != null ? !pageNum.equals(config.pageNum) : config.pageNum != null)
                return false;
            if (scale != null ? !scale.equals(config.scale) : config.scale != null)
                return false;
            return height != null ? height.equals(config.height) : config.height == null;
        }

        @Override
        public int hashCode() {
            int result = pageNum != null ? pageNum.hashCode() : 0;
            result = 31 * result + (scale != null ? scale.hashCode() : 0);
            result = 31 * result + (height != null ? height.hashCode() : 0);
            return result;
        }

        @Override public String toString() {
            return "Advise{" +
                    "pageNum=" + pageNum +
                    ", scale=" + scale +
                    ", height=" + height +
                    '}';
        }
    }
}
