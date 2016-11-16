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

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.incode.module.document.dom.DocumentModule;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;

@Mixin
public class DocumentAbstract_attachedTo<T> {


    //region > constructor and mixedIn accessor
    private final DocumentAbstract<?> document;

    public DocumentAbstract_attachedTo(final DocumentAbstract<?> document) {
        this.document = document;
    }

    @Programmatic
    public DocumentAbstract getDocument() {
        return document;
    }

    //endregion

    public static class ActionDomainEvent extends DocumentModule.ActionDomainEvent<DocumentAbstract_attachedTo>  { }
    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(defaultView = "table")
    public List<Paperclip> $$() {
        return queryResultsCache.execute(
                () -> paperclipRepository.findByDocument(document)
                , getClass(), "$$", document);
    }

    public boolean hide$$() {
        return document instanceof DocumentTemplate && $$().isEmpty();
    }

    //region > injected services
    @Inject
    PaperclipRepository paperclipRepository;
    @Inject
    QueryResultsCache queryResultsCache;
    //endregion

}
