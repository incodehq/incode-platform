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

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewerAdvisor;

@DomainService(nature = NatureOfService.DOMAIN)
public class PdfJsViewerAdvisorForDemoApp implements PdfJsViewerAdvisor {

    Integer height = 800;
    Double scale = 1.0d;

    @Override
    public Advice advise() {
        return new Advice(scale, height);
    }

    @Override
    public void scaleChangedTo(final double scale) {
        this.scale = scale;
    }

    @Override public void heightChangedTo(final int height) {
        this.height = height;
    }
}
