package org.incode.example.alias.demo.examples.document.dom.applicability.aa;

import java.util.Collections;
import java.util.List;

import org.incode.example.document.dom.impl.applicability.AttachmentAdvisor;
import org.incode.example.document.dom.impl.applicability.AttachmentAdvisorAbstract;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrl;

import lombok.Value;

public class ForDemoObjectAttachToSame extends AttachmentAdvisorAbstract<DemoObjectWithUrl> {

    public ForDemoObjectAttachToSame() {
        super(DemoObjectWithUrl.class);
    }

    @Override
    protected List<PaperclipSpec> doAdvise(
            final DocumentTemplate documentTemplate,
            final DemoObjectWithUrl demoObject,
            final Document createdDocument) {
        return Collections.singletonList(new AttachmentAdvisor.PaperclipSpec(null, demoObject, createdDocument));
    }

    @Value
    public static class DataModel {
        DemoObjectWithUrl demoObject;
    }

}

