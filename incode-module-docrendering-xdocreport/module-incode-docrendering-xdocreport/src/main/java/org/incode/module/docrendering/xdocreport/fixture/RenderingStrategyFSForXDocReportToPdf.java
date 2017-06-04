/*
 *  Copyright 2016 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.docrendering.xdocreport.fixture;

import org.incode.module.docrendering.xdocreport.dom.impl.RendererForXDocReportToPdf;
import org.incode.module.document.dom.impl.docs.DocumentNature;
import org.incode.module.document.fixture.RenderingStrategyFSAbstract;

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
