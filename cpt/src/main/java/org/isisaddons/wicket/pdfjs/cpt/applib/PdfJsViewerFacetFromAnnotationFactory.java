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

package org.isisaddons.wicket.pdfjs.cpt.applib;

import java.lang.reflect.Method;

import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facetapi.FacetUtil;
import org.apache.isis.core.metamodel.facetapi.FeatureType;
import org.apache.isis.core.metamodel.facets.Annotations;
import org.apache.isis.core.metamodel.facets.FacetFactoryAbstract;

public class PdfJsViewerFacetFromAnnotationFactory extends FacetFactoryAbstract {

    public PdfJsViewerFacetFromAnnotationFactory() {
        super(FeatureType.PROPERTIES_AND_ACTIONS);
    }

    @Override
    public void process(final ProcessMethodContext processMethodContext) {

        final FacetHolder holder = processMethodContext.getFacetHolder();
        final Method method = processMethodContext.getMethod();

        final PdfJsViewer pdfjsViewer = Annotations.getAnnotation(method, PdfJsViewer.class);

        if (pdfjsViewer != null) {
            PdfJsViewerFacetAbstract pdfJsViewerFacet = PdfJsViewerFacetFromAnnotation.create(pdfjsViewer, holder);
            servicesInjector.injectServicesInto(pdfJsViewerFacet);
            FacetUtil.addFacet(pdfJsViewerFacet);
        }
    }

    @Override
    public void processParams(ProcessParameterContext processParameterContext) {
        super.processParams(processParameterContext);
    }
}
