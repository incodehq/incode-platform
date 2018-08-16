package org.incode.example.communications.demo.usage.spi.rmf;

import java.util.List;

import javax.inject.Inject;

import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsInvoice;
import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsCustomer;
import org.incode.example.document.dom.impl.applicability.RendererModelFactoryAbstract;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;
import org.incode.example.document.dom.impl.paperclips.Paperclip;
import org.incode.example.document.dom.impl.paperclips.PaperclipRepository;

import lombok.Value;

public class RenderModelFactoryOfDocumentAttachedToCommsInvoice extends RendererModelFactoryAbstract<Document> {

    public RenderModelFactoryOfDocumentAttachedToCommsInvoice() {
        super(Document.class);
    }

    @Override
    protected Object doNewRendererModel(
            final DocumentTemplate documentTemplate, final Document document) {

        final List<Paperclip> paperclips = paperclipRepository.findByDocument(document);
        final CommsInvoice commsInvoice =
                paperclips.stream().map(Paperclip::getAttachedTo)
                .filter(CommsInvoice.class::isInstance)
                .map(CommsInvoice.class::cast)
                .findFirst()
                .get(); // is safe to do this, because attachment advisor will have already run

        return new DataModel(commsInvoice.getCustomer(), commsInvoice);
    }


    @Inject
    PaperclipRepository paperclipRepository;

    @Value
    public static class DataModel {
        CommsCustomer commsCustomer;
        CommsInvoice commsInvoice;
    }


}

