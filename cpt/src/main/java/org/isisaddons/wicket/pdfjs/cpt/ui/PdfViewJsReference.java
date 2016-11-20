package org.isisaddons.wicket.pdfjs.cpt.ui;

import java.util.List;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.wicketstuff.pdfjs.PdfJsReference;
import org.wicketstuff.pdfjs.WicketStuffPdfJsReference;

public class PdfViewJsReference extends JQueryPluginResourceReference {

    public PdfViewJsReference() {
        super(PdfViewerPanel.class, "PdfViewerPanel.js");
    }

    @Override
    public List<HeaderItem> getDependencies() {
        final List<HeaderItem> dependencies = super.getDependencies();
        dependencies.add(JavaScriptHeaderItem.forReference(WicketStuffPdfJsReference.INSTANCE));
        return dependencies;
    }
}
