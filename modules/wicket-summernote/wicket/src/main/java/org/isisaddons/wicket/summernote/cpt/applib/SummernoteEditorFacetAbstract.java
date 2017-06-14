package org.isisaddons.wicket.summernote.cpt.applib;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteConfig;

import org.apache.isis.core.metamodel.facetapi.Facet;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.MultipleValueFacet;
import org.apache.isis.core.metamodel.facets.MultipleValueFacetAbstract;

public abstract class SummernoteEditorFacetAbstract extends MultipleValueFacetAbstract implements MultipleValueFacet {

    private final SummernoteConfig config;

    public SummernoteEditorFacetAbstract(SummernoteConfig config, final FacetHolder holder) {
        super(type(), holder);

        this.config = config;
    }

    public SummernoteConfig getConfig() {
        return config;
    }

    public static Class<? extends Facet> type() {
        return SummernoteEditorFacetAbstract.class;
    }

}
