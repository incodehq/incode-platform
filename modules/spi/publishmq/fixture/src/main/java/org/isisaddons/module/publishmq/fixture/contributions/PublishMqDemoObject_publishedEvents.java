package org.isisaddons.module.publishmq.fixture.contributions;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService2;

import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventRepository;
import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.dom.PublishMqDemoObject;

@Mixin
public class PublishMqDemoObject_publishedEvents {

    private final PublishMqDemoObject publishMqDemoObject;

    public PublishMqDemoObject_publishedEvents(PublishMqDemoObject publishMqDemoObject) {
        this.publishMqDemoObject = publishMqDemoObject;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    public List<PublishedEvent> $$() {
        final Bookmark target = bookmarkService2.bookmarkFor(publishMqDemoObject);
        return publishedEventRepository.findRecentByTarget(target);
    }

    @Inject
    private PublishedEventRepository publishedEventRepository;

    @Inject
    private BookmarkService2 bookmarkService2;

}
