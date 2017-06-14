package org.isisaddons.wicket.wickedcharts.cpt.ui.summarychart;

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.isis.applib.filter.Filter;
import org.apache.isis.core.metamodel.facets.value.bigdecimal.BigDecimalValueFacet;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.CollectionContentsAsFactory;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;

/**
 * {@link ComponentFactory} for {@link CollectionContentsAsSummaryChart}.
 */
public class CollectionContentsAsSummaryChartFactory extends ComponentFactoryAbstract implements CollectionContentsAsFactory {

    private static final long serialVersionUID = 1L;

    private static final String NAME = "summarychart";

    final static Filter<ObjectAssociation> OF_TYPE_BIGDECIMAL = new Filter<ObjectAssociation>(){

        public boolean accept(final ObjectAssociation objectAssoc) {
            ObjectSpecification objectSpec = objectAssoc.getSpecification();
            return objectSpec.containsDoOpFacet(BigDecimalValueFacet.class);
        }};

    public CollectionContentsAsSummaryChartFactory() {
        super(ComponentType.COLLECTION_CONTENTS, NAME);
    }

    @Override
    public ApplicationAdvice appliesTo(final IModel<?> model) {
        if(!(model instanceof EntityCollectionModel)) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }
        final EntityCollectionModel entityCollectionModel = (EntityCollectionModel) model;
        // TOFIX: because of Javascript issues, currently only works for standalone views.
        if(!entityCollectionModel.isStandalone()) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }
        final ObjectSpecification elementSpec = entityCollectionModel.getTypeOfSpecification();
        List<ObjectAssociation> associations = elementSpec.getAssociations(OF_TYPE_BIGDECIMAL);
        return appliesIf(!associations.isEmpty());
    }

    @Override
    public Component createComponent(final String id, final IModel<?> model) {
        final EntityCollectionModel collectionModel = (EntityCollectionModel) model;
        return new CollectionContentsAsSummaryChart(id, collectionModel);
    }

    @Override
    public IModel<String> getTitleLabel() {
        return Model.of("Summary Chart");
    }

    @Override
    public IModel<String> getCssClass() {
        return Model.of("fa fa-bar-chart");
    }
}
