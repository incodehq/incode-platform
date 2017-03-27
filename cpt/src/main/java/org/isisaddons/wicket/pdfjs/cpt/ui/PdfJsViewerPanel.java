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

import org.apache.wicket.Component;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.handler.resource.ResourceRequestHandler;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.IResource;
import org.wicketstuff.pdfjs.PdfJsConfig;
import org.wicketstuff.pdfjs.PdfJsPanel;

import org.apache.isis.applib.value.Blob;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.components.scalars.ScalarPanelAbstract;

import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewerFacet;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;

/**
 *
 */
class PdfJsViewerPanel extends ScalarPanelAbstract implements IResourceListener {
    private static final long serialVersionUID = 1L;

    private static final String ID_SCALAR_NAME = "scalarName";
    private static final String ID_SCALAR_VALUE = "scalarValue";
    private static final String ID_FEEDBACK = "feedback";

    PdfJsViewerPanel(String id, ScalarModel scalarModel) {
        super(id, scalarModel);
    }


    @Override
    protected MarkupContainer addComponentForRegular() {


        MarkupContainer containerIfRegular = new WebMarkupContainer("scalarIfRegular");
        addOrReplace(containerIfRegular);

        final ObjectAdapter adapter = scalarModel.getObject();
        if (adapter != null) {
            final PdfJsViewerFacet pdfJsViewerFacet = scalarModel.getFacet(PdfJsViewerFacet.class);
            PdfJsConfig config = pdfJsViewerFacet != null ? pdfJsViewerFacet.getConfig() : new PdfJsConfig();
            config.withDocumentUrl(urlFor(IResourceListener.INTERFACE, null));
            PdfJsPanel pdfJsPanel = new PdfJsPanel(ID_SCALAR_VALUE, config);

            MarkupContainer prevPageButton = createComponent("prevPage", config);
            MarkupContainer nextPageButton = createComponent("nextPage", config);
            MarkupContainer zoomInButton = createComponent("zoomIn", config);
            MarkupContainer zoomOutButton = createComponent("zoomOut", config);
            MarkupContainer currentPageLabel = createComponent("currentPage", config);
            MarkupContainer totalPagesLabel = createComponent("totalPages", config);
            MarkupContainer currentZoomSelect = createComponent("currentZoom", config);
            MarkupContainer currentHeightSelect = createComponent("currentHeight", config);
            MarkupContainer printButton = createComponent("print", config);

            containerIfRegular.addOrReplace(pdfJsPanel, prevPageButton, nextPageButton, zoomInButton, zoomOutButton, currentPageLabel, totalPagesLabel, currentZoomSelect, currentHeightSelect, printButton);

            containerIfRegular.addOrReplace(new NotificationPanel(ID_FEEDBACK, pdfJsPanel, new ComponentFeedbackMessageFilter(pdfJsPanel)));
        } else {
            permanentlyHide(ID_SCALAR_VALUE, ID_FEEDBACK);
        }

        return containerIfRegular;
    }

    @Override
    protected Component addComponentForCompact() {
        final Blob blob = getBlob();
        if (blob == null) {
            return null;
        }
        WebMarkupContainer containerIfCompact = new WebMarkupContainer("scalarIfCompact");
        addOrReplace(containerIfCompact);

        final IResource bar = new ByteArrayResource(blob.getMimeType().getBaseType(), blob.getBytes(), blob.getName());
        final ResourceLink<Void> dowloadLink = new ResourceLink<>("scalarIfCompactDownload", bar);
        containerIfCompact.add(dowloadLink);

        Label fileNameIfCompact = new Label("fileNameIfCompact", blob.getName());
        dowloadLink.add(fileNameIfCompact);

        return containerIfCompact;
    }

    private MarkupContainer createComponent(final String id, final PdfJsConfig config) {
        return new WebMarkupContainer(id) {
            @Override
            protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("data-canvas-id", config.getCanvasId());
            }
        };
    }

    @Override
    protected void addFormComponentBehavior(final Behavior behavior) {
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        response.render(CssHeaderItem.forReference(new CssResourceReference(PdfJsViewerPanel.class, "PdfJsViewerPanel.css")));
        response.render(JavaScriptHeaderItem.forReference(new PdfJsViewerReference()));
    }


    @Override
    public void onResourceRequested() {
        Blob pdfBlob = getBlob();
        if (pdfBlob != null) {
            final byte[] bytes = pdfBlob.getBytes();
            final ByteArrayResource resource = new ByteArrayResource("application/pdf", bytes);
            final ResourceRequestHandler handler = new ResourceRequestHandler(resource, null);
            getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
        } else {
            throw new AbortWithHttpErrorCodeException(404);
        }
    }

    private Blob getBlob() {
        Blob pdfBlob = null;
        final ObjectAdapter adapter = getModel().getObject();
        if (adapter != null) {
            pdfBlob = (Blob) adapter.getObject();
        }
        return pdfBlob;
    }
}
