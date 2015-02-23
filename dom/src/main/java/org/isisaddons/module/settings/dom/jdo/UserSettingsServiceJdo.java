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
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.SettingAbstract;
import org.isisaddons.module.settings.dom.SettingType;
import org.isisaddons.module.settings.dom.UserSetting;
import org.isisaddons.module.settings.dom.UserSettingsService;
import org.isisaddons.module.settings.dom.UserSettingsServiceRW;
import org.joda.time.LocalDate;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

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
public class UserSettingsServiceJdo extends AbstractService implements UserSettingsServiceRW {

    public static abstract class PropertyDomainEvent<T> extends SettingsModule.PropertyDomainEvent<UserSettingsServiceJdo, T> {
        public PropertyDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends SettingsModule.CollectionDomainEvent<UserSettingsServiceJdo, T> {
        public CollectionDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends SettingsModule.ActionDomainEvent<UserSettingsServiceJdo> {
        public ActionDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class FindDomainEvent extends ActionDomainEvent {
        public FindDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = FindDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            named = "Find User Setting",
            cssClassFa = "search"
    )
    @MemberOrder(sequence = "2.1")
    @Override
    public UserSetting find(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key) {
        return firstMatch(
                new QueryDefault<>(UserSettingJdo.class,
                        "findByUserAndKey", 
                        "user",user,
                        "key", key));
    }


    // //////////////////////////////////////

    public static class ListAllForDomainEvent extends ActionDomainEvent {
        public ListAllForDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = ListAllForDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            named = "List All Settings For User",
            cssClassFa = "user"
    )
    @MemberOrder(sequence="2.2")
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<UserSetting> listAllFor(
            @ParameterLayout(named="User")
            final String user) {
        return (List)allMatches(
                new QueryDefault<>(UserSettingJdo.class,
                        "findByUser", 
                        "user", user));
    }
    public List<String> choices0ListAllFor() {
        return existingUsers();
    }

    private List<String> existingUsers() {
        final List<UserSetting> listAll = listAll();
        return Lists.newArrayList(Sets.newTreeSet(Iterables.transform(listAll, GET_USER)));
    }

    private final static Function<UserSetting, String> GET_USER = new Function<UserSetting, String>() {
        public String apply(final UserSetting input) {
            return input.getUser();
        }
    };

    // //////////////////////////////////////

    public static class ListAllDomainEvent extends ActionDomainEvent {
        public ListAllDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = ListAllDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            named = "List All User Settings",
            cssClassFa = "list"
    )
    @MemberOrder(sequence="2.3")
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<UserSetting> listAll() {
        return (List)allMatches(
                new QueryDefault<>(UserSettingJdo.class,
                        "findAll"));
    }


    // //////////////////////////////////////

    public static class NewStringDomainEvent extends ActionDomainEvent {
        public NewStringDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = NewStringDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "plus"
    )
    @MemberOrder(sequence = "2.3.1")
    public UserSettingJdo newString(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value") final String value) {
        return newSetting(user, key, description, SettingType.STRING, value);
    }
    public String default0NewString() {
        return getContainer().getUser().getName();
    }

    // //////////////////////////////////////

    public static class NewIntDomainEvent extends ActionDomainEvent {
        public NewIntDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = NewIntDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "plus"
    )
    @MemberOrder(sequence = "2.3.2")
    public UserSettingJdo newInt(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value") final Integer value) {
        return newSetting(user, key, description, SettingType.INT, value.toString());
    }
    public String default0NewInt() {
        return getContainer().getUser().getName();
    }

    // //////////////////////////////////////

    public static class NewLongDomainEvent extends ActionDomainEvent {
        public NewLongDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = NewLongDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "plus"
    )
    @MemberOrder(sequence = "2.3.3")
    public UserSettingJdo newLong(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value") final Long value) {
        return newSetting(user, key, description, SettingType.LONG, value.toString());
    }
    public String default0NewLong() {
        return getContainer().getUser().getName();
    }

    // //////////////////////////////////////

    public static class NewLocalDateDomainEvent extends ActionDomainEvent {
        public NewLocalDateDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = NewLocalDateDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "plus"
    )
    @MemberOrder(sequence = "2.3.4")
    public UserSettingJdo newLocalDate(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value") final LocalDate value) {
        return newSetting(user, key, description, SettingType.LOCAL_DATE, value.toString(SettingAbstract.DATE_FORMATTER));
    }
    public String default0NewLocalDate() {
        return getContainer().getUser().getName();
    }

    // //////////////////////////////////////

    public static class NewBooleanDomainEvent extends ActionDomainEvent {
        public NewBooleanDomainEvent(final UserSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = NewBooleanDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "plus"
    )
    @MemberOrder(sequence = "2.3.5")
    public UserSettingJdo newBoolean(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            @Parameter(optionality=Optionality.OPTIONAL)
            final Boolean value) {
        return newSetting(user, key, description, SettingType.BOOLEAN, Boolean.toString(value != null && value));
    }
    public String default0NewBoolean() {
        return getContainer().getUser().getName();
    }

    // //////////////////////////////////////

    private UserSettingJdo newSetting(
            final String user,
            final String key, final String description, final SettingType settingType, final String valueRaw) {
        final UserSettingJdo setting = newTransientInstance(UserSettingJdo.class);
        setting.setUser(user);
        setting.setKey(key);
        setting.setType(settingType);
        setting.setDescription(description);
        setting.setValueRaw(valueRaw);
        persist(setting);
        return setting;
    }

}
