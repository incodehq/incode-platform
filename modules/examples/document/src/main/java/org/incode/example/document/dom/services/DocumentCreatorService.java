package org.incode.example.document.dom.services;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.example.document.dom.api.DocumentService;
import org.incode.example.document.dom.impl.applicability.AttachmentAdvisor;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;
import org.incode.example.document.dom.impl.paperclips.PaperclipRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class DocumentCreatorService {

    /**
     * Replaced by {@link DocumentService#canCreateDocumentAndAttachPaperclips(Object, DocumentTemplate)} as formal API.
     *
     * @param domainObject
     * @param template
     * @return
     */
    @Deprecated
    @Programmatic
    public boolean canCreateDocumentAndAttachPaperclips(
            final Object domainObject,
            final DocumentTemplate template) {
        final AttachmentAdvisor attachmentAdvisor = template.newAttachmentAdvisor(domainObject);
        return attachmentAdvisor != null;
    }

    /**
     * Replaced by {@link DocumentService#createDocumentAndAttachPaperclips(Object, DocumentTemplate)} as formal API.
     *
     * @param domainObject
     * @param template
     * @return
     */
    @Deprecated
    @Programmatic
    public Document createDocumentAndAttachPaperclips(
            final Object domainObject,
            final DocumentTemplate template) {

        final Document createdDocument = template.create(domainObject);

        final List<AttachmentAdvisor.PaperclipSpec> paperclipSpecs = template.newAttachmentAdvice(createdDocument, domainObject);

        for (AttachmentAdvisor.PaperclipSpec paperclipSpec : paperclipSpecs) {
            final String roleName = paperclipSpec.getRoleName();
            final Object attachTo = paperclipSpec.getAttachTo();
            final Document paperclipSpecCreatedDocument = paperclipSpec.getCreatedDocument();
            if(paperclipRepository.canAttach(attachTo)) {
                paperclipRepository.attach(paperclipSpecCreatedDocument, roleName, attachTo);
            }
        }

        return createdDocument;
    }

    @Inject
    PaperclipRepository paperclipRepository;



}
