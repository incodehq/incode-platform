package org.incode.module.document.fixture.app.binders;

import java.util.Collections;

import org.incode.module.document.dom.impl.applicability.Binder;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.fixture.dom.demo.DemoObject;

import lombok.Value;

public class BinderOfDataModelForDemoObject implements Binder {

    @Override
    public Binding newBinding(
            final DocumentTemplate documentTemplate,
            final Object domainObject) {

        if(!(domainObject instanceof DemoObject)) {
            throw new IllegalArgumentException("Domain object must be of type DemoObject");
        }
        DemoObject demoObject = (DemoObject) domainObject;

        // dataModel
        final DataModel dataModel = new DataModel(demoObject);

        return new Binding(dataModel, Collections.singletonList(new Binding.PaperclipSpec(null, demoObject)));
    }

    @Value
    public static class DataModel {
        DemoObject demoObject;
    }

}

