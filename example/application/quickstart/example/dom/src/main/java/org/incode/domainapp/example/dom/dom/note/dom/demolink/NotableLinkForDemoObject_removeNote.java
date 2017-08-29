package org.incode.domainapp.example.dom.dom.note.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.module.note.dom.impl.note.T_removeNote;

@Mixin
public class NotableLinkForDemoObject_removeNote extends T_removeNote<DemoObject> {
    public NotableLinkForDemoObject_removeNote(final DemoObject notable) {
        super(notable);
    }
}
