package org.incode.domainapp.example.dom.dom.note.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.module.note.dom.impl.note.T_addNote;

@Mixin
public class NotableLinkForDemoObject_addNote extends T_addNote<DemoObject> {
    public NotableLinkForDemoObject_addNote(final DemoObject notable) {
        super(notable);
    }
}
