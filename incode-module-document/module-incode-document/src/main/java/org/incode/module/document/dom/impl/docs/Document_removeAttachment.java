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
package org.incode.module.document.dom.impl.docs;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.incode.module.document.dom.DocumentModule;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;

@Mixin(method = "exec")
public class Document_removeAttachment {

    //region > constructor
    private final Document document;

    public Document_removeAttachment(final Document document) {
        this.document = document;
    }
    //endregion

    public enum Policy {
        DETACH_ONLY("Detach only"),
        DELETE_ATTACHMENT_DOCUMENT_ALSO("Delete attachment document also");

        private final String title;

        Policy(final String title) {
            this.title = title;
        }

        public String title() {
            return title;
        }
    }

    public static class ActionDomainEvent extends DocumentModule.ActionDomainEvent<Document_removeAttachment> { }
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(cssClassFa = "minus-square")
    public Object exec(
            final Document attachment,
            final Policy policy) {

        if(policy == Policy.DELETE_ATTACHMENT_DOCUMENT_ALSO) {

            // use mixin to cascade delete of the attachment and all of its paperclips
            factoryService.mixin(Document_delete.class, attachment).$$();

        } else {

            // remove all attachments between the attached (supporting) document and this document
            // note that the attachment actually "points to" this document, not the other way around.
            List<Paperclip> paperclips = paperclipRepository.findByDocumentAndAttachedTo(attachment, document);
            for (Paperclip paperclip : paperclips) {
                paperclipRepository.delete(paperclip);
            }

        }
        return document;
    }

    public TranslatableString disableExec() {
        if (choices0Exec().isEmpty()) {
            return TranslatableString.tr("No attachments");
        }
        return null;
    }

    public List<Document> choices0Exec() {
        return queryResultsCache.execute(() -> findAttachedToDocumentsNoCache(),
                Document_removeAttachment.class, "choices0Exec", document);
    }

    public Document default0Exec() {
        List<Document> documents = choices0Exec();
        return documents.size() == 1 ? documents.get(0): null;
    }

    private List<Document> findAttachedToDocumentsNoCache() {
        // links of other documents that attach to this document
        final List<Paperclip> paperclips = paperclipRepository.findByAttachedTo(document);
        return Lists.newArrayList(
                FluentIterable.from(paperclips).transform(Paperclip::getDocument)
                        .filter(Document.class::isInstance)
                        .transform(Document.class::cast)
                        .toList());
    }

    @Inject
    FactoryService factoryService;

    @Inject
    PaperclipRepository paperclipRepository;

    @Inject
    QueryResultsCache queryResultsCache;

}
