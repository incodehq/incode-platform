package org.incode.domainapp.example.dom.lib.poly.dom.poly.casecontent;

import java.util.List;
import javax.inject.Inject;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Cases;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CaseContentContributions {

    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public Case addToCase(final Case aCase, final CaseContent caseContent) {
        caseContentLinks.createLink(aCase, caseContent);
        return aCase;
    }
    public String disableAddToCase(final Case aCase, final CaseContent caseContent) {
        if (aCase != null) {
            // in effect, don't contribute to Case
            return "To add to content, navigate to object and select 'add to case'";
        } else {
            return null;
        }
    }

    public List<Case> choices0AddToCase(final Case aCase, final CaseContent caseContent) {
        final List<Case> caseList = Lists.newArrayList(cases.listAllCases());
        final List<Case> currentCases = choices0RemoveFromCase(null, caseContent);
        caseList.removeAll(currentCases);
        return caseList;
    }

    private static Predicate<Case> notSameAs(final Case aCase) {
        return new Predicate<Case>() {
            @Override
            public boolean apply(final Case input) {
                return input == aCase;
            }
        };
    }


    public static class RemoveFromCaseDomainEvent extends ActionDomainEvent<CaseContentContributions> {
        public RemoveFromCaseDomainEvent(final CaseContentContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
        public Case getCase() {
            return (Case) getArguments().get(0);
        }
        public CaseContent getContent() {
            return (CaseContent) getArguments().get(1);
        }
    }

    @Action(
            domainEvent = RemoveFromCaseDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public Case removeFromCase(final Case aCase, final CaseContent caseContent) {
        final List<CaseContentLink> contentLinks = caseContentLinks.findByCase(aCase);
        for (CaseContentLink contentLink : contentLinks) {
            if(contentLink.getPolymorphicReference() == caseContent) {
                container.removeIfNotAlready(contentLink);
                break;
            }
        }
        return aCase;
    }

    public String disableRemoveFromCase(final Case aCase, final CaseContent caseContent) {
        if (caseContent != null) {
            return choices0RemoveFromCase(null, caseContent).isEmpty()? "Not contained in any case": null;
        } else {
            return choices1RemoveFromCase(aCase).isEmpty()? "No contents to remove": null;
        }
    }

    public List<Case> choices0RemoveFromCase(final Case aCase, final CaseContent caseContent) {
        final List<CaseContentLink> contentLinks = caseContentLinks.findByContent(caseContent);
        return Lists.newArrayList(Iterables.transform(contentLinks, CaseContentLink.Functions.GET_CASE));
    }
    public List<CaseContent> choices1RemoveFromCase(final Case aCase) {
        final List<CaseContentLink> contentLinks = caseContentLinks.findByCase(aCase);
        return Lists.newArrayList(Iterables.transform(contentLinks, CaseContentLink.Functions.GET_CONTENT));
    }

    public String validateRemoveFromCase(final Case aCase, final CaseContent caseContent) {
        final List<CaseContentLink> contentLinks = caseContentLinks.findByCase(aCase);
        for (CaseContentLink contentLink : contentLinks) {
            if(contentLink.getPolymorphicReference() == caseContent) {
                return null;
            }
        }
        return "Not contained within case";
    }


    //region > contents (derived collection)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    public List<CaseContent> contents(final Case aCase) {
        final List<CaseContentLink> links = caseContentLinks.findByCase(aCase);
        return Lists.newArrayList(
                Iterables.transform(links, CaseContentLink.Functions.GET_CONTENT)
        );
    }
    //endregion

    //region > cases (derived collection)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    public List<Case> cases(final CaseContent caseContent) {
        final List<CaseContentLink> links = caseContentLinks.findByContent(caseContent);
        return Lists.newArrayList(
                Iterables.transform(links, CaseContentLink.Functions.GET_CASE)
        );
    }
    //endregion

    //region > title (derived property)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @PropertyLayout(
            hidden = Where.OBJECT_FORMS
    )
    public String title(final CaseContent caseContent) {
        return container.titleOf(caseContent);
    }
    //endregion





    @Inject
    private Cases cases;
    @Inject
    private CaseContentLinks caseContentLinks;
    @Inject
    private DomainObjectContainer container;

}
