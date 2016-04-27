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

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.ApplicationSetting;

@Mixin
public class ApplicationSetting_previous_next extends AbstractService {

    //region > domain events

    public static abstract class PropertyDomainEvent<T>
            extends SettingsModule.PropertyDomainEvent<ApplicationSetting_previous_next, T> { }

    public static abstract class CollectionDomainEvent<T>
            extends SettingsModule.CollectionDomainEvent<ApplicationSetting_previous_next, T> {
    }

    public static abstract class ActionDomainEvent
            extends SettingsModule.ActionDomainEvent<ApplicationSetting_previous_next> { }

    //endregion

    //region > constructor
    private final ApplicationSetting current;

    public ApplicationSetting_previous_next(final ApplicationSetting current) {
        this.current = current;
    }
    //endregion

    //region > findNext (action)

    public static class FindNextDomainEvent extends ActionDomainEvent {
    }

    @Action(
            domainEvent = FindNextDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public ApplicationSetting findNext() {
        return firstMatch(
                new QueryDefault<>(ApplicationSettingJdo.class,
                        "findNext",
                        "key", current.getKey()));
    }

    public String disableFindNext() {
        return findNext() == null? "No more settings": null;
    }
    //endregion

    //region > findPrevious (action)

    public static class FindPreviousDomainEvent extends ActionDomainEvent {
    }

    @Action(
            domainEvent = FindNextDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public ApplicationSetting findPrevious() {
        // bit of a workaround; for some reason ORDER BY ... DESC seems to return in ascending order
        final List<ApplicationSettingJdo> settings = allMatches(
                new QueryDefault<>(ApplicationSettingJdo.class,
                        "findPrevious",
                        "key", current.getKey()));
        final int size = settings.size();
        return size != 0? settings.get(size-1): null;
    }

    public String disableFindPrevious() {
        return findPrevious() == null? "No more settings": null;
    }
    //endregion

}
