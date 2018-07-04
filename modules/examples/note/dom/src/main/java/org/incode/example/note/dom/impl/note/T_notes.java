package org.incode.example.note.dom.impl.note;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.example.note.NoteModule;

public abstract class T_notes<T> {

    //region > constructor
    private final T notable;
    public T_notes(final T notable) {
        this.notable = notable;
    }

    public T getNotable() {
        return notable;
    }
    //endregion

    //region > $$
    public static class DomainEvent extends NoteModule.ActionDomainEvent<T_notes> { } { }
    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            defaultView = "table",
            named = "Notes" // might be required, was a regression in 1.11.x
    )
    public List<Note> $$() {
        return noteRepository.findByNotable(this.notable);
    }

    //endregion

    //region  > (injected)
    @Inject
    NoteRepository noteRepository;
    //endregion


}
