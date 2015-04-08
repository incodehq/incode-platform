/*
 *  Copyright 2014 Dan Haywood
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
package org.isisaddons.module.fakedata.fixture.dom;

import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = FakeDataDemoObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class FakeDataDemoObjects {

    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<FakeDataDemoObject> listAll() {
        return container.allInstances(FakeDataDemoObject.class);
    }

    //endregion

    //region > create (action)

    @MemberOrder(sequence = "2")
    public FakeDataDemoObject create(
            final @ParameterLayout(named = "Name") String name,
            final @ParameterLayout(named = "Some Boolean") boolean someBoolean,
            final @ParameterLayout(named = "Some Char") char someChar,
            final @ParameterLayout(named = "Some Byte") byte someByte,
            final @ParameterLayout(named = "Some Short") short someShort,
            final @ParameterLayout(named = "Some Int") int someInt,
            final @ParameterLayout(named = "Some Long") long someLong,
            final @ParameterLayout(named = "Some Float") float someFloat,
            final @ParameterLayout(named = "Some Double") double someDouble) {
        final FakeDataDemoObject obj = container.newTransientInstance(FakeDataDemoObject.class);
        obj.setName(name);
        obj.setSomeBoolean(someBoolean);
        obj.setSomeChar(someChar);
        obj.setSomeByte(someByte);
        obj.setSomeShort(someShort);
        obj.setSomeInt(someInt);
        obj.setSomeLong(someLong);
        obj.setSomeFloat(someFloat);
        obj.setSomeDouble(someDouble);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > injected services
    // //////////////////////////////////////

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion

}
