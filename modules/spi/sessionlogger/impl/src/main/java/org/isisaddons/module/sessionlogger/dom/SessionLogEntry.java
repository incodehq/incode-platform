package org.isisaddons.module.sessionlogger.dom;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasUsername;
import org.apache.isis.applib.services.session.SessionLoggingService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;

import org.isisaddons.module.sessionlogger.SessionLoggerModule;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.APPLICATION,
        schema = "isissessionlogger",
        table="SessionLogEntry")
@javax.jdo.annotations.Queries( {
        @javax.jdo.annotations.Query(
                name="findBySessionId", language="JDOQL",
                value="SELECT "
                      + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                      + "WHERE sessionId == :sessionId"),
        @javax.jdo.annotations.Query(
                name="findByUserAndTimestampBetween", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE user == :user "
                        + "&& loginTimestamp >= :from "
                        + "&& logoutTimestamp <= :to "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByUserAndTimestampAfter", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE user == :user "
                        + "&& loginTimestamp >= :from "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByUserAndTimestampBefore", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE user == :user "
                        + "&& loginTimestamp <= :from "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByUser", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE user == :user "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByTimestampBetween", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE loginTimestamp >= :from "
                        + "&&    logoutTimestamp <= :to "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByTimestampAfter", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE loginTimestamp >= :from "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByTimestampBefore", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE loginTimestamp <= :to "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="find", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByUserAndTimestampStrictlyBefore", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE user == :user "
                        + "&& loginTimestamp < :from "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByUserAndTimestampStrictlyAfter", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE user == :user "
                        + "&& loginTimestamp > :from "
                        + "ORDER BY loginTimestamp ASC"),
        @javax.jdo.annotations.Query(
                name="listAllActiveSessions", language="JDOQL",
                value="SELECT "
                      + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                      + "WHERE logoutTimestamp == null "
                      + "ORDER BY loginTimestamp ASC"),
        @javax.jdo.annotations.Query(
                name="findRecentByUser", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE user == :user "
                        + "ORDER BY loginTimestamp DESC "
                        + "RANGE 0,10"),
        @javax.jdo.annotations.Query(
                name="logoutAllActiveSessions", language="JDOQL",
                value="UPDATE org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                       + "   SET logoutTimestamp == :logoutTimestamp "
                       + "      ,causedBy2 == :causedBy2 "
                       + " WHERE causedBy2 == null" )
})
@DomainObject(
        objectType = "isissessionlogger.SessionLogEntry",
        editing = Editing.DISABLED
)
@DomainObjectLayout(named = "Session Log Entry")
public class SessionLogEntry implements HasUsername, Comparable<SessionLogEntry> {

    //region > domain events
    public static abstract class PropertyDomainEvent<T> extends SessionLoggerModule.PropertyDomainEvent<SessionLogEntry, T> {
    }

    public static abstract class CollectionDomainEvent<T> extends SessionLoggerModule.CollectionDomainEvent<SessionLogEntry, T> {
    }

    public static abstract class ActionDomainEvent extends SessionLoggerModule.ActionDomainEvent<SessionLogEntry> {
    }
    //endregion

