package org.isisaddons.metamodel.paraname8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.apache.isis.core.commons.lang.StringExtensions;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facetapi.FacetUtil;
import org.apache.isis.core.metamodel.facetapi.FeatureType;
import org.apache.isis.core.metamodel.facets.FacetFactoryAbstract;
import org.apache.isis.core.metamodel.facets.FacetedMethodParameter;
import org.apache.isis.core.metamodel.facets.all.named.NamedFacet;

public class NamedFacetOnParameterParaname8Factory extends FacetFactoryAbstract {
    public NamedFacetOnParameterParaname8Factory() {
        super(FeatureType.PARAMETERS_ONLY);
    }

    @Override
    public void processParams(final ProcessParameterContext processParameterContext) {
        final Method method = processParameterContext.getMethod();
        final int paramNum = processParameterContext.getParamNum();

        final Parameter[] parameters = method.getParameters();
        final Parameter parameter = parameters[paramNum];
        final String parameterName = parameter.getName();

        String naturalName = StringExtensions.asNaturalName2(parameterName);
        final FacetedMethodParameter facetHolder = processParameterContext.getFacetHolder();
        final NamedFacet facet = facetHolder.getFacet(NamedFacet.class);
        if(!facet.isNoop()) {
            return;
        }
        FacetUtil.addFacet(create(naturalName, facetHolder));
    }

    private NamedFacet create(final String parameterName, final FacetHolder holder) {
        return new NamedFacetOnParameterParaname8(parameterName, holder);
    }

}
