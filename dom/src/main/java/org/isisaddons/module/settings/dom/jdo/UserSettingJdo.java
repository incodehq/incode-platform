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


import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.HasUsername;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;

import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.SettingType;
import org.isisaddons.module.settings.dom.UserSetting;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.APPLICATION, 
        objectIdClass=UserSettingJdoPK.class,
        schema = "isissettings",
        table="UserSetting")
@javax.jdo.annotations.Queries({ 
    @javax.jdo.annotations.Query(
            name = "findByUserAndKey", language = "JDOQL", 
            value = "SELECT "
                    + "FROM org.isisaddons.module.settings.dom.jdo.UserSettingJdo "
                    + "WHERE user == :user "
                    + "&& key == :key "),
        @javax.jdo.annotations.Query(
        name = "findNext", language = "JDOQL",
        value = "SELECT "
                + "FROM org.isisaddons.module.settings.dom.jdo.UserSettingJdo "
                + "WHERE user == :user "
                + "&&    key > :key "
                + "ORDER BY key ASC"),
        @javax.jdo.annotations.Query(
        name = "findPrevious", language = "JDOQL",
        value = "SELECT "
                + "FROM org.isisaddons.module.settings.dom.jdo.UserSettingJdo "
                + "WHERE user == :user "
                + "&&    key < :key "
                + "ORDER BY key DESC"),
        @javax.jdo.annotations.Query(
            name = "findByUser", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.settings.dom.jdo.UserSettingJdo "
                    + "WHERE user == :user "
                    + "ORDER BY key")
    ,@javax.jdo.annotations.Query(
            name = "findAll", language = "JDOQL", 
            value = "SELECT "
                    + "FROM org.isisaddons.module.settings.dom.jdo.UserSettingJdo "
                    + "ORDER BY user, key") 
})
// can't see how to specify this order in the primary key; however HSQLDB objects :-(
//@javax.jdo.annotations.Unique(name="USER_KEY_IDX", members={"user","key"}) 
@DomainObject(
        objectType = "isissettings.UserSetting",
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        named="User Setting"
)
public class UserSettingJdo extends SettingAbstractJdo implements UserSetting, HasUsername {

    //region > domain events
    public static class PropertyDomainEvent<T>
            extends SettingsModule.PropertyDomainEvent<UserSettingJdo, T> { }

    public static class CollectionDomainEvent<T>
            extends SettingsModule.CollectionDomainEvent<UserSettingJdo, T> { }

    public static class ActionDomainEvent
            extends SettingsModule.ActionDomainEvent<UserSettingJdo> { }
    //endregion

    //region > user (property)

    public static class UserDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(length=JdoColumnLength.USER_NAME)
    @javax.jdo.annotations.PrimaryKey
    @Property(
            domainEvent = UserDomainEvent.class
    )
    @Title(sequence="5", append=": ")
    @Getter @Setter
    private String user;

    @Programmatic
    public String getUsername() {
        return getUser();
    }

    //endregion

    //region > key (overridden property)

    public static class KeyDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(length=128)
    @javax.jdo.annotations.PrimaryKey
    @Property(
            domainEvent = KeyDomainEvent.class
    )
    @Title(sequence="10")
    @Override
    public String getKey() {
        return super.getKey();
    }
    @Override
    public void setKey(String key) {
        super.setKey(key);
    }

    //endregion

    //region > description (overridden property)

    public static class DescriptionDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(length=JdoColumnLength.DESCRIPTION)
    @javax.jdo.annotations.Persistent
    @Property(
            domainEvent=DescriptionDomainEvent.class
    )
    @Override
    public String getDescription() {
        return super.getDescription();
    }
    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    //endregion

    //region > valueRaw (overridden property)

    public static class ValueRawDomainEvent extends PropertyDomainEvent<String> {
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

    //endregion

    //region > type (overridden property)


    public static class TypeDomainEvent extends PropertyDomainEvent<SettingType> {
    }

    @javax.jdo.annotations.Column(allowsNull="false", length=20)
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

    //endregion

}
