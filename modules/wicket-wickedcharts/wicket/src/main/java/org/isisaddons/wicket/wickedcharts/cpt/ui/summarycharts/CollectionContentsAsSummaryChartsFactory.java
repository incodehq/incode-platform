package org.isisaddons.wicket.wickedcharts.cpt.ui.summarycharts;

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.isis.applib.filter.Filter;
import org.apache.isis.core.metamodel.facets.value.bigdecimal.BigDecimalValueFacet;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;

/**
 * {@link ComponentFactory} for {@link CollectionContentsAsSummaryCharts}.
 */
public class CollectionContentsAsSummaryChartsFactory extends ComponentFactoryAbstract {

    private static final long serialVersionUID = 1L;

    private static final String NAME = "summarycharts";

    final static Filter<ObjectAssociation> OF_TYPE_BIGDECIMAL = new Filter<ObjectAssociation>(){

        public boolean accept(final ObjectAssociation objectAssoc) {
            ObjectSpecification objectSpec = objectAssoc.getSpecification();
            return objectSpec.containsDoOpFacet(BigDecimalValueFacet.class);
        }};

    public CollectionContentsAsSummaryChartsFactory() {
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
        return new CollectionContentsAsSummaryCharts(id, collectionModel);
    }
}
