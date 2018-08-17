package org.isisaddons.wicket.pdfjs.cpt.applib;

import org.wicketstuff.pdfjs.PdfJsConfig;

import org.apache.isis.core.metamodel.facets.MultipleValueFacet;

public interface PdfJsViewerFacet extends MultipleValueFacet {

    PdfJsConfig configFor(final PdfJsViewerAdvisor.InstanceKey instanceKey);

}
