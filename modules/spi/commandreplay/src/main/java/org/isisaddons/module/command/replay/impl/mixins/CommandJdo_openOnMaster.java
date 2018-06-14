package org.isisaddons.module.command.replay.impl.mixins;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.bookmark.BookmarkService2;
import org.apache.isis.core.metamodel.services.configinternal.ConfigurationServiceInternal;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.replay.impl.ConfigurationKeys;

@Mixin(method = "act")
public class CommandJdo_openOnMaster<T> {

    private final CommandJdo commandJdo;
    public CommandJdo_openOnMaster(CommandJdo commandJdo) {
        this.commandJdo = commandJdo;
    }

    public static class ActionDomainEvent extends CommandModule.ActionDomainEvent<CommandJdo_openOnMaster> { }
    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "transactionId", sequence = "1")
    public URL act() {
        final String baseUrlPrefix = lookupBaseUrlPrefix();
        final String urlSuffix = bookmarkService2.bookmarkFor(commandJdo).toString();

        try {
            return new URL(baseUrlPrefix + urlSuffix);
        } catch (MalformedURLException e) {
            throw new ApplicationException(e);
        }
    }

    public boolean hideAct() {
        return lookupBaseUrlPrefix() == null;
    }

    private String lookupBaseUrlPrefix() {
        String masterBaseUrlEndUser = configurationServiceInternal.asMap()
                .get(ConfigurationKeys.MASTER_BASE_URL_END_USER_URL_ISIS_KEY);
        if(masterBaseUrlEndUser == null) {
            return null;
        }
        if(!masterBaseUrlEndUser.endsWith("/")) {
            masterBaseUrlEndUser = masterBaseUrlEndUser + "/";
        }
        return masterBaseUrlEndUser + "wicket/entity/";
    }

    @Inject
    ConfigurationServiceInternal configurationServiceInternal;
    @Inject
    BookmarkService2 bookmarkService2;

}
