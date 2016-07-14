package org.incode.module.alias.fixture.dom.apptenancy;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.alias.dom.impl.alias.Alias;
import org.incode.module.alias.dom.spi.aliastype.ApplicationTenancyRepository;
import org.incode.module.alias.fixture.dom.aliastype.AliasTypeDemoEnum;

/**
 * Mandatory implementation of the {@link ApplicationTenancyRepository} SPI.
 */
@DomainService(
    nature = NatureOfService.DOMAIN,
    menuOrder = "1" // override default impl.
)
public class DemoUiSubscriber extends AbstractSubscriber {

    @Subscribe
    public void on(Alias.TitleUiEvent ev) {
        Alias alias = ev.getSource();
        if(isType(alias, AliasTypeDemoEnum.DOCUMENT_MANAGEMENT)) {
            ev.setTitle("DocMgmt  [" + alias.getAliasTypeId() + "] " + alias.getReference());
        } else {
            titleSubscriber.on(ev);
        }
    }

    @Subscribe
    public void on(Alias.IconUiEvent ev) {
        Alias alias = ev.getSource();
        if(isType(alias, AliasTypeDemoEnum.DOCUMENT_MANAGEMENT)) {
            ev.setIconName("Alias-docMgmt");
        } else if (isType(alias, AliasTypeDemoEnum.GENERAL_LEDGER)) {
            ev.setIconName("Alias-GL");
        } else {
            iconSubscriber.on(ev);
        }
    }

    @Subscribe
    public void on(Alias.CssClassUiEvent ev) {
        Alias alias = ev.getSource();
        ev.setCssClass("Alias" + alias.getAtPath().replace("/", "-"));
    }

    static boolean isType(final Alias alias, final AliasTypeDemoEnum aliasType) {
        return alias.getAliasTypeId().equals(aliasType.getId());
    }

    @Inject
    Alias.TitleSubscriber titleSubscriber;
    @Inject
    Alias.IconSubscriber iconSubscriber;
    @Inject
    Alias.CssClassSubscriber cssClassSubscriber;

}
