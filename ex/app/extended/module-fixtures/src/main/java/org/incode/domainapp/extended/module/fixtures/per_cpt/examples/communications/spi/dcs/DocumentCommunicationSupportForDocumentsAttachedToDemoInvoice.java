package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.spi.dcs;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.communications.dom.impl.commchannel.CommunicationChannel;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwnerLink;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwnerLinkRepository;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelType;
import org.incode.example.communications.dom.spi.CommHeaderAbstract;
import org.incode.example.communications.dom.spi.CommHeaderForEmail;
import org.incode.example.communications.dom.spi.CommHeaderForPost;
import org.incode.example.communications.dom.spi.DocumentCommunicationSupport;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.paperclips.Paperclip;
import org.incode.example.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.example.document.dom.impl.types.DocumentType;
import org.incode.example.document.dom.impl.types.DocumentTypeRepository;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoObjectWithNotes;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoInvoice;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.fixture.doctypes.DocumentType_and_DocumentTemplates_createSome;

@DomainService(nature = NatureOfService.DOMAIN)
public class DocumentCommunicationSupportForDocumentsAttachedToDemoInvoice implements DocumentCommunicationSupport {

    @Override
    public DocumentType emailCoverNoteDocumentTypeFor(final Document document) {

        final DemoInvoice invoice = paperclipRepository.paperclipAttaches(document, DemoInvoice.class);
        if (invoice == null) {
            return null;
        }

        final DocumentType documentType = documentTypeRepository
                .findByReference(DocumentType_and_DocumentTemplates_createSome.DOC_TYPE_REF_FREEMARKER_HTML);
        return documentType;
    }


    @Override
    public void inferEmailHeaderFor(
            final Document document,
            final CommHeaderForEmail header) {

        inferToHeader(document, header, CommunicationChannelType.EMAIL_ADDRESS);

    }

    @Override
    public void inferPrintHeaderFor(
            final Document document, final CommHeaderForPost header) {

        inferToHeader(document, header, CommunicationChannelType.POSTAL_ADDRESS);
    }

    private <T extends CommunicationChannel> void inferToHeader(
            final Document document,
            final CommHeaderAbstract<T> header,
            final CommunicationChannelType channelType) {

        final List<Paperclip> paperclips = paperclipRepository.findByDocument(document);
        for (final Paperclip paperclip : paperclips) {
            final Object attachedTo = paperclip.getAttachedTo();

            if(attachedTo instanceof DemoInvoice) {
                final DemoInvoice invoice = (DemoInvoice) attachedTo;
                addTo(invoice, header, channelType);
            }
        }

    }

    private <T extends CommunicationChannel> void addTo(
            final DemoInvoice invoice,
            final CommHeaderAbstract<T> header,
            final CommunicationChannelType channelType) {

        final DemoObjectWithNotes customer = invoice.getCustomer();

        final List<CommunicationChannelOwnerLink> links =
                communicationChannelOwnerLinkRepository.findByOwner(customer);

        final List channels = links.stream().map(
                CommunicationChannelOwnerLink::getCommunicationChannel)
                .filter(cc -> cc.getType() == channelType)
                .collect(Collectors.toList());

        header.getToChoices().addAll(channels);
    }

    @Inject
    CommunicationChannelOwnerLinkRepository communicationChannelOwnerLinkRepository;

    @Inject
    PaperclipRepository paperclipRepository;

    @Inject
    DocumentTypeRepository documentTypeRepository;

}
