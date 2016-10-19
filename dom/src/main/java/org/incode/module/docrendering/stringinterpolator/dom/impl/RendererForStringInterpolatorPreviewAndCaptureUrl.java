/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
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
package org.incode.module.docrendering.stringinterpolator.dom.impl;

import java.io.IOException;
import java.net.URL;

import org.incode.module.document.dom.impl.renderers.RendererFromCharsToBytesWithPreviewToUrl;
import org.incode.module.document.dom.impl.types.DocumentType;

public class RendererForStringInterpolatorPreviewAndCaptureUrl extends RendererForStringInterpolatorCaptureUrl
        implements RendererFromCharsToBytesWithPreviewToUrl {

    @Override
    public URL previewCharsToBytes(
            final DocumentType documentType,
            final String atPath,
            final long templateVersion,
            final String templateChars,
            final Object dataModel) throws IOException {
        return super.previewCharsToBytes(
                documentType, atPath, templateVersion, templateChars, dataModel);
    }


}
