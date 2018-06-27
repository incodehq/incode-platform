package org.incode.example.document.demo.usage.dom.applicability.aa;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrl;
import org.incode.example.document.demo.shared.other.dom.DocOtherObject;
import org.incode.example.document.dom.impl.applicability.AttachmentAdvisor;
import org.incode.example.document.dom.impl.applicability.AttachmentAdvisorAbstract;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;

public class ForDemoObjectAlsoAttachToFirstOtherObject extends
        AttachmentAdvisorAbstract<DocDemoObjectWithUrl> {

    public ForDemoObjectAlsoAttachToFirstOtherObject() {
        super(DocDemoObjectWithUrl.class);
    }

    @Override
    protected List<PaperclipSpec> doAdvise(
            final DocumentTemplate documentTemplate,
            final DocDemoObjectWithUrl demoObject,
            final Document createdDocument) {
        return determinePaperclipSpecs(demoObject, createdDocument);
    }

    protected List<PaperclipSpec> determinePaperclipSpecs(
            final Object domainObject,
            final Document createdDocument) {

        final List<PaperclipSpec> attachTo = Lists.newArrayList();

        attachTo.add(new AttachmentAdvisor.PaperclipSpec(null, domainObject, createdDocument));

        // also attaches to first 'otherObject' (if any)
        final Optional<DocOtherObject> firstOtherObjectIfAny = repositoryService.allInstances(DocOtherObject.class).stream().findFirst();
        if(firstOtherObjectIfAny.isPresent()) {
            attachTo.add(new AttachmentAdvisor.PaperclipSpec("other", firstOtherObjectIfAny.get(), createdDocument));
        }

        return attachTo;
    }


    @Inject
    RepositoryService repositoryService;

}
