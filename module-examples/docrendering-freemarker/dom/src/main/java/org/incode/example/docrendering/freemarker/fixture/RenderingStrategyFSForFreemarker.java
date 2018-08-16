package org.incode.example.docrendering.freemarker.fixture;

import org.incode.example.document.dom.impl.docs.DocumentNature;
import org.incode.example.document.fixture.RenderingStrategyFSAbstract;

import org.incode.example.docrendering.freemarker.dom.impl.RendererForFreemarker;

public class RenderingStrategyFSForFreemarker extends RenderingStrategyFSAbstract {

    public static final String REF = "FMK";

    @Override
    protected void execute(ExecutionContext executionContext) {
        upsertRenderingStrategy(
                REF,
                "RendererForFreemarker Rendering Strategy",
                DocumentNature.CHARACTERS, DocumentNature.CHARACTERS,
                RendererForFreemarker.class, executionContext);

    }


}
