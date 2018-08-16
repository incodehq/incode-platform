package org.incode.examples.note.demo.usage.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.examples.note.demo.shared.demo.dom.NotableObject;
import org.incode.example.note.dom.impl.note.T_addNote;

@Mixin
public class NotableLinkForNotableObject_addNote extends T_addNote<NotableObject> {
    public NotableLinkForNotableObject_addNote(final NotableObject notable) {
        super(notable);
    }
}
