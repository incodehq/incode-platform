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
package org.isisaddons.wicket.pdfjs.cpt.ui;

import java.util.Objects;

import javax.activation.MimeType;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import org.apache.isis.applib.value.Blob;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;

import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewerFacet;

public class PdfViewerPanelComponentFactory extends ComponentFactoryAbstract {

    private static final long serialVersionUID = 1L;

    public PdfViewerPanelComponentFactory() {
        super(ComponentType.SCALAR_NAME_AND_VALUE, PdfJsViewerPanel.class);
    }

    public ApplicationAdvice appliesTo(IModel<?> model) {
        if (!(model instanceof ScalarModel)) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }

        final ScalarModel scalarModel = (ScalarModel) model;
        final PdfJsViewerFacet facet = scalarModel.getFacet(PdfJsViewerFacet.class);
        if(facet == null || facet.isNoop()) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }

        final ObjectAdapter objectAdapter = scalarModel.getObject();
        final boolean isPdf = isPdf(objectAdapter);
        return this.appliesIf(isPdf);
    }

    private static boolean isPdf(final ObjectAdapter objectAdapter) {
        if (objectAdapter == null) {
            return false;
        }
        final Object modelObject = objectAdapter.getObject();
        if (!(modelObject instanceof Blob)) {
            return false;
        }
        final Blob blob = (Blob) modelObject;
        final MimeType mimeType = blob.getMimeType();
        return Objects.equals("application", mimeType.getPrimaryType()) &&
               Objects.equals("pdf", mimeType.getSubType());
    }

    public Component createComponent(String id, IModel<?> model) {
        ScalarModel scalarModel = (ScalarModel) model;
        return new PdfJsViewerPanel(id, scalarModel);
    }
}

