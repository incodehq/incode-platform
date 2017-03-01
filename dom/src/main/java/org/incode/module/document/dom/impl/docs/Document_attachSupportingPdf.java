/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.document.dom.impl.docs;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.FluentIterable;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.value.Blob;

import org.incode.module.document.dom.DocumentModule;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.impl.types.DocumentType;
import org.incode.module.document.dom.spi.DocumentAttachmentAdvisor;

@Mixin(method = "exec")
public class Document_attachSupportingPdf {

    private final Document document;

    public Document_attachSupportingPdf(final Document document) {
        this.document = document;
    }

    public static class ActionDomainEvent extends DocumentModule.ActionDomainEvent<Document_attachSupportingPdf> { }

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION,
            cssClassFa = "paperclip",
            named = "Attach supporting PDF"
    )
    public Document exec(
            final DocumentType documentType,
            @Parameter(fileAccept = "application/pdf")
            final Blob document,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "File name")
            final String fileName,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "Role name")
            final String roleName
        ) throws IOException {

        String name = determineName(document, fileName);

        final Document doc = documentRepository.create(
                                documentType, this.document.getAtPath(), name, document.getMimeType().getBaseType());

        // unlike documents that are generated from a template (where we call documentTemplate#render), in this case
        // we have the actual bytes; so we just set up the remaining state of the document manually.
        doc.setRenderedAt(clockService.nowAsDateTime());
        doc.setState(DocumentState.RENDERED);
        doc.setSort(DocumentSort.BLOB);
        doc.setBlobBytes(document.getBytes());

        paperclipRepository.attach(doc, roleName, this.document);

        return this.document;
    }

    public boolean hideExec() {
        if (documentAttachmentAdvisor == null) {
            return true;
        }
        final List<DocumentType> documentTypes = documentAttachmentAdvisor.documentTypeChoicesFor(document);
        if (documentTypes == null || documentTypes.isEmpty()) {
            return true;
        }

        final List<Paperclip> paperclips = paperclipRepository.findByDocument(document);

        // hide if this document is already attached to other documents.
        // (we want to only attach to the "lead" document.
        return !FluentIterable.from(paperclips).filter(paperclip -> paperclip.getAttachedTo() instanceof Document).isEmpty();
    }

    public List<DocumentType> choices0Exec() {
        return documentAttachmentAdvisor.documentTypeChoicesFor(document);
    }
    public DocumentType default0Exec() {
        return documentAttachmentAdvisor.documentTypeDefaultFor(document);
    }
    public List<String> choices3Exec() {
        return documentAttachmentAdvisor.roleNameChoicesFor(document);
    }
    public String default3Exec() {
        return documentAttachmentAdvisor.roleNameDefaultFor(document);
    }

    private static String determineName(
            final Blob document,
            final String fileName) {
        String name = fileName != null ? fileName : document.getName();
        if(!name.toLowerCase().endsWith(".pdf")) {
            name = name + ".pdf";
        }
        return name;
    }

    @Inject
    DocumentAttachmentAdvisor documentAttachmentAdvisor;

    @Inject
    PaperclipRepository paperclipRepository;

    @Inject
    DocumentRepository documentRepository;

    @Inject
    ClockService clockService;

}
