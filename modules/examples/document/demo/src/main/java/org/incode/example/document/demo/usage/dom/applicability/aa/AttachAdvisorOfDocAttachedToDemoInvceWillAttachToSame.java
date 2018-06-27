package org.incode.example.document.demo.usage.dom.applicability.aa;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.incode.example.document.demo.shared.demowithnotes.dom.DocInvoice;
import org.incode.example.document.dom.impl.applicability.AttachmentAdvisorAbstract;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;
import org.incode.example.document.dom.impl.paperclips.Paperclip;
import org.incode.example.document.dom.impl.paperclips.PaperclipRepository;

public class AttachAdvisorOfDocAttachedToDemoInvceWillAttachToSame extends
        AttachmentAdvisorAbstract<Document> {

    public AttachAdvisorOfDocAttachedToDemoInvceWillAttachToSame() {
        super(Document.class);
    }

    @Override
    protected List<PaperclipSpec> doAdvise(
            final DocumentTemplate documentTemplate,
            final Document document,
            final Document createdDocument) {

        final List<Paperclip> paperclips = paperclipRepository.findByDocument(document);
        final Optional<DocInvoice> demoInvoiceIfAny =
                paperclips.stream().map(Paperclip::getAttachedTo)
                        .filter(DocInvoice.class::isInstance)
                        .map(DocInvoice.class::cast)
                        .findFirst();

        return demoInvoiceIfAny.isPresent()
                ? Collections.singletonList(new PaperclipSpec(null, document, createdDocument))
                : Collections.emptyList();
    }

    @Inject
    PaperclipRepository paperclipRepository;


}

