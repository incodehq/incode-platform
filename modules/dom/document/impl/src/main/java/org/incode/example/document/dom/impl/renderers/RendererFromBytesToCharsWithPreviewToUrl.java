package org.incode.example.document.dom.impl.renderers;

import java.io.IOException;
import java.net.URL;

import org.incode.example.document.dom.impl.types.DocumentType;

public interface RendererFromBytesToCharsWithPreviewToUrl extends RendererFromBytesToChars, PreviewToUrl {

    URL previewBytesToChars(
            final DocumentType documentType,
            final String atPath,
            final long templateVersion,
            final byte[] templateBytes,
            final Object dataModel) throws IOException;

}
