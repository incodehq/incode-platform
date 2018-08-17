package org.incode.module.settings.dom.jdo;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

import org.incode.module.settings.SettingsModule;
import org.incode.module.settings.dom.ApplicationSetting;

@Mixin
public class ApplicationSetting_next extends AbstractService {


    //region > constructor
    private final ApplicationSetting current;

    public ApplicationSetting_next(final ApplicationSetting current) {
        this.current = current;
    }
    //endregion


    public static class ActionDomainEvent
            extends SettingsModule.ActionDomainEvent<ApplicationSetting_next> { }

    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION,
            cssClassFa = "fa-step-forward",
            cssClassFaPosition = ActionLayout.CssClassFaPosition.RIGHT
    )
    public ApplicationSetting $$() {
        return firstMatch(
                new QueryDefault<>(ApplicationSettingJdo.class,
                        "findNext",
                        "key", current.getKey()));
    }

    public String disable$$() {
        return $$() == null? "No more settings": null;
    }

}
