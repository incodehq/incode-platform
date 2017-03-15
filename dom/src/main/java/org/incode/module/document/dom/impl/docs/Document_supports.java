/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
import java.util.Set;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import com.google.common.collect.Sets;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.incode.module.document.dom.DocumentModule;
import org.incode.module.document.dom.spi.SupportingDocumentsEvaluator;

/**
 * In essence this renames the {@link DocumentAbstract_attachedTo} mixin to "supports", only contributed to those
 * documents where at least one {@link SupportingDocumentsEvaluator} indicates that the document in question is
 * in support of some other primary document.
 */
@Mixin(method="coll")
public class Document_supports  {

    private final Document supportingDocumentCandidate;
    public Document_supports(final Document supportingDocumentCandidate) {
        this.supportingDocumentCandidate = supportingDocumentCandidate;
    }

    public static class ActionDomainEvent extends DocumentModule.ActionDomainEvent<Document> {
    }
    @Action(semantics = SemanticsOf.SAFE, domainEvent = ActionDomainEvent.class)
    @ActionLayout(contributed= Contributed.AS_ASSOCIATION)
    public Set<Document> coll() {
        return supportedDocuments();
    }

    public boolean hideColl() {
        Set<Document> documents = supportedDocuments();
        return documents.isEmpty();
    }

    private Set<Document> supportedDocuments() {
        return queryResultsCache.execute(new Callable<Set<Document>>() {
            @Override public Set<Document> call() throws Exception {
                return supportedDocumentsNoCache(supportingDocumentCandidate);
            }
        }, Document_supports.class, "supportedDocuments", supportingDocumentCandidate);
    }

    private Set<Document> supportedDocumentsNoCache(final Document supportingDocumentCandidate) {
        Set<Document> documents = Sets.newTreeSet();
        for (SupportingDocumentsEvaluator supportingDocumentsEvaluator : supportingDocumentsEvaluators) {
            List<Document> supportedDocuments =
                    supportingDocumentsEvaluator.supportedBy(supportingDocumentCandidate);
            if(supportedDocuments != null) {
                documents.addAll(supportedDocuments);
            }
        }
        return documents;
    }

    @Inject
    QueryResultsCache queryResultsCache;

    @Inject
    List<SupportingDocumentsEvaluator> supportingDocumentsEvaluators;

}
