package org.incode.module.note.dom.impl.note;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.note.dom.api.notable.Notable;

@Mixin
public class Note_remove {

    //region > injected services
    @Inject
    NoteRepository noteRepository;
    //endregion

    //region > constructor
    private final Note note;
    public Note_remove(final Note note) {
        this.note = note;
    }
    @Programmatic
    public Note getNote() {
        return note;
    }
    //endregion


    public static class DomainEvent extends Note.ActionDomainEvent<Note_remove> { }
    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
        // position = ActionLayout.Position.PANEL, // hmm... seems not to be recognized...
        cssClass = "warning",
        cssClassFa = "trash"
    )
    public Notable $$() {
        final Notable notable = this.note.getNotable();
        noteRepository.remove(this.note);
        return notable;
    }

}
