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

import javax.jdo.annotations.PersistenceCapable;
import org.isisaddons.module.settings.dom.ApplicationSetting;
import org.isisaddons.module.settings.dom.SettingAbstract;
import org.isisaddons.module.settings.dom.SettingType;
import org.joda.time.LocalDate;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PropertyLayout;

/**
 * Factors out common implementation; however this is not annotated with {@link PersistenceCapable},
 * so that each subclass is its own root entity.
 */
public abstract class SettingAbstractJdo extends SettingAbstract implements ApplicationSetting {

    private String key;

    @javax.jdo.annotations.Column(allowsNull="false")
    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    // //////////////////////////////////////

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @PropertyLayout(
            named = "Update"
    )
    @MemberOrder(name="Description", sequence="1")
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

    @PropertyLayout(
            named = "Update"
    )
    @MemberOrder(name="ValueAsString", sequence="1")
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

    @PropertyLayout(
            named = "Update"
    )
    @MemberOrder(name="ValueAsInt", sequence="1")
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

    @PropertyLayout(
            named = "Update"
    )
    @MemberOrder(name="ValueAsLong", sequence="1")
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

    @PropertyLayout(
            named = "Update"
    )
    @MemberOrder(name="ValueAsLocalDate", sequence="1")
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

    @PropertyLayout(
            named = "Update"
    )
    @MemberOrder(name="ValueAsBoolean", sequence="1")
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
