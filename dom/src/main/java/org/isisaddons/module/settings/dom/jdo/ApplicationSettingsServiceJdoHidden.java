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
import org.isisaddons.module.settings.dom.ApplicationSetting;
import org.joda.time.LocalDate;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Where;

/**
 * An implementation intended to be hidden in the UI, and delegated to by other services.
 *
 * @deprecated - instead use {@link org.isisaddons.module.settings.dom.jdo.ApplicationSettingsServiceJdo}.  If the action items need to be hidden, use either security or a vetoing subscriber.
 */
@Deprecated
public class ApplicationSettingsServiceJdoHidden extends ApplicationSettingsServiceJdo {


    @Action(hidden = Where.EVERYWHERE)
    @Override
    public ApplicationSetting find(final String key) {
        return super.find(key);
    }

    // //////////////////////////////////////

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public List<ApplicationSetting> listAll() {
        return super.listAll();
    }

    // //////////////////////////////////////

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public ApplicationSetting newString(final String key, final String description, final String value) {
        return super.newString(key, description, value);
    }

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public ApplicationSettingJdo newInt(final String key, final String description, final Integer value) {
        return super.newInt(key, description, value);
    }

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public ApplicationSettingJdo newLong(final String key, final String description, final Long value) {
        return super.newLong(key, description, value);
    }

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public ApplicationSettingJdo newLocalDate(final String key, final String description, final LocalDate value) {
        return super.newLocalDate(key, description, value);
    }

    @Action(hidden = Where.EVERYWHERE)
    @Override
    public ApplicationSettingJdo newBoolean(final String key, final String description, final Boolean value) {
        return super.newBoolean(key, description, value);
    }

}
