/*
 *  Copyright 2016 incode.org
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
package org.incode.module.document.dom.services;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.document.dom.impl.applicability.AttachmentAdvisor;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class DocumentCreatorService {

    /**
     * Replaced by {@link org.incode.module.document.dom.api.DocumentService#canCreateDocumentAndAttachPaperclips(Object, DocumentTemplate)} as formal API.
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
     * Replaced by {@link org.incode.module.document.dom.api.DocumentService#createDocumentAndAttachPaperclips(Object, DocumentTemplate)} as formal API.
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
