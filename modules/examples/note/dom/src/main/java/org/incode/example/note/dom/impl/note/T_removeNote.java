package org.incode.example.note.dom.impl.note;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.example.note.NoteModule;

public abstract class T_removeNote<T> {

    //region > constructor
    private final T notable;
    public T_removeNote(final T notable) {
        this.notable = notable;
    }

    public T getNotable() {
        return notable;
    }
    //endregion

    //region > $$

    public static class DomainEvent extends NoteModule.ActionDomainEvent<T_removeNote> { } { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
            cssClassFa = "fa-minus",
            named = "Remove",
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "notes", sequence = "2")
    public Object $$(final Note note) {
        noteRepository.remove(note);
        return this.notable;
    }

    public String disable$$() {
        return choices0$$().isEmpty() ? "No content to remove" : null;
    }

    public List<Note> choices0$$() {
        return this.notable != null ? noteRepository.findByNotable(this.notable): Collections.emptyList();
    }

    public Note default0$$() {
        return firstOf(choices0$$());
    }
    //endregion

    //region > helpers
    static <T> T firstOf(final List<T> list) {
        return list.isEmpty()? null: list.get(0);
    }
    //endregion

    //region  > (injected)
    @Inject
    NoteRepository noteRepository;
    //endregion


}
