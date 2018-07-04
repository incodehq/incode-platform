package org.incode.example.document.demo.usage.dom.applicability.rmf;

import org.isisaddons.module.stringinterpolator.dom.StringInterpolatorService;

import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrl;
import org.incode.example.document.dom.impl.applicability.RendererModelFactoryAbstract;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;

import lombok.Getter;

public class StringInterpolatorRootOfDemoObject extends RendererModelFactoryAbstract<DocDemoObjectWithUrl> {

    public StringInterpolatorRootOfDemoObject() {
        super(DocDemoObjectWithUrl.class);
    }

    @Override
    protected Object doNewRendererModel(
            final DocumentTemplate documentTemplate, final DocDemoObjectWithUrl demoObject) {
        return new DataModel(demoObject);
    }

    public static class DataModel extends StringInterpolatorService.Root {
        @Getter
        private final DocDemoObjectWithUrl demoObject;

        public DataModel(final DocDemoObjectWithUrl demoObject) {
            super(demoObject);
            this.demoObject = demoObject;
        }
    }
}
