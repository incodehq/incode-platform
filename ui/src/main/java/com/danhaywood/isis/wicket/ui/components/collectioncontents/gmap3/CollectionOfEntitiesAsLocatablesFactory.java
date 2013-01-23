/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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


package com.danhaywood.isis.wicket.ui.components.collectioncontents.gmap3;

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.SpecificationLoader;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.HeaderContributorProvider;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.isis.viewer.wicket.ui.panels.PanelUtil;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.GMapHeaderContributor;

import com.danhaywood.isis.wicket.gmap3.applib.Locatable;

public class CollectionOfEntitiesAsLocatablesFactory extends ComponentFactoryAbstract /*implements HeaderContributorProvider */{

	private static final long serialVersionUID = 1L;

	private static final String ID_MAP = "map";

	public CollectionOfEntitiesAsLocatablesFactory() {
		super(ComponentType.COLLECTION_CONTENTS, ID_MAP);
	}

	@Override
	public ApplicationAdvice appliesTo(IModel<?> model) {
		if (!(model instanceof EntityCollectionModel)) {
			return ApplicationAdvice.DOES_NOT_APPLY;
		}
		
		EntityCollectionModel entityCollectionModel = (EntityCollectionModel) model;
		if (entityCollectionModel.hasSelectionHandler()) {
			return ApplicationAdvice.DOES_NOT_APPLY;
		}
		
		ObjectSpecification typeOfSpec = entityCollectionModel.getTypeOfSpecification();
		ObjectSpecification locatableSpec =  getSpecificationLoader().loadSpecification(Locatable.class);
		return appliesIf(typeOfSpec.isOfType(locatableSpec));
	}


    public Component createComponent(String id, IModel<?> model) {
		EntityCollectionModel collectionModel = (EntityCollectionModel) model;
		return new CollectionOfEntitiesAsLocatables(id, collectionModel);
	}

    protected SpecificationLoader getSpecificationLoader() {
        return IsisContext.getSpecificationLoader();
    }

//    public IHeaderContributor getHeaderContributor() {
//        return new IHeaderContributor() {
//            private static final long serialVersionUID = 1L;
//            public void renderHead(IHeaderResponse response) {
//                // the Javascript required by gmap3
//                new GMapHeaderContributor().renderHead(null, response);
//                
//                // the GMap sets up a callback to an 'initialize' function.
//                new GMap("dummy").renderHead(response);
//                
//                // the CSS normally coming from the PanelAbstract
//                PanelUtil.renderHead(response, getClass());
//            }
//        };
//    }
}
