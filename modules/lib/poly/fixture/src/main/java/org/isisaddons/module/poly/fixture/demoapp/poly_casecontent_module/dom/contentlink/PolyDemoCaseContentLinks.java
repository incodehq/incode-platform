package org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink;

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
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolyDemoCaseContentLink.class
)
public class PolyDemoCaseContentLinks {

    //region > init
    PolymorphicAssociationLink.Factory<PolyDemoCase,PolyDemoCaseContent,PolyDemoCaseContentLink,PolyDemoCaseContentLink.InstantiateEvent> linkFactory;

    @PostConstruct
    public void init() {
        linkFactory = container.injectServicesInto(
                new PolymorphicAssociationLink.Factory<>(
                        this,
                        PolyDemoCase.class,
                        PolyDemoCaseContent.class,
                        PolyDemoCaseContentLink.class,
                        PolyDemoCaseContentLink.InstantiateEvent.class
                ));

    }
    //endregion

    //region > findByCase (programmatic)
    @Programmatic
    public List<PolyDemoCaseContentLink> findByCase(final PolyDemoCase aCase) {
        return container.allMatches(
                new QueryDefault<>(PolyDemoCaseContentLink.class,
                        "findByCase",
                        "case", aCase));
    }
    //endregion

    //region > findByContent (programmatic)
    @Programmatic
    public List<PolyDemoCaseContentLink> findByContent(final PolyDemoCaseContent caseContent) {
        if(caseContent == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(caseContent);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(PolyDemoCaseContentLink.class,
                        "findByContent",
                        "contentObjectType", bookmark.getObjectType(),
                        "contentIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > createLink
    @Programmatic
    public void createLink(final PolyDemoCase aCase, final PolyDemoCaseContent content) {
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
