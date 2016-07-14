package org.incode.module.note.dom.impl.note;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;


@Mixin
public class Note_remove {

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

    //region > $$


    public static class DomainEvent extends Note.ActionDomainEvent<Note_remove> { }
    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
        cssClass = "btn-warning",
        cssClassFa = "trash"
    )
    public Object $$() {
        final Object notable = this.note.getNotable();
        noteRepository.remove(this.note);
        return notable;
    }

    //endregion

    //region > injected services
    @Inject
    NoteRepository noteRepository;
    //endregion

}
