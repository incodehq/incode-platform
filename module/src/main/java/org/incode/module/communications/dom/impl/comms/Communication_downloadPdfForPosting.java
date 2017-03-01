/*
 *  Copyright 2016 Dan Haywood
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
package org.incode.module.communications.dom.impl.comms;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.pdfbox.dom.service.PdfBoxService;

import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelType;
import org.incode.module.communications.dom.mixins.DocumentConstants;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentAbstract;
import org.incode.module.document.dom.impl.docs.Document_downloadExternalUrlAsBlob;

@Mixin
public class Communication_downloadPdfForPosting {

    private final Communication communication;

    public Communication_downloadPdfForPosting(final Communication communication) {
        this.communication = communication;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @ActionLayout(
            named = "Download PDF for posting",
            cssClassFa = "download"
    )
    public Blob $$(
            @ParameterLayout(named = "File name")
            final String fileName) throws IOException {

        communication.sent();

        final List<Document> allEnclosed = findEnclosedPdfDocuments();
        if(allEnclosed.size() == 1) {
            final Document enclosed = allEnclosed.get(0);
            return asBlob(enclosed);
        }

        // merge 'em
        allEnclosed.sort(Ordering.natural().onResultOf(DocumentAbstract::getId));

        final List<byte[]> pdfBytes = Lists.newArrayList();

        for (Document enclosed : allEnclosed) {
            final Blob blob = asBlob(enclosed);
            final byte[] bytes = blob.getBytes();
            pdfBytes.add(bytes);
        }

        final byte[] mergedBytes = pdfBoxService.merge(pdfBytes.toArray(new byte[][] {}));

        return new Blob(fileName, DocumentConstants.MIME_TYPE_APPLICATION_PDF, mergedBytes);
    }

    private Blob asBlob(final Document enclosed) {
        switch (enclosed.getSort()) {
        case BLOB:
            final Blob blob = enclosed.getBlob();
            return blob;
        case EXTERNAL_BLOB:
            return factoryService.mixin(Document_downloadExternalUrlAsBlob.class, enclosed).$$();
        }
        return null;
    }

    public boolean hide$$() {
        if(communication.getType() != CommunicationChannelType.POSTAL_ADDRESS) {
            return true;
        }
        return false;
    }

    public String disable$$() {
        final List<Document> enclosed = findEnclosedPdfDocuments();
        if(enclosed.isEmpty()) {
            return String.format("Cannot locate any '%s' (PDF) documents", DocumentConstants.PAPERCLIP_ROLE_ENCLOSED);
        }
        return null;
    }

    public List<String> choices0$$() {
        final List<Document> pdfDocuments = findEnclosedPdfDocuments();
        return Lists.newArrayList(pdfDocuments.stream().map(Document::getName).collect(Collectors.toList()));
    }

    private List<Document> findEnclosedPdfDocuments() {
        return communication.findDocuments(
                DocumentConstants.PAPERCLIP_ROLE_ENCLOSED,
                DocumentConstants.MIME_TYPE_APPLICATION_PDF);
    }



    @Inject
    FactoryService factoryService;
    @Inject
    PdfBoxService pdfBoxService;




}
