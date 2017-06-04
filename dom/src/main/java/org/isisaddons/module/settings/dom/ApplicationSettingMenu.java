/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
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
