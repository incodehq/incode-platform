package org.incode.example.alias.demo.examples.note.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.alias.demo.shared.dom.DemoObject;
import org.incode.example.note.dom.impl.note.T_removeNote;

@Mixin
public class NotableLinkForDemoObject_removeNote extends T_removeNote<DemoObject> {
    public NotableLinkForDemoObject_removeNote(final DemoObject notable) {
        super(notable);
    }
}
