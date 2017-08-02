package org.incode.domainapp.example.dom.lib.fakedata.demo;

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
