package org.incode.example.docrendering.stringinterpolator.fixture;

import org.incode.example.document.dom.impl.docs.DocumentNature;
import org.incode.example.document.fixture.RenderingStrategyFSAbstract;

import org.incode.example.docrendering.stringinterpolator.dom.impl.RendererForStringInterpolator;

public class RenderingStrategyFSForStringInterpolator extends RenderingStrategyFSAbstract {

    public static final String REF = "SI";

    @Override
    protected void execute(ExecutionContext executionContext) {
        upsertRenderingStrategy(
                REF,
                "String interpolate",
                DocumentNature.CHARACTERS,
                DocumentNature.CHARACTERS,
                RendererForStringInterpolator.class, executionContext);
    }

}
