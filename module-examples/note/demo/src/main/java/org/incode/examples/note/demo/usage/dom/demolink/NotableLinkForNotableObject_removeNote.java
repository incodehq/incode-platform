package org.incode.examples.note.demo.usage.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.examples.note.demo.shared.demo.dom.NotableObject;
import org.incode.example.note.dom.impl.note.T_removeNote;

@Mixin
public class NotableLinkForNotableObject_removeNote extends T_removeNote<NotableObject> {
    public NotableLinkForNotableObject_removeNote(final NotableObject notable) {
        super(notable);
    }
}
