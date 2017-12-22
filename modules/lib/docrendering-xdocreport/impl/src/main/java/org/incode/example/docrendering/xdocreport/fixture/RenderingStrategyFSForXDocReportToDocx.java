package org.incode.example.docrendering.xdocreport.fixture;

import org.incode.example.docrendering.xdocreport.dom.impl.RendererForXDocReportToDocx;
import org.incode.example.document.dom.impl.docs.DocumentNature;
import org.incode.example.document.fixture.RenderingStrategyFSAbstract;

public class RenderingStrategyFSForXDocReportToDocx extends RenderingStrategyFSAbstract {

    public static final String REF = "XDD";

    @Override
    protected void execute(ExecutionContext executionContext) {
        upsertRenderingStrategy(
                REF,
                "XDocReport to .docx",
                DocumentNature.BYTES,
                DocumentNature.BYTES,
                RendererForXDocReportToDocx.class, executionContext);
    }

}
