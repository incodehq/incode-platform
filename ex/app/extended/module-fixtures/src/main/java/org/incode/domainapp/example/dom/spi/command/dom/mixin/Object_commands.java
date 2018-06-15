package org.incode.domainapp.example.dom.spi.command.dom.mixin;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.isisaddons.module.command.dom.BackgroundCommandServiceJdoRepository;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;

@Mixin
public class Object_commands {

    private final Object entity;

    public Object_commands(Object entity) {
        this.entity = entity;
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    public List<CommandJdo> $$() {
        final Bookmark bookmark = bookmarkService.bookmarkFor(entity);
        return commandServiceJdoRepository.findByTargetAndFromAndTo(bookmark, null, null);
    }

    public boolean hide$$() {
        // to avoid confusion with what the command module itself contributes...
        return entity instanceof HasTransactionId;
    }

    // //////////////////////////////////////

    @Inject
    private BookmarkService bookmarkService;

    @Inject
    private CommandServiceJdoRepository commandServiceJdoRepository;

    @Inject
    private BackgroundCommandServiceJdoRepository backgroundCommandServiceJdoRepository;

}
