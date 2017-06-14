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
