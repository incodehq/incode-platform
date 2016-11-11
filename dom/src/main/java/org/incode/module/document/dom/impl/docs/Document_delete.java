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

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.document.dom.DocumentModule;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;

@Mixin
public class Document_delete {

    //region > constructor
    private final Document document;

    public Document_delete(final Document document) {
        this.document = document;
    }
    //endregion


    public static class ActionDomainEvent extends DocumentModule.ActionDomainEvent<Document_delete> { }
    @Action(
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(cssClassFa = "trash")
    public Object $$() {

        List<Object> attachedToList = Lists.newArrayList();
        final List<Paperclip> paperclips = paperclipRepository.findByDocument(document);
        for (Paperclip paperclip : paperclips) {

            final Object attachedTo = paperclip.getAttachedTo();
            attachedToList.add(attachedTo);

            paperclipRepository.delete(paperclip);
        }

        documentRepository.delete(document);

        // if only attached to a single object, then return; otherwise return null.
        return attachedToList.size() == 1 ? attachedToList.get(0): null;
    }


    @Inject
    DocumentRepository documentRepository;

    @Inject
    PaperclipRepository paperclipRepository;

}
