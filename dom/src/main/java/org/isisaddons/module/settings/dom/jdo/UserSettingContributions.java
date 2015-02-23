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
import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.UserSetting;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

/**
 * An implementation of {@link org.isisaddons.module.settings.dom.ApplicationSettingsService} that persists settings
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
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class UserSettingContributions extends AbstractService {

    public static abstract class PropertyDomainEvent<T> extends SettingsModule.PropertyDomainEvent<UserSettingContributions, T> {
        public PropertyDomainEvent(final UserSettingContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final UserSettingContributions source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends SettingsModule.CollectionDomainEvent<UserSettingContributions, T> {
        public CollectionDomainEvent(final UserSettingContributions source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final UserSettingContributions source, final Identifier identifier, final Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends SettingsModule.ActionDomainEvent<UserSettingContributions> {
        public ActionDomainEvent(final UserSettingContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final UserSettingContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final UserSettingContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class FindNextDomainEvent extends ActionDomainEvent {
        public FindNextDomainEvent(final UserSettingContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = FindNextDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public UserSetting findNext(final UserSetting current) {
        return firstMatch(
                new QueryDefault<>(UserSettingJdo.class,
                        "findNext",
                        "user", current.getUser(),
                        "key", current.getKey()));
    }

    public String disableFindNext(final UserSetting current) {
        return findNext(current) == null? "No more settings": null;
    }

    // //////////////////////////////////////

    public static class FindPreviousDomainEvent extends ActionDomainEvent {
        public FindPreviousDomainEvent(final UserSettingContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = FindNextDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public UserSetting findPrevious(final UserSetting current) {
        // bit of a workaround; for some reason ORDER BY ... DESC seems to return in ascending order
        final List<UserSettingJdo> settings = allMatches(
                new QueryDefault<>(UserSettingJdo.class,
                        "findPrevious",
                        "user", current.getUser(),
                        "key", current.getKey()));
        final int size = settings.size();
        return size != 0? settings.get(size-1): null;
    }

    public String disableFindPrevious(final UserSetting current) {
        return findPrevious(current) == null? "No more settings": null;
    }

}
