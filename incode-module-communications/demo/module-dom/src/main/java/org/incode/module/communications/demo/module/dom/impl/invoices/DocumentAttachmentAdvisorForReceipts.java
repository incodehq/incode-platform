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

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.communications.demo.module.fixture.data.doctypes.DocumentTypesAndTemplatesFixture;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.types.DocumentType;
import org.incode.module.document.dom.impl.types.DocumentTypeRepository;
import org.incode.module.document.dom.spi.DocumentAttachmentAdvisor;

@DomainService(nature = NatureOfService.DOMAIN)
public class DocumentAttachmentAdvisorForReceipts implements DocumentAttachmentAdvisor {

    private static final String ROLE_NAME = "receipt";

    @Override
    public List<DocumentType> documentTypeChoicesFor(final Document document) {
        final DocumentType documentType = documentTypeRepository
                .findByReference(DocumentTypesAndTemplatesFixture.DOC_TYPE_REF_RECEIPT);
        return Lists.newArrayList(documentType);
    }

    @Override
    public DocumentType documentTypeDefaultFor(final Document document) {
        return documentTypeChoicesFor(document).get(0);
    }

    @Override
    public List<String> roleNameChoicesFor(final Document document) {
        return Lists.newArrayList(ROLE_NAME);
    }

    @Override
    public String roleNameDefaultFor(final Document document) {
        return roleNameChoicesFor(document).get(0);
    }

    @Inject
    DocumentTypeRepository documentTypeRepository;


}
