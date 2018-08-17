package org.incode.module.settings.dom;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.settings.dom.jdo.UserSettingsServiceJdo;

/**
 * An implementation of {@link UserSettingsService} that persists settings
 * as entities into a JDO-backed database.
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Settings",
        menuOrder = "400.2",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY
)
public class UserSettingMenu extends UserSettingsServiceJdo {


}