    //region > title, icon etc
    public String title() {

        // nb: not thread-safe
        // formats defined in https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return String.format("%s: %s logged %s %s",
                format.format(getLoginTimestamp()),
                getUser(),
                getLogoutTimestamp() == null ? "in": "out",
                getCausedBy() == SessionLoggingService.CausedBy.SESSION_EXPIRATION ? "(session expired)" : "");
    }

    public String cssClass() {
        return "sessionLogEntry-" + iconName();
    }

    public String iconName() {
        return getLogoutTimestamp() == null
                ? "login"
                :getCausedBy() != SessionLoggingService.CausedBy.SESSION_EXPIRATION
                    ? "logout"
                    : "expired";
    }
    //endregion

    //region > sessionId (property)

    public static class SessionIdDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Column(allowsNull="false", length=15)
    @Property(
            domainEvent = CausedByDomainEvent.class
    )
    @Getter @Setter
    private String sessionId;

    //endregion

    //region > user (property); getUsername


    public static class UsernameDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.USER_NAME)
    @Property(
            domainEvent = UsernameDomainEvent.class
    )
    @Getter @Setter
    private String user;

    @Programmatic
    public String getUsername() {
        return getUser();
    }

    //endregion

    //region > loginTimestamp (property)


    public static class LoginTimestampDomainEvent extends PropertyDomainEvent<Timestamp> {
    }

    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            domainEvent = LoginTimestampDomainEvent.class
    )
    @Getter @Setter
    private Timestamp loginTimestamp;

    //endregion

    //region > logoutTimestamp (property)


    public static class LogoutTimestampDomainEvent extends PropertyDomainEvent<Timestamp> {
    }

    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            domainEvent = LogoutTimestampDomainEvent.class
    )
    @Getter @Setter
    private Timestamp logoutTimestamp;

    //endregion

    //region > causedBy (property)

    /**
     * anticipates Isis 1.13.1
     */
    enum CausedBy2 {
        USER,
        SESSION_EXPIRATION,
        RESTART;
    }


    public static class CausedByDomainEvent extends PropertyDomainEvent<SessionLoggingService.CausedBy> {
    }

    @javax.jdo.annotations.Column(
            allowsNull="true",
            length=18,
            name = "causedBy" // until Isis 1.13.1 is extended
    )
    @Property(
            domainEvent = CausedByDomainEvent.class
    )
    @PropertyLayout(
            named = "Caused by"
    )
    @Getter @Setter
    private CausedBy2 causedBy2; // until Isis 1.13.1 is extended


    @Programmatic
    @javax.jdo.annotations.NotPersistent
    SessionLoggingService.CausedBy getCausedBy() {
        final CausedBy2 causedBy2 = getCausedBy2();
        return asCausedBy(causedBy2);
    }
    void setCausedBy(SessionLoggingService.CausedBy causedBy) {
        setCausedBy2(asCausedBy2(causedBy));
    }

    private static SessionLogEntry.CausedBy2 asCausedBy2(SessionLoggingService.CausedBy causedBy) {

        return causedBy != null
                ? CausedBy2.valueOf(causedBy.name())
                : null;
    }
    private static SessionLoggingService.CausedBy asCausedBy(SessionLogEntry.CausedBy2 causedBy2) {
        return causedBy2 != null
                ? SessionLoggingService.CausedBy.valueOf(causedBy2.name())
                : null;
    }


    //endregion

    //region > next

    public static class NextDomainEvent extends ActionDomainEvent {
    }

    @Action(
            domainEvent = NextDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-step-forward",
            cssClassFaPosition = ActionLayout.CssClassFaPosition.RIGHT
    )
    public SessionLogEntry next() {
        final List<SessionLogEntry> after = sessionLogEntryRepository.findByUserAndStrictlyAfter(getUser(), getLoginTimestamp());
        return !after.isEmpty() ? after.get(0) : this;
    }

    public String disableNext() {
        return next() == this? "None after": null;
    }

    //endregion

    //region > previous
    public static class PreviousDomainEvent extends ActionDomainEvent {
    }

    @Action(
            domainEvent = PreviousDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-step-backward"
    )
    public SessionLogEntry previous() {
        final List<SessionLogEntry> before = sessionLogEntryRepository.findByUserAndStrictlyBefore(getUser(), getLoginTimestamp());
        return !before.isEmpty() ? before.get(0) : this;
    }

    public String disablePrevious() {
        return previous() == this? "None before": null;
    }
    //endregion

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "loginTimestamp","username","sessionId","logoutTimestamp","causedBy");
    }

    @Override
    public int compareTo(final SessionLogEntry logEntry) {
        return ObjectContracts.compare(this, logEntry, "loginTimestamp","username","sessionId","logoutTimestamp","causedBy");
    }

    //endregion

    //region > Injected services
    @javax.inject.Inject
    SessionLogEntryRepository sessionLogEntryRepository;
    //endregion


}
