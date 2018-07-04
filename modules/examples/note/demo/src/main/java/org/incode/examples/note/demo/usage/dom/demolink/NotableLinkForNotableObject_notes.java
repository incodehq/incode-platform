package org.incode.examples.note.demo.usage.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.examples.note.demo.shared.demo.dom.NotableObject;
import org.incode.example.note.dom.impl.note.T_notes;

@Mixin
public class NotableLinkForNotableObject_notes extends T_notes<NotableObject> {
    public NotableLinkForNotableObject_notes(final NotableObject notable) {
        super(notable);
    }
}
