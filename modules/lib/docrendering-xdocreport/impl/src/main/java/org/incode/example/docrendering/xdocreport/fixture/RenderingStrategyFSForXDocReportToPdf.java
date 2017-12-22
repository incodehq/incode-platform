package org.incode.example.docrendering.xdocreport.fixture;

import org.incode.example.docrendering.xdocreport.dom.impl.RendererForXDocReportToPdf;
import org.incode.example.document.dom.impl.docs.DocumentNature;
import org.incode.example.document.fixture.RenderingStrategyFSAbstract;

public class RenderingStrategyFSForXDocReportToPdf extends RenderingStrategyFSAbstract {

    public static final String REF = "XDP";

    @Override
    protected void execute(ExecutionContext executionContext) {
        upsertRenderingStrategy(
                REF,
                "XDocReport to .pdf",
                DocumentNature.BYTES,
                DocumentNature.BYTES,
                RendererForXDocReportToPdf.class, executionContext);
    }

}
