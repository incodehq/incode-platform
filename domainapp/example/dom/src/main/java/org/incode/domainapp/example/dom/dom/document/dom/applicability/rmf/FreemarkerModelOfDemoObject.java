package org.incode.domainapp.example.dom.dom.document.dom.applicability.rmf;

import org.incode.module.document.dom.impl.applicability.RendererModelFactoryAbstract;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.domainapp.example.dom.dom.document.dom.demo.DemoObject;

import lombok.Value;

public class FreemarkerModelOfDemoObject extends RendererModelFactoryAbstract<DemoObject> {

    public FreemarkerModelOfDemoObject() {
        super(DemoObject.class);
    }

    @Override protected Object doNewRendererModel(
            final DocumentTemplate documentTemplate, final DemoObject demoObject) {
        return new DataModel(demoObject);
    }

    @Value
    public static class DataModel {
        DemoObject demoObject;
    }

}

