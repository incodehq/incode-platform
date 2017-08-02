package org.isisaddons.wicket.pdfjs.cpt.applib;

import org.wicketstuff.pdfjs.PdfJsConfig;

import org.apache.isis.core.metamodel.facetapi.Facet;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.MultipleValueFacetAbstract;

public abstract class PdfJsViewerFacetAbstract extends MultipleValueFacetAbstract implements PdfJsViewerFacet {

    private final PdfJsConfig config;

    public PdfJsViewerFacetAbstract(PdfJsConfig config, final FacetHolder holder) {
        super(type(), holder);

        this.config = config;
    }

    public PdfJsConfig configFor(final PdfJsViewerAdvisor.InstanceKey instanceKey) {
        return config;
    }

    public static Class<? extends Facet> type() {
        return PdfJsViewerFacet.class;
    }

}
