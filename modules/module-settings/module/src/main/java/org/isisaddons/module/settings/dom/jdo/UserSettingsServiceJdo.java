package org.isisaddons.module.settings.dom.jdo;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.user.UserService;

import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.UserSetting;
import org.isisaddons.module.settings.dom.UserSettingMenu;
import org.isisaddons.module.settings.dom.UserSettingRepository;
import org.isisaddons.module.settings.dom.UserSettingsServiceRW;

/**
 * @deprecated - use {@link UserSettingRepository} or {@link UserSettingMenu} instead.
 */
@Deprecated
public class UserSettingsServiceJdo extends AbstractService implements UserSettingsServiceRW {

    //region > domain events
    public static abstract class PropertyDomainEvent<T> extends SettingsModule.PropertyDomainEvent<UserSettingsServiceJdo, T> {
    }

    public static abstract class CollectionDomainEvent<T> extends SettingsModule.CollectionDomainEvent<UserSettingsServiceJdo, T> {
    }

    public static abstract class ActionDomainEvent extends SettingsModule.ActionDomainEvent<UserSettingsServiceJdo> {
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
        return userSettingRepository.find(user, key);
    }
    //endregion

    //region > listAllFor (action)

    public static class ListAllForDomainEvent extends ActionDomainEvent {
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
        return userSettingRepository.listAllFor(user);
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

    //endregion

    //region > listAll (action)

    public static class ListAllDomainEvent extends ActionDomainEvent {
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
        return userSettingRepository.listAll();
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
    @MemberOrder(sequence = "2.3.1")
    public UserSettingJdo newString(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            final String value) {
        return userSettingRepository.newString(user, key, description, value);
    }
    public String default0NewString() {
        return userService.getUser().getName();
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
    @MemberOrder(sequence = "2.3.2")
    public UserSettingJdo newInt(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            final Integer value) {
        return userSettingRepository.newInt(user, key, description, value);
    }
    public String default0NewInt() {
        return userService.getUser().getName();
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
    @MemberOrder(sequence = "2.3.3")
    public UserSettingJdo newLong(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            final Long value) {
        return userSettingRepository.newLong(user, key, description, value);
    }
    public String default0NewLong() {
        return userService.getUser().getName();
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
    @MemberOrder(sequence = "2.3.4")
    public UserSettingJdo newLocalDate(
            @ParameterLayout(named="User")
            final String user,
            @ParameterLayout(named="Key")
            final String key,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Value")
            final LocalDate value) {
        return userSettingRepository.newLocalDate(user, key, description, value);
    }
    public String default0NewLocalDate() {
        return userService.getUser().getName();
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
        return userSettingRepository.newBoolean(user, key, description, value);
    }
    public String default0NewBoolean() {
        return userService.getUser().getName();
    }

    //endregion

    //region > injected
    @Inject
    UserService userService;
    @Inject
    UserSettingRepository userSettingRepository;
    //endregion
}
