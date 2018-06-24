package org.incode.examples.note.demo.usage.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.examples.note.demo.shared.demo.dom.DemoObject;
import org.incode.example.note.dom.impl.note.T_notes;

@Mixin
public class NotableLinkForDemoObject_notes extends T_notes<DemoObject> {
    public NotableLinkForDemoObject_notes(final DemoObject notable) {
        super(notable);
    }
}
