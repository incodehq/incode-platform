/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.module.command.fixture.dom;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.background.BackgroundService;
import org.apache.isis.applib.util.ObjectContracts;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
        objectType = "COMMAND_ANNOTATED_OBJECT"
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class SomeCommandAnnotatedObject implements Comparable<SomeCommandAnnotatedObject> {

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    @Getter @Setter
    private String name;


    //region > changeName (action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
    public SomeCommandAnnotatedObject changeName(
            @ParameterLayout(named = "New name")
            final String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeName() {
        return getName();
    }

    //endregion

    //region > changeNameWithSafeSemantics (action)

    @Action(
            semantics = SemanticsOf.SAFE // obviously, a mistake
    )
    @ActionLayout(
            describedAs = "'Mistakenly' annotated as being invoked with safe semantics, not annotated as a Command; should persist anyway"
    )
    public SomeCommandAnnotatedObject changeNameWithSafeSemantics(
            @ParameterLayout(named = "New name")
            final String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeNameWithSafeSemantics() {
        return getName();
    }

    //endregion

    //region > changeNameExplicitlyInBackground (action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
    @ActionLayout(
            named = "Schedule"
    )
    public void changeNameExplicitlyInBackground(
            @ParameterLayout(named = "New name")
            final String newName) {
        backgroundService.execute(this).changeName(newName);
    }

    public String default0ChangeNameExplicitlyInBackground() {
        return getName();
    }

    //endregion

    //region > changeNameImplicitlyInBackground (action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED,
            commandExecuteIn = CommandExecuteIn.BACKGROUND
    )
    @ActionLayout(
            named = "Schedule implicitly"
    )
    public SomeCommandAnnotatedObject changeNameImplicitlyInBackground(
            @ParameterLayout(named = "New name")
            final String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeNameImplicitlyInBackground() {
        return getName();
    }

    //endregion

    //region > changeNameCommandNotPersisted (action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED,
            commandPersistence = CommandPersistence.NOT_PERSISTED
    )
    @ActionLayout(
            named = "Change (not persisted)"
    )
    public SomeCommandAnnotatedObject changeNameCommandNotPersisted(
            @ParameterLayout(named = "New name")
            final String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeNameCommandNotPersisted() {
        return getName();
    }

    //endregion

    //region > compareTo

    @Override
    public int compareTo(final SomeCommandAnnotatedObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private BackgroundService backgroundService;
    //endregion

}
