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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.facets.value.image.ImageValueFacet;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;

/**
 * {@link PanelAbstract Panel} that represents a {@link EntityCollectionModel
 * collection of entity}s rendered using {@link AjaxFallbackDefaultDataTable}.
 */
public class PdfViewerPanel extends PanelAbstract<ScalarModel> {
    private static final long serialVersionUID = 1L;
    private static final String ID_SCALAR_NAME = "scalarName";
    private static final String ID_SCALAR_VALUE = "scalarValue";
    private static final String ID_FEEDBACK = "feedback";

    public PdfViewerPanel(String id, ScalarModel scalarModel) {
        super(id, scalarModel);
        this.buildGui();
    }

    private void buildGui() {
        String name = this.getModel().getName();
        Label scalarName = new Label("scalarName", name);
        this.addOrReplace(new Component[]{scalarName});
        ImageValueFacet imageValueFacet = this.getModel().getTypeOfSpecification().getFacet(ImageValueFacet.class);
        ObjectAdapter adapter = this.getModel().getObject();
        if(adapter != null) {
            final Image imageValue = imageValueFacet.getImage(adapter);
            RenderedDynamicImageResource imageResource =
                    new RenderedDynamicImageResource(imageValue.getWidth(null), imageValue.getHeight(null)) {
                private static final long serialVersionUID = 1L;

                protected boolean render(Graphics2D graphics, Attributes attributes) {
                    graphics.drawImage(imageValue, 0, 0, null);
                    return true;
                }
            };
            org.apache.wicket.markup.html.image.Image image = new org.apache.wicket.markup.html.image.Image("scalarValue", imageResource);
            this.addOrReplace(image);
            this.addOrReplace(new NotificationPanel("feedback", image, new ComponentFeedbackMessageFilter(image)));
        } else {
            this.permanentlyHide("scalarValue", "feedback");
        }

    }
}
