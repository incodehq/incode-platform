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

package org.isisaddons.wicket.summernote.cpt.applib;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteConfig;

import org.apache.isis.core.metamodel.facetapi.FacetHolder;

public class SummernoteEditorFromAnnotationFacet extends SummernoteEditorFacetAbstract {

    public SummernoteEditorFromAnnotationFacet(final SummernoteConfig config, final FacetHolder holder) {
        super(config, holder);
    }

    public static SummernoteEditorFacetAbstract create(SummernoteEditor annotation, FacetHolder holder) {
        SummernoteConfig config = new SummernoteConfig();
        config.withAirMode(annotation.airMode());

        int height = annotation.height();
        if (height > 0) {
            config.withHeight(height);
        }

        int maxHeight = annotation.maxHeight();
        if (maxHeight > 0) {
            config.withMaxHeight(maxHeight);
        }

        int minHeight = annotation.minHeight();
        if (minHeight > 0) {
            config.withMinHeight(minHeight);
        }

        int overlayTimeout = annotation.overlayTimeout();
        if (overlayTimeout > -1) {
            config.withOverlayTimeout(overlayTimeout);
        }

        config.force(annotation.focus());

        return new SummernoteEditorFromAnnotationFacet(config, holder);
    }
}
