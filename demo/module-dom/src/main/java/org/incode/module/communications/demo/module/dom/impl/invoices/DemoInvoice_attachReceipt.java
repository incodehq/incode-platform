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
package org.incode.module.communications.demo.module.dom.impl.invoices;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.value.Blob;

import org.incode.module.communications.demo.module.fixture.data.doctypes.DocumentTypesFixture;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentRepository;
import org.incode.module.document.dom.impl.docs.DocumentSort;
import org.incode.module.document.dom.impl.docs.DocumentState;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.impl.types.DocumentType;
import org.incode.module.document.dom.impl.types.DocumentTypeRepository;

@Mixin
public class DemoInvoice_attachReceipt {

    private static final String AT_PATH = "/";
    private static final String ROLE_NAME = "receipt";

    private final DemoInvoice invoice;

    public DemoInvoice_attachReceipt(final DemoInvoice invoice) {
        this.invoice = invoice;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    @MemberOrder(
            name = "documents",
            sequence = "1"
    )
    public DemoInvoice $$(
            @Parameter(fileAccept = "application/pdf")
            final Blob document,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "File name")
            final String fileName
        ) throws IOException {

        String name = determineName(document, fileName);

        final DocumentType documentType = findDocumentType(DocumentTypesFixture.DOC_TYPE_REF_RECEIPT);

        final Document receiptDoc = documentRepository
                .create(documentType, AT_PATH,
                        name,
                        document.getMimeType().getBaseType());

        // unlike documents that are generated from a template (where we call documentTemplate#render), in this case
        // we have the actual bytes; so we just set up the remaining state of the document manually.
        receiptDoc.setRenderedAt(clockService.nowAsDateTime());
        receiptDoc.setState(DocumentState.RENDERED);
        receiptDoc.setSort(DocumentSort.BLOB);
        receiptDoc.setBlobBytes(document.getBytes());

        paperclipRepository.attach(receiptDoc, ROLE_NAME, invoice);

        return invoice;
    }


    private String determineName(
            final Blob document,
            final String fileName) {
        String name = fileName != null ? fileName : document.getName();
        if(!name.endsWith(".pdf")) {
            name = name + ".pdf";
        }
        return name;
    }

    private DocumentType findDocumentType(final String ref) {
        return documentTypeRepository.findByReference(ref);
    }

    @Inject
    DocumentTypeRepository documentTypeRepository;

    @Inject
    PaperclipRepository paperclipRepository;

    @Inject
    DocumentRepository documentRepository;

    @Inject
    ClockService clockService;

}
