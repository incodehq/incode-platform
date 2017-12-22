package org.incode.example.docrendering.stringinterpolator.fixture;

import org.incode.example.document.dom.impl.docs.DocumentNature;
import org.incode.example.document.fixture.RenderingStrategyFSAbstract;

import org.incode.example.docrendering.stringinterpolator.dom.impl.RendererForStringInterpolatorPreviewAndCaptureUrl;

public class RenderingStrategyFSForStringInterpolatorPreviewAndCaptureUrl extends RenderingStrategyFSAbstract {

    public static final String REF = "SIPC";

    @Override
    protected void execute(ExecutionContext executionContext) {
        upsertRenderingStrategy(
                REF,
                "String interpolate URL for Preview and Capture",
                DocumentNature.CHARACTERS,
                DocumentNature.BYTES,
                RendererForStringInterpolatorPreviewAndCaptureUrl.class, executionContext);
    }

}
