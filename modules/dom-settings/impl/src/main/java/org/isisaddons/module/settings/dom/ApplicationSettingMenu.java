package org.isisaddons.module.settings.dom;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;

import org.isisaddons.module.settings.dom.jdo.ApplicationSettingsServiceJdo;

/**
 * An implementation of {@link ApplicationSettingsService} that persists settings
 * as entities into a JDO-backed database.
 *
 * <p>
 *     This service is automatically registered and provides UI-visible actions.  If necessary these can be hidden
 *     either using security or by writing a subscriber to veto their visibility:
 * </p>
 * <pre>
 * &#64;DomainService(nature = NatureOfService.DOMAIN)
 * public class HideIsisModuleSettingsModuleMenus {
 *
 *   &#64;Programmatic &#64;PostConstruct
 *   public void postConstruct() { eventBusService.register(this); }
 *   &#64;Programmatic &#64;PostConstruct
 *   public void preDestroy() { eventBusService.unregister(this); }
 *
 *   &#64;Programmatic &#64;Subscribe
 *   public void on(final SettingsModule.ActionDomainEvent<?> event) {
 *     event.hide();
 *   }
 *
 *   &#64;Inject
 *   private EventBusService eventBusService;
 * }
 * </pre>
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Settings",
        menuOrder = "400.1",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY
)
public class ApplicationSettingMenu extends ApplicationSettingsServiceJdo implements ApplicationSettingsServiceRW {


}
