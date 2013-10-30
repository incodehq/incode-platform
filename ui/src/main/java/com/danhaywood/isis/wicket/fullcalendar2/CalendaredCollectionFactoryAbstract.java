/*
 *  Copyright 2013 Dan Haywood
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
package com.danhaywood.isis.wicket.fullcalendar2;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;

public abstract class CalendaredCollectionFactoryAbstract extends ComponentFactoryAbstract {

    private static final long serialVersionUID = 1L;

    private static final String NAME = "calendar";

    private Class<?> cls;

    public CalendaredCollectionFactoryAbstract(final Class<?> cls) {
        super(ComponentType.COLLECTION_CONTENTS, NAME);
        this.cls = cls;
    }

    @Override
    public ApplicationAdvice appliesTo(final IModel<?> model) {
        if(!(model instanceof EntityCollectionModel)) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }
        final EntityCollectionModel entityCollectionModel = (EntityCollectionModel) model;
        
        final ObjectSpecification elementSpec = entityCollectionModel.getTypeOfSpecification();
        final Class<?> correspondingClass = elementSpec.getCorrespondingClass();
        
        return appliesIf(cls.isAssignableFrom(correspondingClass));
    }

    @Override
    public Component createComponent(final String id, final IModel<?> model) {
        final EntityCollectionModel collectionModel = (EntityCollectionModel) model;
        return newComponent(id, collectionModel);
    }

    protected abstract Component newComponent(final String id, final EntityCollectionModel collectionModel);
}
