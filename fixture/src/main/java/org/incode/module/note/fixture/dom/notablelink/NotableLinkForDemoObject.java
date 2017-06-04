/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.note.fixture.dom.notablelink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.note.dom.impl.notablelink.NotableLink;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;
import org.incode.module.note.dom.impl.note.T_addNote;
import org.incode.module.note.dom.impl.note.T_notes;
import org.incode.module.note.dom.impl.note.T_removeNote;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE, schema ="incodeNoteDemo")
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "incodeNoteDemo.NotableLinkForDemoObject"
)
public class NotableLinkForDemoObject extends NotableLink {


    //region > demoObject (property)
    private NoteDemoObject demoObject;

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    public NoteDemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final NoteDemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion

    //region > notable (hook, derived)

    @Override
    public Object getNotable() {
        return getDemoObject();
    }

    @Override
    protected void setNotable(final Object object) {
        setDemoObject((NoteDemoObject) object);
    }

    //endregion

    //region > SubtypeProvider SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends NotableLinkRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(NoteDemoObject.class, NotableLinkForDemoObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _notes extends T_notes<NoteDemoObject> {
        public _notes(final NoteDemoObject notable) {
            super(notable);
        }
    }

    @Mixin
    public static class _addNote extends T_addNote<NoteDemoObject> {
        public _addNote(final NoteDemoObject notable) {
            super(notable);
        }
    }

    @Mixin
    public static class _removeNote extends T_removeNote<NoteDemoObject> {
        public _removeNote(final NoteDemoObject notable) {
            super(notable);
        }
    }

    //endregion


}
