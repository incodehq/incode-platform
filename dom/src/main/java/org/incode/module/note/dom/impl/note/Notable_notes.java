/*
 *
 *  Copyright 2015 incode.org
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
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
package org.incode.module.note.dom.impl.note;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.note.dom.api.notable.Notable;

@Mixin
public class Notable_notes {

    //region  > (injected)
    @Inject
    NoteRepository noteRepository;
    //endregion

    //region > constructor
    private final Notable notable;
    public Notable_notes(final Notable notable) {
        this.notable = notable;
    }

    public Notable getNotable() {
        return notable;
    }
    //endregion



    public static class DomainEvent extends Notable.ActionDomainEvent<Notable_notes> { } { }
    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            named = "Notes", // regression in isis 1.11.x requires this to be specified
            defaultView = "table"
    )
    public List<Note> $$() {
        return noteRepository.findByNotable(this.notable);
    }

}
