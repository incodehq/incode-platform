#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.note.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.example.note.dom.impl.note.T_notes;

@Mixin
public class NotableLinkForDemoObject_notes extends T_notes<DemoObject> {
    public NotableLinkForDemoObject_notes(final DemoObject notable) {
        super(notable);
    }
}
