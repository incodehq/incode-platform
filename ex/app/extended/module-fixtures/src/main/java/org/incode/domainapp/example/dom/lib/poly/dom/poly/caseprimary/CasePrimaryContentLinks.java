package org.incode.domainapp.example.dom.lib.poly.dom.poly.caseprimary;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;

import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.example.dom.lib.poly.dom.poly.casecontent.CaseContent;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CasePrimaryContentLink.class
)
public class CasePrimaryContentLinks {

    //region > init
    PolymorphicAssociationLink.Factory<Case,CaseContent,CasePrimaryContentLink,CasePrimaryContentLink.InstantiateEvent> linkFactory;

    @PostConstruct
    public void init() {
        linkFactory = container.injectServicesInto(
                new PolymorphicAssociationLink.Factory<>(
                        this,
                        Case.class,
                        CaseContent.class,
                        CasePrimaryContentLink.class,
                        CasePrimaryContentLink.InstantiateEvent.class
                ));

    }
    //endregion

    //region > findByCase (programmatic)
    @Programmatic
    public CasePrimaryContentLink findByCase(final Case aCase) {
        return container.firstMatch(
                new QueryDefault<>(CasePrimaryContentLink.class,
                        "findByCase",
                        "case", aCase));
    }
    //endregion

    //region > findByContent (programmatic)
    @Programmatic
    public List<CasePrimaryContentLink> findByContent(final CaseContent caseContent) {
        if(caseContent == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(caseContent);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(CasePrimaryContentLink.class,
                        "findByContent",
                        "contentObjectType", bookmark.getObjectType(),
                        "contentIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > findByCaseAndContent (programmatic)
    @Programmatic
    public CasePrimaryContentLink findByCaseAndContent(final Case aCase, final CaseContent caseContent) {
        if(caseContent == null || aCase == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(caseContent);
        if(bookmark == null) {
            return null;
        }
        return container.firstMatch(
                new QueryDefault<>(CasePrimaryContentLink.class,
                        "findByCaseAndContent",
                        "case", aCase,
                        "contentObjectType", bookmark.getObjectType(),
                        "contentIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > createLink
    @Programmatic
    public void createLink(final Case aCase, final CaseContent content) {
        linkFactory.createLink(aCase, content);
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    //endregion

}
