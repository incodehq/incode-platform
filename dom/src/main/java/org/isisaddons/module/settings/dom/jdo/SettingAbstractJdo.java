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

import javax.inject.Inject;
import javax.jdo.annotations.PersistenceCapable;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.Setting;
import org.isisaddons.module.settings.dom.SettingAbstract;
import org.isisaddons.module.settings.dom.SettingType;

import lombok.Getter;
import lombok.Setter;

/**
 * Factors out common implementation; however this is not annotated with {@link PersistenceCapable},
 * so that each subclass is its own root entity.
 */
public abstract class SettingAbstractJdo extends SettingAbstract implements Setting {

    //region > domain events

    public static abstract class PropertyDomainEvent<S extends SettingAbstractJdo, T>
            extends SettingsModule.PropertyDomainEvent<S, T> { }

    public static abstract class CollectionDomainEvent<S extends SettingAbstractJdo, T>
            extends SettingsModule.CollectionDomainEvent<S, T> { }

    public static abstract class ActionDomainEvent<S extends SettingAbstractJdo>
            extends SettingsModule.ActionDomainEvent<S> { }
    //endregion

    @javax.jdo.annotations.Column(allowsNull="false")
    @Getter @Setter
    private String key;

    @Getter @Setter
    private String description;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Getter @Setter
    private SettingType type;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Getter @Setter
    private String valueRaw;

    //region > updateDescription (action)

    public static class UpdateDescriptionDomainEvent
            extends ActionDomainEvent<SettingAbstractJdo> {
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


    //endregion

    //region > updateAsString (action)

    public static class UpdateAsStringDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
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

    //endregion

    //region > updateAsInt (action)

    public static class UpdateAsIntDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
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

    //endregion

    //region > updateAsLong (action)

    public static class UpdateAsLongDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
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

    //endregion

    //region > updateAsLocalDate (action)

    public static class UpdateAsLocalDateDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
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

    //endregion

    //region > updateAsBoolean (action)

    public static class UpdateAsBooleanDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
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

    //endregion

    //region > delete (action)

    public static class DeleteDomainEvent extends ActionDomainEvent<SettingAbstractJdo> {
    }

    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
            cssClassFa = "trash",
            cssClass = "btn-warning"
    )
    public SettingAbstractJdo delete() {
        repositoryService.remove(this);
        messageService.informUser("Setting deleted");
        return null;
    }

    //endregion

    //region > injected services

    @Inject
    MessageService messageService;

    @Inject
    RepositoryService repositoryService;

    //endregion


}
