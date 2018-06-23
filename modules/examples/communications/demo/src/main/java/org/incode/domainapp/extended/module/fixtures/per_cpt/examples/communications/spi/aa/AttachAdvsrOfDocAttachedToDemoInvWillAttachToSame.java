package org.incode.example.alias.demo.examples.communications.spi.aa;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.incode.example.document.dom.impl.applicability.AttachmentAdvisorAbstract;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;
import org.incode.example.document.dom.impl.paperclips.Paperclip;
import org.incode.example.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoInvoice;

public class AttachAdvsrOfDocAttachedToDemoInvWillAttachToSame extends
        AttachmentAdvisorAbstract<Document> {

    public AttachAdvsrOfDocAttachedToDemoInvWillAttachToSame() {
        super(Document.class);
    }

    @Override
    protected List<PaperclipSpec> doAdvise(
            final DocumentTemplate documentTemplate,
            final Document document,
            final Document createdDocument) {

        final List<Paperclip> paperclips = paperclipRepository.findByDocument(document);
        final Optional<DemoInvoice> demoInvoiceIfAny =
                paperclips.stream().map(Paperclip::getAttachedTo)
                        .filter(DemoInvoice.class::isInstance)
                        .map(DemoInvoice.class::cast)
                        .findFirst();

        return demoInvoiceIfAny.isPresent()
                ? Collections.singletonList(new PaperclipSpec(null, document, createdDocument))
                : Collections.emptyList();
    }

    @Inject
    PaperclipRepository paperclipRepository;


}

