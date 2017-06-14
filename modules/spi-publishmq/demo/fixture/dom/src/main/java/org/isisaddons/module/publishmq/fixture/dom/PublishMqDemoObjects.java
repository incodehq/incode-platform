package org.isisaddons.module.publishmq.fixture.dom;

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
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isispublishmqDemo.PublishMqDemoObjects",
        repositoryFor = PublishMqDemoObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class PublishMqDemoObjects {


    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<PublishMqDemoObject> listAll() {
        return container.allInstances(PublishMqDemoObject.class);
    }

    //endregion

    //region > create (action)
    
    @MemberOrder(sequence = "2")
    public PublishMqDemoObject create(
            final @ParameterLayout(named = "Name") String name) {
        final PublishMqDemoObject obj = container.newTransientInstance(PublishMqDemoObject.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            publishing = Publishing.ENABLED
    )
    @MemberOrder(sequence = "3")
    public List<PublishMqDemoObject> incrementAll() {
        final List<PublishMqDemoObject> publishMqDemoObjects = listAll();
        for (PublishMqDemoObject publishMqDemoObject : publishMqDemoObjects) {
            publishMqDemoObject.incrementCountInBulk();
        }
        return publishMqDemoObjects;
    }

    //endregion


    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion

}
