package org.incode.module.note.dom.impl.note;

import javax.inject.Inject;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.note.dom.api.notable.Notable;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class NoteActionRemove {

    public static class DomainEvent extends Note.ActionDomainEvent<NoteActionRemove> {
        public DomainEvent(
                final NoteActionRemove source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Notable remove(
            final Note note,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Are you sure?")
            final Boolean areYouSure
    ) {
        final Notable notable = note.getNotable();
        noteRepository.remove(note);
        return notable;
    }

    public String validateRemove(final Note note, final Boolean areYouSure) {
        return areYouSure != null && areYouSure
                ? null
                : "Check the 'are you sure' to continue";
    }


    @Inject
    NoteRepository noteRepository;

}
