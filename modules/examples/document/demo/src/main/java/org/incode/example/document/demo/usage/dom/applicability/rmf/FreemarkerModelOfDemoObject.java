package org.incode.example.document.demo.usage.dom.applicability.rmf;

import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrl;
import org.incode.example.document.dom.impl.applicability.RendererModelFactoryAbstract;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;

import lombok.Value;

public class FreemarkerModelOfDemoObject extends RendererModelFactoryAbstract<DocDemoObjectWithUrl> {

    public FreemarkerModelOfDemoObject() {
        super(DocDemoObjectWithUrl.class);
    }

    @Override protected Object doNewRendererModel(
            final DocumentTemplate documentTemplate, final DocDemoObjectWithUrl demoObject) {
        return new DataModel(demoObject);
    }

    @Value
    public static class DataModel {
        DocDemoObjectWithUrl demoObject;
    }

}

