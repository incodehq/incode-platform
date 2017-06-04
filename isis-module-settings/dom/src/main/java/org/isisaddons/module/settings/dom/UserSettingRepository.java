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

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.isisaddons.module.settings.dom.jdo.UserSettingJdo;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = UserSettingJdo.class
)
public class UserSettingRepository {

    //region > find

    @Programmatic
    public UserSetting find(
            final String user,
            final String key) {
        return queryResultsCache.execute(new Callable<UserSetting>() {
            @Override
            public UserSetting call() throws Exception {
                return doFind(user, key);
            }
        }, UserSettingRepository.class, "find", user, key);
    }

    protected UserSettingJdo doFind(final String user, final String key) {
        return repositoryService.firstMatch(
                new QueryDefault<>(UserSettingJdo.class,
                        "findByUserAndKey",
                        "user",user,
                        "key", key));
    }
    //endregion

    //region > listAll

    @Programmatic
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<UserSetting> listAllFor(
            final String user) {
        return (List)repositoryService.allMatches(
                new QueryDefault<>(UserSettingJdo.class,
                        "findByUser",
                        "user", user));
    }

    //endregion

    //region > listAll

    @Programmatic
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<UserSetting> listAll() {
        return (List)repositoryService.allMatches(
                new QueryDefault<>(UserSettingJdo.class,
                        "findAll"));
    }

    //endregion

    //region > newString

    @Programmatic
    public UserSettingJdo newString(
            final String user,
            final String key,
            final String description,
            final String value) {
        return newSetting(user, key, description, SettingType.STRING, value);
    }

    //endregion

    //region > newInt

    @Programmatic
    public UserSettingJdo newInt(
            final String user,
            final String key,
            final String description,
            final Integer value) {
        return newSetting(user, key, description, SettingType.INT, value.toString());
    }

    //endregion

    //region > newLong

    @Programmatic
    public UserSettingJdo newLong(
            final String user,
            final String key,
            final String description,
            final Long value) {
        return newSetting(user, key, description, SettingType.LONG, value.toString());
    }

    //endregion

    //region > newLocalDate

    @Programmatic
    public UserSettingJdo newLocalDate(
            final String user,
            final String key,
            final String description,
            final LocalDate value) {
        return newSetting(user, key, description, SettingType.LOCAL_DATE, value.toString(SettingAbstract.DATE_FORMATTER));
    }

    //endregion

    //region > newBoolean

    @Programmatic
    public UserSettingJdo newBoolean(
            final String user,
            final String key,
            final String description,
            final Boolean value) {
        return newSetting(user, key, description, SettingType.BOOLEAN, Boolean.toString(value != null && value));
    }

    //endregion

    //region > helpers
    private UserSettingJdo newSetting(
            final String user,
            final String key, final String description, final SettingType settingType, final String valueRaw) {
        final UserSettingJdo setting = repositoryService.instantiate(UserSettingJdo.class);
        setting.setUser(user);
        setting.setKey(key);
        setting.setType(settingType);
        setting.setDescription(description);
        setting.setValueRaw(valueRaw);
        repositoryService.persist(setting);
        return setting;
    }
    //endregion

    //region > injected
    @Inject
    RepositoryService repositoryService;
    @Inject
    QueryResultsCache queryResultsCache;
    //endregion
}
