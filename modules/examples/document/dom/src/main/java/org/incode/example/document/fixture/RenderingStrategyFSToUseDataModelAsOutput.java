package org.incode.example.document.fixture;

import org.incode.example.document.dom.impl.docs.DocumentNature;

import org.incode.example.document.dom.impl.renderers.RendererUsesDataModelAsOutput;

public class RenderingStrategyFSToUseDataModelAsOutput extends RenderingStrategyFSAbstract {

    public static final String REF = "DIRECT";

    @Override
    protected void execute(ExecutionContext executionContext) {
        upsertRenderingStrategy(
                REF,
                "Use input as output",
                DocumentNature.CHARACTERS, DocumentNature.CHARACTERS,
                RendererUsesDataModelAsOutput.class, executionContext);

    }

}
