package org.incode.domainapp.example.dom.demo.dom.demowithall;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleLibFakeData.DemoObjectWithAllMenu"
)
@DomainServiceLayout(
        named = "Demo",
        menuOrder = "10.2"
)
public class DemoObjectWithAllMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DemoObjectWithAll> listAllDemoObjectsWithAll() {
        return container.allInstances(DemoObjectWithAll.class);
    }


    @MemberOrder(sequence = "2")
    public DemoObjectWithAll createDemoObjectWithAll(
            final String name,
            final boolean someBoolean,
            final char someChar,
            final byte someByte,
            final short someShort,
            final int someInt,
            final long someLong,
            final float someFloat,
            final double someDouble) {
        final DemoObjectWithAll obj = container.newTransientInstance(DemoObjectWithAll.class);
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


    @javax.inject.Inject 
    DomainObjectContainer container;

}
