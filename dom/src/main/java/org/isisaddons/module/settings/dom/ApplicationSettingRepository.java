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

import org.isisaddons.module.settings.dom.jdo.ApplicationSettingJdo;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = ApplicationSettingJdo.class
)
public class ApplicationSettingRepository {

    //region > find
    @Programmatic
    public ApplicationSetting find(final String key) {
        return queryResultsCache.execute(new Callable<ApplicationSetting>() {
            @Override
            public ApplicationSetting call() throws Exception {
                return doFind(key);
            }
        }, ApplicationSettingRepository.class, "find", key);
    }

    protected ApplicationSettingJdo doFind(final String key) {
        return repositoryService.firstMatch(
                new QueryDefault<>(ApplicationSettingJdo.class,
                        "findByKey",
                        "key", key));
    }

    //endregion

    //region > listAll

    @Programmatic
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<ApplicationSetting> listAll() {
        return (List)repositoryService.allMatches(
                new QueryDefault<>(ApplicationSettingJdo.class,
                        "findAll"));
    }
    //endregion

    //region > newString
    @Programmatic
    public ApplicationSetting newString(
            final String key,
            final String description,
            final String value) {
        return newSetting(key, description, SettingType.STRING, value);
    }
    //endregion

    //region > newInt

    @Programmatic
    public ApplicationSettingJdo newInt(
            final String key,
            final String description,
            final Integer value) {
        return newSetting(key, description, SettingType.INT, value.toString());
    }

    //endregion

    //region > newLong

    @Programmatic
    public ApplicationSettingJdo newLong(
            final String key,
            final String description,
            final Long value) {
        return newSetting(key, description, SettingType.LONG, value.toString());
    }

    //endregion

    //region > newLocalDate

    @Programmatic
    public ApplicationSettingJdo newLocalDate(
            final String key,
            final String description,
            final LocalDate value) {
        return newSetting(key, description, SettingType.LOCAL_DATE, value.toString(SettingAbstract.DATE_FORMATTER));
    }

    //endregion

    //region > newBoolean

    @Programmatic
    public ApplicationSettingJdo newBoolean(
            final String key,
            final String description,
            final Boolean value) {
        return newSetting(key, description, SettingType.BOOLEAN, Boolean.toString(value != null && value));
    }

    //endregion

    //region > helpers
    private ApplicationSettingJdo newSetting(
            final String key, final String description, final SettingType settingType, final String valueRaw) {
        final ApplicationSettingJdo setting = repositoryService.instantiate(ApplicationSettingJdo.class);
        setting.setKey(key);
        setting.setDescription(description);
        setting.setValueRaw(valueRaw);
        setting.setType(settingType);
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
