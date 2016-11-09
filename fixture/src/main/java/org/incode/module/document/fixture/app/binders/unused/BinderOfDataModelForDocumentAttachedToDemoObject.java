package org.incode.module.document.fixture.app.binders.unused;

import java.util.Collections;

import javax.inject.Inject;

import org.incode.module.document.dom.impl.applicability.Binder;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.fixture.dom.demo.DemoObject;

import lombok.Data;

public class BinderOfDataModelForDocumentAttachedToDemoObject implements Binder {

    @Override
    public Binding newBinding(
            final DocumentTemplate documentTemplate,
            final Object domainObject,
            final String additionalTextIfAny) {

        if(!(domainObject instanceof Document)) {
            throw new IllegalArgumentException("Domain object must be of type Document");
        }
        Document document = (Document) domainObject;

        final DemoObject demoObject = paperclipRepository.paperclipAttaches(document, DemoObject.class);
        if(demoObject == null) {
            throw new IllegalArgumentException("Document must be attached to a DemoObject");
        }

        final DataModel dataModel = new DataModel();
        dataModel.setDemoObject(demoObject);

        return new Binding(dataModel, Collections.singletonList(document));
    }

    @Data
    public static class DataModel {
        DemoObject demoObject;
    }

    @Inject
    PaperclipRepository paperclipRepository;
}

