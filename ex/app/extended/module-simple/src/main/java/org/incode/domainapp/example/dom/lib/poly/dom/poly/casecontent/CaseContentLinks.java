package org.incode.domainapp.example.dom.lib.poly.dom.poly.casecontent;

import java.util.List;
import javax.annotation.PostConstruct;
import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Case;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CaseContentLink.class
)
public class CaseContentLinks {

    //region > init
    PolymorphicAssociationLink.Factory<Case,CaseContent,CaseContentLink,CaseContentLink.InstantiateEvent> linkFactory;

    @PostConstruct
    public void init() {
        linkFactory = container.injectServicesInto(
                new PolymorphicAssociationLink.Factory<>(
                        this,
                        Case.class,
                        CaseContent.class,
                        CaseContentLink.class,
                        CaseContentLink.InstantiateEvent.class
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
