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
import javax.jdo.annotations.IdentityType;
import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.ApplicationSetting;
import org.isisaddons.module.settings.dom.SettingType;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.APPLICATION,
        schema = "isissettings",
        table="ApplicationSetting")
@javax.jdo.annotations.Queries({ 
     @javax.jdo.annotations.Query(
             name = "findByKey", language = "JDOQL", 
             value = "SELECT "
                     + "FROM org.isisaddons.module.settings.dom.jdo.ApplicationSettingJdo "
                     + "WHERE key == :key"),
        @javax.jdo.annotations.Query(
                name = "findNext", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.settings.dom.jdo.ApplicationSettingJdo "
                        + "WHERE key > :key "
                        + "ORDER BY key ASC"),
        @javax.jdo.annotations.Query(
                name = "findPrevious", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.settings.dom.jdo.ApplicationSettingJdo "
                        + "WHERE key < :key "
                        + "ORDER BY key DESC"),
     @javax.jdo.annotations.Query(
            name = "findAll", language = "JDOQL", 
            value = "SELECT "
                    + "FROM org.isisaddons.module.settings.dom.jdo.ApplicationSettingJdo "
                    + "ORDER BY key")
})
@DomainObject(
        objectType = "isissettings.ApplicationSetting",
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        named="Application Setting"
)
public class ApplicationSettingJdo extends SettingAbstractJdo implements ApplicationSetting {

    public static abstract class PropertyDomainEvent<T> extends SettingsModule.PropertyDomainEvent<ApplicationSettingJdo, T> {
        public PropertyDomainEvent(final ApplicationSettingJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final ApplicationSettingJdo source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends SettingsModule.CollectionDomainEvent<ApplicationSettingJdo, T> {
        public CollectionDomainEvent(final ApplicationSettingJdo source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final ApplicationSettingJdo source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends SettingsModule.ActionDomainEvent<ApplicationSettingJdo> {
        public ActionDomainEvent(final ApplicationSettingJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final ApplicationSettingJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final ApplicationSettingJdo source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class KeyDomainEvent extends PropertyDomainEvent<String> {
        public KeyDomainEvent(final ApplicationSettingJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public KeyDomainEvent(final ApplicationSettingJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }


    @javax.jdo.annotations.Column(length=JdoColumnLength.SettingAbstract.SETTING_KEY)
    @javax.jdo.annotations.PrimaryKey
    @Property(
            domainEvent = KeyDomainEvent.class
    )
    public String getKey() {
        return super.getKey();
    }
    @Override
    public void setKey(String key) {
        super.setKey(key);
    }

    // //////////////////////////////////////

    public static class DescriptionDomainEvent extends PropertyDomainEvent<String> {
        public DescriptionDomainEvent(final ApplicationSettingJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public DescriptionDomainEvent(final ApplicationSettingJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @javax.jdo.annotations.Column(length=JdoColumnLength.DESCRIPTION)
    @javax.jdo.annotations.Persistent
    @Property(
            domainEvent = DescriptionDomainEvent.class
    )
    @Override
    public String getDescription() {
        return super.getDescription();
    }
    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }
    
    // //////////////////////////////////////

    public static class ValueRawDomainEvent extends PropertyDomainEvent<String> {
        public ValueRawDomainEvent(final ApplicationSettingJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public ValueRawDomainEvent(final ApplicationSettingJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.SettingAbstract.VALUE_RAW)
    @javax.jdo.annotations.Persistent
    @Property(
            domainEvent = ValueRawDomainEvent.class
    )
    @Override
    public String getValueRaw() {
        return super.getValueRaw();
    }
    @Override
    public void setValueRaw(String valueAsRaw) {
        super.setValueRaw(valueAsRaw);
    }
    
    // //////////////////////////////////////

    public static class TypeDomainEvent extends PropertyDomainEvent<SettingType> {
        public TypeDomainEvent(final ApplicationSettingJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public TypeDomainEvent(final ApplicationSettingJdo source, final Identifier identifier, final SettingType oldValue, final SettingType newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.SettingAbstract.SETTING_TYPE)
    @javax.jdo.annotations.Persistent
    @Property(
            domainEvent = TypeDomainEvent.class
    )
    @Override
    public SettingType getType() {
        return super.getType();
    }
    @Override
    public void setType(SettingType type) {
        super.setType(type);
    }
}
