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
package org.isisaddons.wicket.gmap3.cpt.ui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.specloader.SpecificationLoader;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.CollectionContentsAsFactory;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import org.apache.wicket.model.Model;
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
        EntityCollectionModel collectionModel = (EntityCollectionModel) model;
        return new CollectionOfEntitiesAsLocatables(id, collectionModel);
    }

    protected SpecificationLoader getSpecificationLoader() {
        return IsisContext.getSessionFactory().getSpecificationLoader();
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
