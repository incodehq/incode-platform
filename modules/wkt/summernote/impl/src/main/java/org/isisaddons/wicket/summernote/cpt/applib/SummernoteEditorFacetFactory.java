package org.isisaddons.wicket.summernote.cpt.applib;

import java.lang.reflect.Method;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facetapi.FacetUtil;
import org.apache.isis.core.metamodel.facetapi.FeatureType;
import org.apache.isis.core.metamodel.facets.Annotations;
import org.apache.isis.core.metamodel.facets.FacetFactoryAbstract;


public class SummernoteEditorFacetFactory extends FacetFactoryAbstract {

    public SummernoteEditorFacetFactory() {
        super(FeatureType.PROPERTIES_AND_ACTIONS);
    }

    @Override
    public void process(final ProcessMethodContext processMethodContext) {

        final FacetHolder holder = processMethodContext.getFacetHolder();
        final Method method = processMethodContext.getMethod();

        final SummernoteEditor summernoteEditor = Annotations.getAnnotation(method, SummernoteEditor.class);

        if (summernoteEditor != null) {
            SummernoteEditorFacetAbstract summernoteEditorFacet = SummernoteEditorFromAnnotationFacet.create(summernoteEditor, holder);
            FacetUtil.addFacet(summernoteEditorFacet);
        }
    }

    @Override
    public void processParams(ProcessParameterContext processParameterContext) {
        super.processParams(processParameterContext);
    }
}
