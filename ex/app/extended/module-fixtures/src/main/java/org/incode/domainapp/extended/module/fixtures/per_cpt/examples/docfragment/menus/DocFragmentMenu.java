package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.docfragment.menus;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.specloader.SpecificationLoader;

import org.incode.example.docfragment.dom.impl.DocFragment;
import org.incode.example.docfragment.dom.impl.DocFragmentRepository;
import org.incode.example.docfragment.dom.spi.ApplicationTenancyService;
import org.incode.example.docfragment.dom.types.TemplateTextType;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = DocFragment.class
)
@DomainServiceLayout(
        named = "Demo",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "1")
public class DocFragmentMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DocFragment> listAllDocFragments() {
        return docfragmentRepository.listAll();
    }


    public static class CreateDomainEvent extends ActionDomainEvent<DocFragmentMenu> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public DocFragment createDocFragment(
            @ParameterLayout(named="Object type")
            final String objectType,
            @ParameterLayout(named="Name")
            final String name,
            @ParameterLayout(named="At path")
            final String atPath,
            @ParameterLayout(named="Template text", multiLine = TemplateTextType.Meta.MULTILINE)
            final String templateText
            ) {
        return docfragmentRepository.create(objectType, name, atPath, templateText);
    }

    public List<String> choices0CreateDocFragment() {
        final Collection<ObjectSpecification> objectSpecifications = specificationLookup.allSpecifications();
        return Lists.newArrayList(
                FluentIterable.from(objectSpecifications)
                .transform(x -> x.getSpecId().asString())
                .toSortedList(String::compareTo)
        );
    }

    public List<String> choices2CreateDocFragment() {
        return Lists.newArrayList("/", "/ITA", "/FRA");
    }


    @javax.inject.Inject
    DocFragmentRepository docfragmentRepository;

    @javax.inject.Inject
    SpecificationLoader specificationLookup;

    @javax.inject.Inject
    ApplicationTenancyService applicationTenancyService;


}
