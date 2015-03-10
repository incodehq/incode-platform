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
package org.isisaddons.module.poly.fixture.dom.modules.casemgmt;

import org.isisaddons.module.poly.dom.PolymorphicLinkHelper;
import org.isisaddons.module.poly.dom.PolymorphicLinkInstantiateEvent;

import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CaseContentLink.class
)
public class CaseContentLinks {

    //region > init
    public static class InstantiateEvent
            extends PolymorphicLinkInstantiateEvent<Case, CaseContent, CaseContentLink> {

        public InstantiateEvent(final Object source, final Case aCase, final CaseContent content) {
            super(CaseContentLink.class, source, aCase, content);
        }
    }

    PolymorphicLinkHelper<Case,CaseContent,CaseContentLink,InstantiateEvent> ownerLinkHelper;

    @PostConstruct
    public void init() {
        ownerLinkHelper = container.injectServicesInto(
                new PolymorphicLinkHelper<>(
                        this,
                        Case.class,
                        CaseContent.class,
                        CaseContentLink.class,
                        InstantiateEvent.class
                ));

    }
    //endregion

    //region > findByCase (programmatic)
    @Programmatic
    public List<CaseContentLink> findByCase(final Case aCase) {
        return container.allMatches(
                new QueryDefault<>(CaseContentLink.class,
                        "findByCase",
                        "case", aCase));
    }
    //endregion

    //region > findByContent (programmatic)
    @Programmatic
    public List<CaseContentLink> findByContent(final CaseContent caseContent) {
        if(caseContent == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(caseContent);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(CaseContentLink.class,
                        "findByContent",
                        "contentObjectType", bookmark.getObjectType(),
                        "contentIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > createLink
    @Programmatic
    public void createLink(final Case aCase, final CaseContent content) {
        ownerLinkHelper.createLink(aCase, content);
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    //endregion

}
