package org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom;

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
        repositoryFor = PolyDemoPolyDemoCasePrimaryContentLink.class
)
public class PolyDemoCasePrimaryContentLinks {

    //region > init
    PolymorphicAssociationLink.Factory<PolyDemoCase,PolyDemoCaseContent,PolyDemoPolyDemoCasePrimaryContentLink,PolyDemoPolyDemoCasePrimaryContentLink.InstantiateEvent> linkFactory;

    @PostConstruct
    public void init() {
        linkFactory = container.injectServicesInto(
                new PolymorphicAssociationLink.Factory<>(
                        this,
                        PolyDemoCase.class,
                        PolyDemoCaseContent.class,
                        PolyDemoPolyDemoCasePrimaryContentLink.class,
                        PolyDemoPolyDemoCasePrimaryContentLink.InstantiateEvent.class
                ));

    }
    //endregion

    //region > findByCase (programmatic)
    @Programmatic
    public PolyDemoPolyDemoCasePrimaryContentLink findByCase(final PolyDemoCase aCase) {
        return container.firstMatch(
                new QueryDefault<>(PolyDemoPolyDemoCasePrimaryContentLink.class,
                        "findByCase",
                        "case", aCase));
    }
    //endregion

    //region > findByContent (programmatic)
    @Programmatic
    public List<PolyDemoPolyDemoCasePrimaryContentLink> findByContent(final PolyDemoCaseContent caseContent) {
        if(caseContent == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(caseContent);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(PolyDemoPolyDemoCasePrimaryContentLink.class,
                        "findByContent",
                        "contentObjectType", bookmark.getObjectType(),
                        "contentIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > findByCaseAndContent (programmatic)
    @Programmatic
    public PolyDemoPolyDemoCasePrimaryContentLink findByCaseAndContent(final PolyDemoCase aCase, final PolyDemoCaseContent caseContent) {
        if(caseContent == null || aCase == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(caseContent);
        if(bookmark == null) {
            return null;
        }
        return container.firstMatch(
                new QueryDefault<>(PolyDemoPolyDemoCasePrimaryContentLink.class,
                        "findByCaseAndContent",
                        "case", aCase,
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
