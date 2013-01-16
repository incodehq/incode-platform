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


package org.apache.isis.extensions.wicket.view.gmap2.collection.locatable;

import java.util.List;

import org.apache.isis.extensions.wicket.model.models.EntityCollectionModel;
import org.apache.isis.extensions.wicket.model.models.EntityModel;
import org.apache.isis.extensions.wicket.ui.app.imagecache.ImageCache;
import org.apache.isis.extensions.wicket.ui.pages.PageClassRegistry;
import org.apache.isis.extensions.wicket.ui.pages.PageType;
import org.apache.isis.extensions.wicket.ui.panels.PanelAbstract;
import org.apache.isis.extensions.wicket.view.gmap2.applib.Locatable;
import org.apache.isis.extensions.wicket.view.gmap2.applib.Location;
import org.apache.isis.metamodel.adapter.ObjectAdapter;
import org.apache.isis.metamodel.facets.object.ident.icon.IconFacet;
import org.apache.isis.metamodel.spec.ObjectSpecification;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.protocol.http.WebApplication;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GEvent;
import wicket.contrib.gmap.api.GEventHandler;
import wicket.contrib.gmap.api.GIcon;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class CollectionOfEntitiesAsLocatables extends
        PanelAbstract<EntityCollectionModel> {

    private static final long serialVersionUID = 1L;

    private static final String GOOGLE_MAPS_API_KEY = "GOOGLE_MAPS_API_KEY";
    private static final String ID_MAP = "map";

    /**
     * Injected in {@link #setImageCache(ImageCache)}
     */
    private ImageCache imageCache;
    
    /**
     * Injected in {@link #setPageClassRegistry(PageClassRegistry)}
     */
    private PageClassRegistry pageClassRegistry;

    public CollectionOfEntitiesAsLocatables(final String id,
            final EntityCollectionModel model) {
        super(id, model);
        buildGui();
    }

    private void buildGui() {
        final GMap2 map = new GMap2(ID_MAP, getGoogleMapsAPIkey());

        final GControl mapControl = getMapControl();
        if (mapControl != null) {
            map.addControl(mapControl);
        }

        addMarkers(map);
        add(map);
    }

    /**
     * Protected visibility so can be overridden if required.
     */
    protected GControl getMapControl() {
        return GControl.GLargeMapControl;
    }

    private void addMarkers(final GMap2 map) {

        EntityCollectionModel model = getModel();
        List<ObjectAdapter> adapterList = model.getObject();

        List<GLatLng> glatLngsToShow = Lists.newArrayList();
        for (ObjectAdapter adapter : adapterList) {

            final GMarker gMarker = createGMarker(adapter);

            addClickListener(gMarker, adapter);

            map.addOverlay(gMarker);
            glatLngsToShow.add(gMarker.getLatLng());
        }

        map.fitMarkers(glatLngsToShow);
    }

    private GMarker createGMarker(ObjectAdapter adapter) {
        GMarkerOptions markerOptions = buildMarkerOptions(adapter);
        final Locatable locatable = (Locatable) adapter.getObject();
        return new GMarker(asGLatLng(locatable), markerOptions);
    }

    private GMarkerOptions buildMarkerOptions(ObjectAdapter adapter) {
        final PackageResource imageResource = determineImageResource(adapter);
        final ResourceReference resourceReference = new ResourceReference(
                adapter.titleString()) {
            private static final long serialVersionUID = 1L;

            protected org.apache.wicket.Resource newResource() {
                return imageResource;
            }
        };
        final String urlFor = (String) urlFor(resourceReference);

        GMarkerOptions notDraggable = new GMarkerOptions(adapter.titleString(),
                new GIcon(urlFor)).draggable(false);
        return notDraggable;
    }

    private GLatLng asGLatLng(Locatable locatable) {
        final Location location = locatable.getLocation();
        return new GLatLng(location.getLatitude(), location.getLongitude());
    }

    private PackageResource determineImageResource(ObjectAdapter adapter) {
        ObjectSpecification typeOfSpec;
        PackageResource imageResource = null;
        if (adapter != null) {
            typeOfSpec = adapter.getSpecification();
            IconFacet iconFacet = typeOfSpec.getFacet(IconFacet.class);
            if (iconFacet != null) {
                String iconName = iconFacet.iconName(adapter);
                imageResource = getImageCache().findImage(iconName);
            }
        }
        if (imageResource == null) {
            typeOfSpec = getModel().getTypeOfSpecification();
            imageResource = getImageCache().findImage(typeOfSpec);
        }
        return imageResource;
    }

    private void addClickListener(final GMarker gMarker, ObjectAdapter adapter) {
        final Class<? extends Page> pageClass = getPageClassRegistry()
                .getPageClass(PageType.ENTITY);
        final PageParameters pageParameters = EntityModel.createPageParameters(
                adapter, getOidStringifier());
        gMarker.addListener(GEvent.click, new GEventHandler() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onEvent(AjaxRequestTarget target) {
                setResponsePage(pageClass, pageParameters);
            }
        });
    }

    /**
     * Overridable so can obtain API from another source if required.
     */
    protected String getGoogleMapsAPIkey() {
        final WebApplication webApplication = (WebApplication) getApplication();
        return webApplication.getInitParameter(GOOGLE_MAPS_API_KEY);
    }

    
    //////////////////////////////////////////////
    // Dependency Injection
    //////////////////////////////////////////////

    protected ImageCache getImageCache() {
        return imageCache;
    }
    @Inject
    public void setImageCache(ImageCache imageCache) {
        this.imageCache = imageCache;
    }

    protected PageClassRegistry getPageClassRegistry() {
        return pageClassRegistry;
    }
    @Inject
    public void setPageClassRegistry(PageClassRegistry pageClassRegistry) {
        this.pageClassRegistry = pageClassRegistry;
    }


}
