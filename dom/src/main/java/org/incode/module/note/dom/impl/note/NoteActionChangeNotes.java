package org.incode.module.note.dom.impl.note;

import com.google.common.base.Strings;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.note.dom.NoteModule;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class NoteActionChangeNotes {

    public static class DomainEvent extends NoteModule.ActionDomainEvent<NoteActionChangeNotes> {
        public DomainEvent(final NoteActionChangeNotes source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Note changeNotes(
            final Note note,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Notes", multiLine = NoteModule.MultiLine.NOTES)
            final String notes) {
        note.setNotes(notes);

        return note;
    }

    public String default1ChangeNotes(final Note note) {
        return note.getNotes();
    }

    public String validateChangeNotes(final Note note, final String notes) {
        if(Strings.isNullOrEmpty(notes) && note.getDate() == null) {
            return "Must specify either note text or a date (or both).";
        }
        return null;
    }


}
