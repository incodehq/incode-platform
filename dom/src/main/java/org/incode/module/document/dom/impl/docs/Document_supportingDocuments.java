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

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.tablecol.TableColumnOrderService;

import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.mixins.T_documents;

@Mixin
public class Document_supportingDocuments extends T_documents<Document> {

    public Document_supportingDocuments(final Document document) {
        super(document);
    }

    // hide if this document is actually a supporting document for some other primary document
    public boolean hide$$() {
        final Document document = supportsEvaluator.supportedBy(getAttachedTo());
        return document != null;
    }

    @DomainService(
            nature = NatureOfService.DOMAIN,
            menuOrder = "100"
    )
    public static class ColumnOrderServiceInbound implements TableColumnOrderService {

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

            if (!"supportingDocuments".equals(collectionId)) {
                return null;
            }

            final List<String> trimmedPropertyIds = Lists.newArrayList(propertyIds);
            trimmedPropertyIds.remove("attachedTo");
            return trimmedPropertyIds;
        }

        @Override
        public List<String> orderStandalone(final Class<?> collectionType, final List<String> propertyIds) {
            return null;
        }
    }

    @Inject
    Document_supports.Evaluator supportsEvaluator;

}
