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

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.impl.types.DocumentType;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DocumentAbstract.class
)
public class DocumentRepository {

    public String getId() {
        return "incodeDocuments.DocumentRepository";
    }


    @Programmatic
    public Document create(
            final DocumentType type,
            final String atPath,
            final String documentName,
            final String mimeType) {
        final DateTime createdAt = clockService.nowAsDateTime();
        final Document document = new Document(type, atPath, documentName, mimeType, createdAt);
        repositoryService.persist(document);
        return document;
    }

    @Inject
    PaperclipRepository paperclipRepository;

    @Programmatic
    public List<Document> findOrphaned() {

//        // from https://docs.oracle.com/cd/E13189_01/kodo/docs324/ref_guide_subqueries.html
//        // doesn't work...
//        Query query = isisJdoSupport.getJdoPersistenceManager().newQuery(Document.class,
//                "(SELECT FROM org.incode.module.document.dom.impl.paperclips.Paperclip p WHERE p.document == document).isEmpty () ");
//        Object execute = query.execute();
//        return (List<Document>) execute;

        // TODO: naive implementation for now
        final List<Document> documents = repositoryService.allInstances(Document.class);
        for (Iterator<Document> iterator = documents.iterator(); iterator.hasNext(); ) {
            final Document document = iterator.next();
            List<Paperclip> paperclips = paperclipRepository.findByDocument(document);
            if (!paperclips.isEmpty()) {
                iterator.remove();
            }
        }
        return documents;
    }

    @Programmatic
    public List<Document> findBetween(final LocalDate startDate, final LocalDate endDateIfAny) {

        final DateTime startDateTime = startDate.toDateTimeAtStartOfDay();

        final QueryDefault<Document> query;
        if (endDateIfAny != null) {
            final DateTime endDateTime = endDateIfAny.plusDays(1).toDateTimeAtStartOfDay();
            query = new QueryDefault<>(Document.class,
                    "findByCreatedAtBetween",
                    "startDateTime", startDateTime,
                    "endDateTime", endDateTime);
        }
        else {
            query = new QueryDefault<>(Document.class,
                    "findByCreatedAtAfter",
                    "startDateTime", startDateTime);
        }

        return repositoryService.allMatches(query);
    }

    @Programmatic
    public List<DocumentAbstract> allDocuments() {
        return repositoryService.allInstances(DocumentAbstract.class);
    }


    @Programmatic
    public void delete(final Document document) {
        repositoryService.removeAndFlush(document);
    }


    //region > injected services

    @Inject
    QueryResultsCache queryResultsCache;

    @Inject
    IsisJdoSupport isisJdoSupport;
    @Inject
    RepositoryService repositoryService;
    @Inject
    ClockService clockService;


    //endregion

}
