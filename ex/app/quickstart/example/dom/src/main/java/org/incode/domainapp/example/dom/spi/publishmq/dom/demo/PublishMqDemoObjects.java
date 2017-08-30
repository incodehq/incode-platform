package org.incode.domainapp.example.dom.spi.publishmq.dom.demo;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleSpiPublishMq.PublishMqDemoObjects",
        repositoryFor = PublishMqDemoObject.class
)
@DomainServiceLayout(
        named = "SPI Modules",
        menuOrder = "50.3"
)
public class PublishMqDemoObjects {



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<PublishMqDemoObject> listAllPublishMqDemoObjects() {
        return container.allInstances(PublishMqDemoObject.class);
    }



    @MemberOrder(sequence = "2")
    public PublishMqDemoObject createPublishMqDemoObject(
            final String name) {
        final PublishMqDemoObject obj = new PublishMqDemoObject(name, null, null);
        container.persistIfNotAlready(obj);
        return obj;
    }


    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            publishing = Publishing.ENABLED
    )
    @MemberOrder(sequence = "3")
    public List<PublishMqDemoObject> incrementAllPublishMqDemoObjects() {
        final List<PublishMqDemoObject> publishMqDemoObjects = listAllPublishMqDemoObjects();
        for (PublishMqDemoObject publishMqDemoObject : publishMqDemoObjects) {
            publishMqDemoObject.incrementCountInBulk();
        }
        return publishMqDemoObjects;
    }



    @javax.inject.Inject
    DomainObjectContainer container;


}
