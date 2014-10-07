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

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@ObjectType("COMMAND_ANNOTATED_OBJECT")
@Bookmarkable
public class SomeCommandAnnotatedObject implements Comparable<SomeCommandAnnotatedObject> {

    //region > name (property)

    private String name;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //endregion

    //region > changeName (action)

    @ActionSemantics(ActionSemantics.Of.IDEMPOTENT)
    @Command
    public SomeCommandAnnotatedObject changeName(final @Named("New name") String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeName() {
        return getName();
    }

    //endregion

    //region > changeNameWithSafeSemantics (action)

    @DescribedAs("'Mistakenly' annotated as being invoked with safe semantics, not annotated as a Command; should persist anyway")
    @ActionSemantics(ActionSemantics.Of.SAFE) // obviously, a mistake
    public SomeCommandAnnotatedObject changeNameWithSafeSemantics(final @Named("New name") String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeNameWithSafeSemantics() {
        return getName();
    }

    //endregion

    //region > changeNameExplicitlyInBackground (action)

    @Named("Schedule")
    @ActionSemantics(ActionSemantics.Of.IDEMPOTENT)
    @Command
    public void changeNameExplicitlyInBackground(final @Named("New name") String newName) {
        backgroundService.execute(this).changeName(newName);
    }

    public String default0ChangeNameExplicitlyInBackground() {
        return getName();
    }

    //endregion

    //region > changeNameImplicitlyInBackground (action)

    @Named("Schedule implicitly")
    @ActionSemantics(ActionSemantics.Of.IDEMPOTENT)
    @Command(executeIn = Command.ExecuteIn.BACKGROUND)
    public SomeCommandAnnotatedObject changeNameImplicitlyInBackground(final @Named("New name") String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeNameImplicitlyInBackground() {
        return getName();
    }

    //endregion

    //region > changeNameCommandNotPersisted (action)

    @Named("Change (not persisted)")
    @ActionSemantics(ActionSemantics.Of.IDEMPOTENT)
    @Command(persistence = Command.Persistence.NOT_PERSISTED)
    public SomeCommandAnnotatedObject changeNameCommandNotPersisted(final @Named("New name") String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeNameCommandNotPersisted() {
        return getName();
    }

    //endregion

    //region > compareTo

    @Override
    public int compareTo(SomeCommandAnnotatedObject other) {
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
