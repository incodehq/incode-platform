package org.isisaddons.wicket.wickedcharts.cpt.ui.scalarchart;

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.specloader.SpecificationLoader;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.models.ValueModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;

/**
 * {@link ComponentFactory} for {@link StandaloneValueAsWickedChart}.
 */
public class StandaloneValueAsWickedChartFactory extends ComponentFactoryAbstract {

    private static final long serialVersionUID = 1L;

    public StandaloneValueAsWickedChartFactory() {
        super(ComponentType.VALUE);
    }

    @Override
    public ApplicationAdvice appliesTo(final IModel<?> model) {
        
        if (!(model instanceof ValueModel)) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }
        final ValueModel valueModel = (ValueModel) model;
        if(model.getObject() == null) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }
        
        final ObjectSpecification chartOptionsSpec = getSpecificationLoader().loadSpecification(WickedChart.class);
        final ObjectSpecification scalarSpec = valueModel.getObject().getSpecification();
        
        return appliesExclusivelyIf(scalarSpec.isOfType(chartOptionsSpec));
    }

    @Override
    public Component createComponent(final String id, final IModel<?> scalarModel) {
        return new StandaloneValueAsWickedChart(id, (ValueModel)scalarModel);
    }

    protected SpecificationLoader getSpecificationLoader() {
        return IsisContext.getSessionFactory().getSpecificationLoader();
    }
}
