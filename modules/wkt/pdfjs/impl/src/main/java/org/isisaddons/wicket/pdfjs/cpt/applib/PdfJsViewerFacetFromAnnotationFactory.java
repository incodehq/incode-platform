package org.isisaddons.wicket.pdfjs.cpt.applib;

import java.lang.reflect.Method;

import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facetapi.FacetUtil;
import org.apache.isis.core.metamodel.facetapi.FeatureType;
import org.apache.isis.core.metamodel.facets.Annotations;
import org.apache.isis.core.metamodel.facets.FacetFactoryAbstract;

public class PdfJsViewerFacetFromAnnotationFactory extends FacetFactoryAbstract {

    public PdfJsViewerFacetFromAnnotationFactory() {
        super(FeatureType.PROPERTIES_AND_ACTIONS);
    }

    @Override
    public void process(final ProcessMethodContext processMethodContext) {

        final FacetHolder holder = processMethodContext.getFacetHolder();
        final Method method = processMethodContext.getMethod();

        final PdfJsViewer pdfjsViewer = Annotations.getAnnotation(method, PdfJsViewer.class);

        if (pdfjsViewer != null) {
            PdfJsViewerFacetAbstract pdfJsViewerFacet = PdfJsViewerFacetFromAnnotation.create(pdfjsViewer, holder);
            servicesInjector.injectServicesInto(pdfJsViewerFacet);
            FacetUtil.addFacet(pdfJsViewerFacet);
        }
    }

    @Override
    public void processParams(ProcessParameterContext processParameterContext) {
        super.processParams(processParameterContext);
    }
}
