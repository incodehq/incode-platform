package org.incode.module.settings.dom.jdo;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.settings.SettingsModule;
import org.incode.module.settings.dom.ApplicationSetting;
import org.incode.module.settings.dom.ApplicationSettingMenu;
import org.incode.module.settings.dom.ApplicationSettingRepository;
import org.incode.module.settings.dom.ApplicationSettingsServiceRW;

/**
 * @deprecated - use {@link ApplicationSettingRepository} or {@link ApplicationSettingMenu} instead.
 */
@Deprecated
public class ApplicationSettingsServiceJdo extends AbstractService implements ApplicationSettingsServiceRW {

    //region > domain events
    public static abstract class PropertyDomainEvent<T> extends SettingsModule.PropertyDomainEvent<ApplicationSettingsServiceJdo, T> {
    }

    public static abstract class CollectionDomainEvent<T> extends SettingsModule.CollectionDomainEvent<ApplicationSettingsServiceJdo, T> {
    }

    public static abstract class ActionDomainEvent extends SettingsModule.ActionDomainEvent<ApplicationSettingsServiceJdo> {
    }
    //endregion

    //region > find (action)
    public static class FindDomainEvent extends ActionDomainEvent {
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

    //region > listAll (action)

    public static class ListAllDomainEvent extends ActionDomainEvent {
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

    //region > newString (action)
    public static class NewStringDomainEvent extends ActionDomainEvent {
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

    //region > newInt (action)

    public static class NewIntDomainEvent extends ActionDomainEvent {
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

    //region > newLong (action)

    public static class NewLongDomainEvent extends ActionDomainEvent {
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

    //region > newLocalDate (action)
    public static class NewLocalDateDomainEvent extends ActionDomainEvent {
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

    //region > newBoolean (action)

    public static class NewBooleanDomainEvent extends ActionDomainEvent {
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
