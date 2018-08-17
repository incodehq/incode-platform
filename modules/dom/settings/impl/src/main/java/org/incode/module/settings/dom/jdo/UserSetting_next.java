package org.incode.module.settings.dom.jdo;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

import org.incode.module.settings.SettingsModule;
import org.incode.module.settings.dom.UserSetting;

@Mixin
public class UserSetting_next extends AbstractService {

    public static class ActionDomainEvent
            extends SettingsModule.ActionDomainEvent<UserSetting_next> { }

    //region > constructors

    private final UserSetting current;
    public UserSetting_next(final UserSetting current) {
        this.current = current;
    }

    //endregion


    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION,
            cssClassFa = "fa-step-forward",
            cssClassFaPosition = ActionLayout.CssClassFaPosition.RIGHT
    )
    public UserSetting $$() {
        return firstMatch(
                new QueryDefault<>(UserSettingJdo.class,
                        "findNext",
                        "user", current.getUser(),
                        "key", current.getKey()));
    }

    public String disable$$() {
        return $$() == null? "No more settings": null;
    }


}
