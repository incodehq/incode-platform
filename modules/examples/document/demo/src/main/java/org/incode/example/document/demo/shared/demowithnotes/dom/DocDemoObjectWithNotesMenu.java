package org.incode.example.document.demo.shared.demowithnotes.dom;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDocumentsDemo.DocDemoObjectWithNotesMenu"
)
@DomainServiceLayout(
        named = "Demo",
        menuOrder = "10.5"
)
public class DocDemoObjectWithNotesMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DocDemoObjectWithNotes> listAllDemoObjectsWithNotes() {
        return docDemoObjectWithNotesRepository.listAll();
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<DocDemoObjectWithNotes> findDemoObjectsWithNotesByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return docDemoObjectWithNotesRepository.findByName(name);
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @MemberOrder(sequence = "3")
    public DocDemoObjectWithNotes createDemoObjectWithNotes(
            @ParameterLayout(named="Name")
            final String name) {
        return docDemoObjectWithNotesRepository.create(name);
    }


    @javax.inject.Inject
    DocDemoObjectWithNotesRepository docDemoObjectWithNotesRepository;

}
