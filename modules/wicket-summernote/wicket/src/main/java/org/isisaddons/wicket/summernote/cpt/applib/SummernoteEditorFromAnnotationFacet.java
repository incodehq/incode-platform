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
