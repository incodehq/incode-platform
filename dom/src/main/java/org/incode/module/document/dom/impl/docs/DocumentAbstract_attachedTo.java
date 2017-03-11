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
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;
import org.apache.isis.applib.services.tablecol.TableColumnOrderService;

import org.incode.module.document.dom.DocumentModule;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.spi.SupportingDocumentsEvaluator;

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
        if (document instanceof DocumentTemplate && $$().isEmpty()) {
            return true;
        }
        if(document instanceof Document) {
            // hide for supporting documents
            final Document doc = (Document) document;
            for (SupportingDocumentsEvaluator supportingDocumentsEvaluator : supportingDocumentsEvaluators) {
                final SupportingDocumentsEvaluator.Evaluation evaluation =
                        supportingDocumentsEvaluator.evaluate(doc);
                if(evaluation == SupportingDocumentsEvaluator.Evaluation.SUPPORTING) {
                    return true;
                }
            }
        }
        return false;
    }



    //region > injected services
    @Inject
    List<SupportingDocumentsEvaluator> supportingDocumentsEvaluators;
    @Inject
    PaperclipRepository paperclipRepository;
    @Inject
    QueryResultsCache queryResultsCache;
    //endregion


    @DomainService(
            nature = NatureOfService.DOMAIN,
            menuOrder = "100"
    )
    public static class ColumnOrderService implements TableColumnOrderService {

        @Override
        public List<String> orderParented(
                final Object domainObject,
                final String collectionId,
                final Class<?> collectionType,
                final List<String> propertyIds) {
            if (!Paperclip.class.isAssignableFrom(collectionType)) {
                return null;
            }

            if (!(domainObject instanceof DocumentAbstract)) {
                return null;
            }

            if (!"attachedTo".equals(collectionId)) {
                return null;
            }

            final List<String> trimmedPropertyIds = Lists.newArrayList(propertyIds);
            trimmedPropertyIds.remove("document");
            trimmedPropertyIds.remove("documentDate");
            trimmedPropertyIds.remove("documentState");
            return trimmedPropertyIds;
        }

        @Override
        public List<String> orderStandalone(final Class<?> collectionType, final List<String> propertyIds) {
            return null;
        }
    }

}
