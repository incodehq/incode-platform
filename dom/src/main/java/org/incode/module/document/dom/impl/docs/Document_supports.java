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

import javax.inject.Inject;

import com.google.common.collect.FluentIterable;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.document.dom.DocumentModule;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;

@Mixin(method="prop")
public class Document_supports  {

    private final Document supportingDocumentCandidate;
    public Document_supports(final Document supportingDocumentCandidate) {
        this.supportingDocumentCandidate = supportingDocumentCandidate;
    }

    public static class ActionDomainEvent extends DocumentModule.ActionDomainEvent<Document> {
    }
    @Action(semantics = SemanticsOf.SAFE, domainEvent = ActionDomainEvent.class)
    @ActionLayout(contributed= Contributed.AS_ASSOCIATION)
    public Document prop() {
        return evaluator.supportedBy(supportingDocumentCandidate);
    }

    // hide this property if this document is not actually a supporting document (is instead a "primary" document)
    public boolean hideProp() {
        return prop() == null;
    }


    @Inject
    Evaluator evaluator;

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class Evaluator {

        /**
         * @return the (first) document, if any, for which *this* document is a supporting document, else null
         */
        @Programmatic
        public Document supportedBy(Document secondaryDocument) {
            final List<Paperclip> byDocument = paperclipRepository.findByDocument(secondaryDocument);
            final List<Document> supportedDocumentsIfAny =
                    FluentIterable.from(byDocument)
                                  .transform(x -> x.getAttachedTo())
                                  .filter(Document.class::isInstance)
                                  .transform(Document.class::cast)
                                  .toList();
            return supportedDocumentsIfAny.isEmpty() ? null : supportedDocumentsIfAny.get(0);
        }

        @Inject
        PaperclipRepository paperclipRepository;
    }

}
