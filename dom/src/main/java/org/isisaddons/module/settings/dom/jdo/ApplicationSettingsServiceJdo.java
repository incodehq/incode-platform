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

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.ApplicationSetting;
import org.isisaddons.module.settings.dom.ApplicationSettingMenu;
import org.isisaddons.module.settings.dom.ApplicationSettingRepository;
import org.isisaddons.module.settings.dom.ApplicationSettingsServiceRW;

/**
 * @deprecated - use {@link ApplicationSettingRepository} or {@link ApplicationSettingMenu} instead.
 */
@Deprecated
public class ApplicationSettingsServiceJdo extends AbstractService implements ApplicationSettingsServiceRW {

    //region > domain events
    public static abstract class PropertyDomainEvent<T> extends SettingsModule.PropertyDomainEvent<ApplicationSettingsServiceJdo, T> {
        public PropertyDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends SettingsModule.CollectionDomainEvent<ApplicationSettingsServiceJdo, T> {
        public CollectionDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends SettingsModule.ActionDomainEvent<ApplicationSettingsServiceJdo> {
        public ActionDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > find
    public static class FindDomainEvent extends ActionDomainEvent {
        public FindDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = FindDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            named = "Find Application Setting",
            cssClassFa = "search"
    )
    @MemberOrder(sequence = "1.1")
    @Override
    public ApplicationSetting find(@ParameterLayout(named="Key") final String key) {
        return applicationSettingRepository.find(key);
    }

    //endregion

    //region > listAll

    public static class ListAllDomainEvent extends ActionDomainEvent {
        public ListAllDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = ListAllDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            named = "List All Application Settings",
            cssClassFa = "list"
    )
    @MemberOrder(sequence="1.2")
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<ApplicationSetting> listAll() {
        return applicationSettingRepository.listAll();
    }
    //endregion

    //region > newString
    public static class NewStringDomainEvent extends ActionDomainEvent {
        public NewStringDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
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
    @MemberOrder(sequence = "1.3.1")
    @Override
    public ApplicationSetting newString(
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            final String value) {
        return applicationSettingRepository.newString(key, description, value);
    }
    //endregion

    //region > newInt

    public static class NewIntDomainEvent extends ActionDomainEvent {
        public NewIntDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
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
    @MemberOrder(sequence="1.3.2")
    @Override
    public ApplicationSettingJdo newInt(
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            final Integer value) {
        return applicationSettingRepository.newInt(key, description, value);
    }

    //endregion

    //region > newLong

    public static class NewLongDomainEvent extends ActionDomainEvent {
        public NewLongDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
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
    @MemberOrder(sequence="1.3.3")
    @Override
    public ApplicationSettingJdo newLong(
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            final Long value) {
        return applicationSettingRepository.newLong(key, description, value);
    }

    //endregion

    //region > newLocalDate
    public static class NewLocalDateDomainEvent extends ActionDomainEvent {
        public NewLocalDateDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
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
    @MemberOrder(sequence = "1.3.4")
    @Override
    public ApplicationSettingJdo newLocalDate(
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            final LocalDate value) {
        return applicationSettingRepository.newLocalDate(key, description, value);
    }

    //endregion

    //region > newBoolean

    public static class NewBooleanDomainEvent extends ActionDomainEvent {
        public NewBooleanDomainEvent(final ApplicationSettingsServiceJdo source, final Identifier identifier, final Object... arguments) {
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
    @MemberOrder(sequence = "1.3.5")
    @Override
    public ApplicationSettingJdo newBoolean(
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            @Parameter(optionality=Optionality.OPTIONAL)
            final Boolean value) {
        return applicationSettingRepository.newBoolean(key, description, value);
    }

    //endregion

    //region > injected
    @Inject
    ApplicationSettingRepository applicationSettingRepository;
    //endregion

}
