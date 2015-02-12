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
package org.isisaddons.module.settings.dom.jdo;

import java.util.List;
import org.isisaddons.module.settings.dom.UserSetting;
import org.joda.time.LocalDate;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Where;

/**
 * An implementation intended to be hidden in the UI, and delegated to by other services.
 *
 * @deprecated - instead use {@link org.isisaddons.module.settings.dom.jdo.ApplicationSettingsServiceJdo}.  If the action items need to be hidden, use either security or a vetoing subscriber.
 */
@Deprecated
public class UserSettingsServiceJdoHidden extends UserSettingsServiceJdo {

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public UserSetting find(final String user, final String key) {
        return super.find(user, key);
    }

    // //////////////////////////////////////

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public List<UserSetting> listAll() {
        return super.listAll();
    }

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public List<UserSetting> listAllFor(final String user) {
        return super.listAllFor(user);
    }

    // //////////////////////////////////////

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public UserSettingJdo newString(final String user, final String key, final String description, final String value) {
        return super.newString(user, key, description, value);
    }

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public UserSettingJdo newInt(final String user, final String key, final String description, final Integer value) {
        return super.newInt(user, key, description, value);
    }

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public UserSettingJdo newLong(final String user, final String key, final String description, final Long value) {
        return super.newLong(user, key, description, value);
    }

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public UserSettingJdo newLocalDate(final String user, final String key, final String description, final LocalDate value) {
        return super.newLocalDate(user, key, description, value);
    }

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public UserSettingJdo newBoolean(final String user, final String key, final String description, final Boolean value) {
        return super.newBoolean(user, key, description, value);
    }

    

}
