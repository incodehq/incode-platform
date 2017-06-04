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
package org.isisaddons.wicket.summernote.cpt.ui.editor;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteEditor;

import org.apache.wicket.Component;
import org.apache.wicket.core.util.string.JavaScriptUtils;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.isisaddons.wicket.summernote.cpt.applib.SummernoteEditorFacetAbstract;
import org.apache.isis.core.metamodel.adapter.mgr.AdapterManager;
import org.apache.isis.viewer.wicket.model.models.EntityModel;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.components.scalars.TextFieldValueModel;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;

public class SummernoteEditorPanel extends PanelAbstract<ScalarModel> implements TextFieldValueModel.ScalarModelProvider {

    private static final long serialVersionUID = 1L;

    public SummernoteEditorPanel(final String id, final ScalarModel model) {
        super(id, model);

        buildGui();
    }

    private void buildGui() {

        final ScalarModel model = getModel();

        addOrReplace(new Label("label", model.getName()));

        if (EntityModel.Mode.EDIT.equals(model.getMode())) {
            addOrReplace(getComponentForRegular());
        } else {
            addOrReplace(getComponentForCompact());
        }
    }

    protected Component getComponentForCompact() {
        Fragment fragment = new Fragment("fragment", "compact", SummernoteEditorPanel.this);
        Label label = new Label("editor", new TextFieldValueModel<>(this));
        label.setEscapeModelStrings(false);
        fragment.add(label);
        return fragment;
    }

    protected Component getComponentForRegular() {
        Fragment fragment = new Fragment("fragment", "regular", SummernoteEditorPanel.this);

        SummernoteEditorFacetAbstract editorFacet = getModel().getFacet(SummernoteEditorFacetAbstract.class);
        SummernoteConfig config = editorFacet.getConfig();

        fragment.add(new SummernoteEditor("editor", new TextFieldValueModel<>(this), config) {
            @Override
            public void renderHead(IHeaderResponse response) {
                super.renderHead(response);

                response.render(OnDomReadyHeaderItem.forScript(String.format("debugger;$('#%s').code('%s')",
                                                                     getMarkupId(), JavaScriptUtils.escapeQuotes(getModelObject()))));
            }
        });
        return fragment;
    }

    @Override
    protected void onModelChanged() {
        buildGui();
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        buildGui();
    }

    @Override
    public AdapterManager getAdapterManager() {
        return getPersistenceSession();
    }
}
