package org.isisaddons.wicket.gmap3.cpt.ui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.specloader.SpecificationLoader;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.CollectionContentsAsFactory;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;

import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibConstants;
import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;

public class CollectionOfEntitiesAsLocatablesFactory extends ComponentFactoryAbstract implements CollectionContentsAsFactory {

    private static final long serialVersionUID = 1L;

    private static final String ID_MAP = "map";

    private boolean determinedWhetherInternetReachable;
    private boolean internetReachable;

    public CollectionOfEntitiesAsLocatablesFactory() {
        super(ComponentType.COLLECTION_CONTENTS, ID_MAP);
    }

    @Override
    public ApplicationAdvice appliesTo(IModel<?> model) {

        final String apiKey = getConfiguration().getString(Gmap3ApplibConstants.API_KEY);
        if(apiKey == null) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }

        if(!internetReachable()) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }
        
        if (!(model instanceof EntityCollectionModel)) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }

        EntityCollectionModel entityCollectionModel = (EntityCollectionModel) model;

        ObjectSpecification typeOfSpec = entityCollectionModel.getTypeOfSpecification();
        ObjectSpecification locatableSpec = getSpecificationLoader().loadSpecification(Locatable.class);
        return appliesIf(typeOfSpec.isOfType(locatableSpec));
    }

    private boolean internetReachable() {
        if(!determinedWhetherInternetReachable) {
            internetReachable = isInternetReachable();
            determinedWhetherInternetReachable = true;
        }
        return internetReachable;
    }
    
    /**
     * Tries to retrieve some content, 1 second timeout.
     */
    private static boolean isInternetReachable()
    {
        try {
            final URL url = new URL("http://www.google.com");
            final HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            urlConnect.setConnectTimeout(1000);
            urlConnect.getContent();
            urlConnect.disconnect();
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public Component createComponent(String id, IModel<?> model) {

        final String apiKey = getConfiguration().getString(Gmap3ApplibConstants.API_KEY);

        EntityCollectionModel collectionModel = (EntityCollectionModel) model;
        return new CollectionOfEntitiesAsLocatables(id, apiKey, collectionModel);
    }

    protected SpecificationLoader getSpecificationLoader() {
        return IsisContext.getSessionFactory().getSpecificationLoader();
    }

    protected IsisConfiguration getConfiguration() {
        return IsisContext.getSessionFactory().getConfiguration();
    }

    @Override
    public IModel<String> getTitleLabel() {
        return Model.of("Map");
    }

    @Override
    public IModel<String> getCssClass() {
        return Model.of("fa fa-map-marker");
    }

}
