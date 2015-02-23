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
import org.isisaddons.module.settings.dom.ApplicationSetting;
import org.isisaddons.module.settings.dom.ApplicationSettingsService;
import org.isisaddons.module.settings.dom.ApplicationSettingsServiceRW;
import org.isisaddons.module.settings.dom.SettingAbstract;
import org.isisaddons.module.settings.dom.SettingType;
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
public class ApplicationSettingsServiceJdo extends AbstractService implements ApplicationSettingsServiceRW {

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

    // //////////////////////////////////////

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
        return firstMatch(
                new QueryDefault<>(ApplicationSettingJdo.class,
                        "findByKey", 
                        "key", key));
    }

    // //////////////////////////////////////

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
        return (List)allMatches(
                new QueryDefault<>(ApplicationSettingJdo.class,
                        "findAll"));
    }

    // //////////////////////////////////////

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
        return newSetting(key, description, SettingType.STRING, value);
    }

    // //////////////////////////////////////

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
        return newSetting(key, description, SettingType.INT, value.toString());
    }

    // //////////////////////////////////////

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
        return newSetting(key, description, SettingType.LONG, value.toString());
    }

    // //////////////////////////////////////

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
        return newSetting(key, description, SettingType.LOCAL_DATE, value.toString(SettingAbstract.DATE_FORMATTER));
    }

    // //////////////////////////////////////

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
        return newSetting(key, description, SettingType.BOOLEAN, Boolean.toString(value != null && value));
    }


    // //////////////////////////////////////

    private ApplicationSettingJdo newSetting(
            final String key, final String description, final SettingType settingType, final String valueRaw) {
        final ApplicationSettingJdo setting = newTransientInstance(ApplicationSettingJdo.class);
        setting.setKey(key);
        setting.setDescription(description);
        setting.setValueRaw(valueRaw);
        setting.setType(settingType);
        persist(setting);
        return setting;
    }

}
