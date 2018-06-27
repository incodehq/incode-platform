package org.incode.example.document.demo.usage.dom.applicability.rmf;

import java.util.List;

import javax.inject.Inject;

import org.incode.example.document.demo.shared.demowithnotes.dom.DocInvoice;
import org.incode.example.document.demo.shared.demowithnotes.dom.DocDemoObjectWithNotes;
import org.incode.example.document.dom.impl.applicability.RendererModelFactoryAbstract;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;
import org.incode.example.document.dom.impl.paperclips.Paperclip;
import org.incode.example.document.dom.impl.paperclips.PaperclipRepository;

import lombok.Value;

public class RenderModelFactoryOfDocumentAttachedToDemoInvoice extends RendererModelFactoryAbstract<Document> {

    public RenderModelFactoryOfDocumentAttachedToDemoInvoice() {
        super(Document.class);
    }

    @Override
    protected Object doNewRendererModel(
            final DocumentTemplate documentTemplate, final Document document) {

        final List<Paperclip> paperclips = paperclipRepository.findByDocument(document);
        final DocInvoice docInvoice =
                paperclips.stream().map(Paperclip::getAttachedTo)
                .filter(DocInvoice.class::isInstance)
                .map(DocInvoice.class::cast)
                .findFirst()
                .get(); // is safe to do this, because attachment advisor will have already run

        return new DataModel(docInvoice.getCustomer(), docInvoice);
    }


    @Inject
    PaperclipRepository paperclipRepository;

    @Value
    public static class DataModel {
        DocDemoObjectWithNotes demoCustomer;
        DocInvoice docInvoice;
    }


}

