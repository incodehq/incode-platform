package org.incode.module.alias.fixture.app.ui;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.alias.dom.impl.Alias;
import org.incode.module.alias.fixture.app.spiimpl.aliastype.AliasTypeDemoEnum;

@DomainService(nature = NatureOfService.DOMAIN )
public class DemoUiSubscriber extends AbstractSubscriber {

    @Subscribe
    public void on(Alias.TitleUiEvent ev) {
        Alias alias = ev.getSource();
        if(isType(alias, AliasTypeDemoEnum.DOCUMENT_MANAGEMENT)) {
            ev.setTitle("DocMgmt  [" + alias.getAliasTypeId() + "] " + alias.getReference());
        }
    }

    @Subscribe
    public void on(Alias.IconUiEvent ev) {
        Alias alias = ev.getSource();
        if(isType(alias, AliasTypeDemoEnum.DOCUMENT_MANAGEMENT)) {
            ev.setIconName("Alias-docMgmt");
        } else if (isType(alias, AliasTypeDemoEnum.GENERAL_LEDGER)) {
            ev.setIconName("Alias-GL");
        }
    }

    @Subscribe
    public void on(Alias.CssClassUiEvent ev) {
        Alias alias = ev.getSource();
        ev.setCssClass("Alias" + alias.getAtPath().replace("/", "-"));
    }

    private static boolean isType(final Alias alias, final AliasTypeDemoEnum aliasType) {
        return alias.getAliasTypeId().equals(aliasType.getId());
    }
}
