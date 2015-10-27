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

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GEvent;
import org.wicketstuff.gmap.api.GEventHandler;
import org.wicketstuff.gmap.api.GIcon;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.model.models.EntityModel;
import org.apache.isis.viewer.wicket.model.models.ImageResourceCache;
import org.apache.isis.viewer.wicket.model.models.PageType;
import org.apache.isis.viewer.wicket.ui.pages.PageClassRegistry;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;
import org.apache.isis.viewer.wicket.ui.panels.PanelUtil;

import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.isisaddons.wicket.gmap3.cpt.applib.LocationDereferencingService;

public class CollectionOfEntitiesAsLocatables extends
        PanelAbstract<EntityCollectionModel> {

    private static final long serialVersionUID = 1L;

    private static final String INVISIBLE_CLASS = "collection-contents-as-locatables-invisible";
    private static final String ID_MAP = "map";

    @Inject
    private ImageResourceCache imageResourceCache;
    
    @Inject
    private PageClassRegistry pageClassRegistry;

    public CollectionOfEntitiesAsLocatables(final String id,
            final EntityCollectionModel model) {
        super(id, model);
        buildGui();
    }

    private void buildGui() {

        final EntityCollectionModel model = getModel();
        final List<ObjectAdapter> adapterList = model.getObject();

        final GMap map = new GMap(ID_MAP);
        map.setStreetViewControlEnabled(true);
        map.setScaleControlEnabled(true);
        map.setScrollWheelZoomEnabled(true);
        map.setPanControlEnabled(true);
        map.setDoubleClickZoomEnabled(true);

        // centre the map on the first object that has a location.
        for (ObjectAdapter adapter : adapterList) {
            GLatLng latLng = asGLatLng((Locatable)adapter.getObject());
            if(latLng != null) {
                map.setCenter(latLng);
                break;
            }
        }
        
        addOrReplace(map);
        applyCssVisibility(map, !adapterList.isEmpty());
        
        addMarkers(map, adapterList);
    }
    
    private static void applyCssVisibility(final Component component, final boolean visible) {
        final AttributeModifier modifier =  
                visible 
                    ? new AttributeModifier("class", String.valueOf(component.getMarkupAttributes().get("class")).replaceFirst(INVISIBLE_CLASS, "")) 
                    : new AttributeAppender("class", " " +
                            INVISIBLE_CLASS);
        component.add(modifier);
    }

    private void addMarkers(final GMap map, List<ObjectAdapter> adapterList) {

        List<GLatLng> glatLngsToShow = Lists.newArrayList();
        for (ObjectAdapter adapter : adapterList) {

            ObjectAdapter dereferencedAdapter = dereference(adapter);

            final GMarker gMarker = createGMarker(map, adapter, dereferencedAdapter);
            if(gMarker != null) {
                map.addOverlay(gMarker);
                addClickListener(gMarker, dereferencedAdapter);
                glatLngsToShow.add(gMarker.getLatLng());
            }
        }

        map.fitMarkers(glatLngsToShow);
    }

    private ObjectAdapter dereference(final ObjectAdapter adapterForLocatable) {
        final LocationDereferencingService locationDereferencingService =
                IsisContext.getSessionFactory().getServicesInjector().lookupService(LocationDereferencingService.class);
        if(locationDereferencingService == null) {
            return adapterForLocatable;
        }
        final Object dereferencedObject = locationDereferencingService.dereference(adapterForLocatable.getObject());
        return IsisContext.getPersistenceSession().adapterFor(dereferencedObject);
    }

    private GMarker createGMarker(GMap map, ObjectAdapter adapter, final ObjectAdapter dereferencedAdapter) {
        GMarkerOptions markerOptions = buildMarkerOptions(map, adapter, dereferencedAdapter);
        if(markerOptions == null)
            return null;
        return new GMarker(markerOptions);
    }

    private GMarkerOptions buildMarkerOptions(GMap map, ObjectAdapter adapter, final ObjectAdapter dereferencedAdapter) {
        
        final Locatable locatable = (Locatable) adapter.getObject();
        
        final ResourceReference imageResource = determineImageResource(adapter);
        final String urlFor = (String)urlFor(imageResource, new PageParameters());
        @SuppressWarnings("unused")
        final GIcon gicon = new GIcon(urlFor);
        
        GLatLng gLatLng = asGLatLng(locatable);
        if(gLatLng == null) {
            return null;
        }
        final GMarkerOptions markerOptions = new GMarkerOptions(
                map, gLatLng, 
                dereferencedAdapter.titleString(null)   ).draggable(false);
        return markerOptions;
    }

    private GLatLng asGLatLng(Locatable locatable) {
        final Location location = locatable.getLocation();
        return location!=null?new GLatLng(location.getLatitude(), location.getLongitude()):null;
    }

    private ResourceReference determineImageResource(ObjectAdapter adapter) {
        ResourceReference imageResource = null;
        if (adapter != null) {
            imageResource = getImageCache().resourceReferenceFor(adapter);
        }
        return imageResource;
    }

    private void addClickListener(final GMarker gMarker, ObjectAdapter adapter) {
        final Class<? extends Page> pageClass = getPageClassRegistry()
                .getPageClass(PageType.ENTITY);
        final PageParameters pageParameters = EntityModel.createPageParameters(
                adapter);

        gMarker.addListener(GEvent.click, new GEventHandler() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onEvent(AjaxRequestTarget target) {
                setResponsePage(pageClass, pageParameters);
            }
        });
    }

    @Override
    protected void onModelChanged() {
        buildGui();
    }


    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        PanelUtil.renderHead(response, CollectionOfEntitiesAsLocatables.class);
    }


    //////////////////////////////////////////////
    // Dependency Injection
    //////////////////////////////////////////////

    protected ImageResourceCache getImageCache() {
        return imageResourceCache;
    }

    protected PageClassRegistry getPageClassRegistry() {
        return pageClassRegistry;
    }

}
