package org.incode.module.document.fixture.app.applicability.aa;

import java.util.Collections;
import java.util.List;

import org.incode.module.document.dom.impl.applicability.AttachmentAdvisor;
import org.incode.module.document.dom.impl.applicability.AttachmentAdvisorAbstract;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.fixture.dom.demo.DemoObject;

import lombok.Value;

public class ForDemoObjectAttachToSame extends AttachmentAdvisorAbstract<DemoObject> {

    public ForDemoObjectAttachToSame() {
        super(DemoObject.class);
    }

    @Override
    protected List<PaperclipSpec> doAdvise(
            final DocumentTemplate documentTemplate,
            final DemoObject demoObject) {
        return Collections.singletonList(new AttachmentAdvisor.PaperclipSpec(null, demoObject));
    }

    @Value
    public static class DataModel {
        DemoObject demoObject;
    }

}

