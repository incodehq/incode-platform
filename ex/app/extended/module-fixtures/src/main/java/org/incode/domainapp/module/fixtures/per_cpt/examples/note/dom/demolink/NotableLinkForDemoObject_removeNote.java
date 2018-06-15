package org.incode.domainapp.module.fixtures.per_cpt.examples.note.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.note.dom.impl.note.T_removeNote;

@Mixin
public class NotableLinkForDemoObject_removeNote extends T_removeNote<DemoObject> {
    public NotableLinkForDemoObject_removeNote(final DemoObject notable) {
        super(notable);
    }
}
