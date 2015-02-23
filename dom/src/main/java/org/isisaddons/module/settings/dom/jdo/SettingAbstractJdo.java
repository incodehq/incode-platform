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
import javax.jdo.annotations.PersistenceCapable;
import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.Setting;
import org.isisaddons.module.settings.dom.SettingAbstract;
import org.isisaddons.module.settings.dom.SettingType;
import org.joda.time.LocalDate;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;

/**
 * Factors out common implementation; however this is not annotated with {@link PersistenceCapable},
 * so that each subclass is its own root entity.
 */
public abstract class SettingAbstractJdo extends SettingAbstract implements Setting {

    public static abstract class PropertyDomainEvent<S extends SettingAbstractJdo, T> extends SettingsModule.PropertyDomainEvent<S, T> {
        public PropertyDomainEvent(final S source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final S source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<S extends SettingAbstractJdo, T> extends SettingsModule.CollectionDomainEvent<S, T> {
        public CollectionDomainEvent(final S source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final S source, final Identifier identifier, final Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent<S extends SettingAbstractJdo> extends SettingsModule.ActionDomainEvent<S> {
        public ActionDomainEvent(final S source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final S source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final S source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    private String key;

    @javax.jdo.annotations.Column(allowsNull="false")
    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    // //////////////////////////////////////

    public static class UpdateDescriptionDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
        public UpdateDescriptionDomainEvent(final SettingAbstractJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Action(
            domainEvent = UpdateDescriptionDomainEvent.class
    )
    public SettingAbstractJdo updateDescription(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Description")
            final String description) {
        setDescription(description);
        return this;
    }
    public String default0UpdateDescription() {
        return getDescription();
    }
    
    // //////////////////////////////////////

    private SettingType type;

    @javax.jdo.annotations.Column(allowsNull="false")
    public SettingType getType() {
        return type;
    }

    public void setType(final SettingType type) {
        this.type = type;
    }

    // //////////////////////////////////////

    private String valueRaw;

    @javax.jdo.annotations.Column(allowsNull="false")
    public String getValueRaw() {
        return valueRaw;
    }

    public void setValueRaw(final String valueAsRaw) {
        this.valueRaw = valueAsRaw;
    }

    // //////////////////////////////////////

    public static class UpdateAsStringDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
        public UpdateAsStringDomainEvent(final SettingAbstractJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateAsStringDomainEvent.class
    )
    public SettingAbstractJdo updateAsString(
            @ParameterLayout(named = "Value")
            final String value) {
        setValueRaw(value);
        return this;
    }
    public String default0UpdateAsString() {
        return getValueAsString();
    }
    public boolean hideUpdateAsString() {
        return typeIsNot(SettingType.STRING);
    }

    // //////////////////////////////////////

    public static class UpdateAsIntDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
        public UpdateAsIntDomainEvent(final SettingAbstractJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateAsIntDomainEvent.class
    )
    public SettingAbstractJdo updateAsInt(
            @ParameterLayout(named = "Value")
            final Integer value) {
        setValueRaw(value.toString());
        return this;
    }
    public Integer default0UpdateAsInt() {
        return getValueAsInt();
    }
    public boolean hideUpdateAsInt() {
        return typeIsNot(SettingType.INT);
    }

    // //////////////////////////////////////

    public static class UpdateAsLongDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
        public UpdateAsLongDomainEvent(final SettingAbstractJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateAsLongDomainEvent.class
    )
    public SettingAbstractJdo updateAsLong(
            @ParameterLayout(named = "Value") final
            Long value) {
        setValueRaw(value.toString());
        return this;
    }
    public Long default0UpdateAsLong() {
        return getValueAsLong();
    }
    public boolean hideUpdateAsLong() {
        return typeIsNot(SettingType.LONG);
    }

    // //////////////////////////////////////

    public static class UpdateAsLocalDateDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
        public UpdateAsLocalDateDomainEvent(final SettingAbstractJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateAsLocalDateDomainEvent.class
    )
    public SettingAbstractJdo updateAsLocalDate(
            @ParameterLayout(named = "Value")
            final LocalDate value) {
        setValueRaw(value.toString(DATE_FORMATTER));
        return this;
    }
    public LocalDate default0UpdateAsLocalDate() {
        return getValueAsLocalDate();
    }
    public boolean hideUpdateAsLocalDate() {
        return typeIsNot(SettingType.LOCAL_DATE);
    }

    // //////////////////////////////////////

    public static class UpdateAsBooleanDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
        public UpdateAsBooleanDomainEvent(final SettingAbstractJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateAsBooleanDomainEvent.class
    )
    public SettingAbstractJdo updateAsBoolean(
            @ParameterLayout(named = "Value")
            final Boolean value) {
        setValueRaw(value.toString());
        return this;
    }
    public Boolean default0UpdateAsBoolean() {
        return getValueAsBoolean();
    }
    public boolean hideUpdateAsBoolean() {
        return typeIsNot(SettingType.BOOLEAN);
    }
    
    // //////////////////////////////////////

    public static class DeleteDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
        public DeleteDomainEvent(final SettingAbstractJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = DeleteDomainEvent.class
    )
    public SettingAbstractJdo delete(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Are you sure?")
            final Boolean confirm) {
        if(confirm == null || !confirm) {
            container.informUser("Setting NOT deleted");
            return this;
        }
        container.remove(this);
        container.informUser("Setting deleted");
        return null;
    }
    

    // //////////////////////////////////////
    
    private DomainObjectContainer container;

    public void setDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }


}
