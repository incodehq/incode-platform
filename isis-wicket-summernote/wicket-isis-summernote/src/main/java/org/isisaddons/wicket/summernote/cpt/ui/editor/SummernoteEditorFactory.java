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

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.model.models.ValueModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import org.isisaddons.wicket.summernote.cpt.applib.SummernoteEditorFacetAbstract;

/**
 * {@link ComponentFactory} for {@link SummernoteEditorPanel}.
 */
public class SummernoteEditorFactory extends ComponentFactoryAbstract {

    private static final long serialVersionUID = 1L;

    public SummernoteEditorFactory() {
        super(ComponentType.SCALAR_NAME_AND_VALUE);
    }

    @Override
    public ApplicationAdvice appliesTo(final IModel<?> model) {

        if (!(model instanceof ScalarModel)) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }
        final ScalarModel scalarModel = (ScalarModel) model;

        SummernoteEditorFacetAbstract facet = scalarModel.getFacet(SummernoteEditorFacetAbstract.class);
        return appliesExclusivelyIf(facet != null);
    }

    @Override
    public Component createComponent(final String id, final IModel<?> scalarModel) {
        return new SummernoteEditorPanel(id, (ScalarModel)scalarModel);
    }
}
