package org.isisaddons.wicket.excel.cpt.ui;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.CollectionContentsAsFactory;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;

/**
 * {@link ComponentFactory} for {@link CollectionContentsAsExcel}.
 */
public class CollectionContentsAsExcelFactory extends ComponentFactoryAbstract implements CollectionContentsAsFactory {

    private static final long serialVersionUID = 1L;

    private static final String NAME = "excel";

    public CollectionContentsAsExcelFactory() {
        super(ComponentType.COLLECTION_CONTENTS, NAME);
    }

    @Override
    public ApplicationAdvice appliesTo(final IModel<?> model) {
        if(!(model instanceof EntityCollectionModel)) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }
        return ApplicationAdvice.APPLIES;
    }

    @Override
    public Component createComponent(final String id, final IModel<?> model) {
        final EntityCollectionModel collectionModel = (EntityCollectionModel) model;
        return new CollectionContentsAsExcel(id, collectionModel);
    }

    @Override
    public IModel<String> getTitleLabel() {
        return Model.of("Excel");
    }

    @Override
    public IModel<String> getCssClass() {
        return Model.of("fa fa-file-excel-o");
    }
}
