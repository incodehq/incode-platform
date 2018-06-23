package org.incode.example.alias.demo.examples.note.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.alias.demo.shared.dom.DemoObject;
import org.incode.example.note.dom.impl.note.T_notes;

@Mixin
public class NotableLinkForDemoObject_notes extends T_notes<DemoObject> {
    public NotableLinkForDemoObject_notes(final DemoObject notable) {
        super(notable);
    }
}
