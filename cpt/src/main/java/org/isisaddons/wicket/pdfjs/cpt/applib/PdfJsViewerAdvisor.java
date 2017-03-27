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

import org.apache.isis.applib.annotation.Programmatic;

/**
 * SPI service interface.
 */
public interface PdfJsViewerAdvisor {

    @Programmatic
    Advice advise();

    @Programmatic
    void scaleChangedTo(final double height);
    @Programmatic
    void heightChangedTo(final int height);

    public static class Advice {

        private final Double scale;
        private final Integer height;

        public Advice(final Double scale, final Integer height) {
            this.scale = scale;
            this.height = height;
        }

        public Double getScale() {
            return scale;
        }

        public Integer getHeight() {
            return height;
        }

    }
}
