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
package org.isisaddons.module.sessionlogger.dom;

import java.sql.Timestamp;
import java.util.List;
import javax.jdo.annotations.IdentityType;
import org.isisaddons.module.sessionlogger.SessionLoggerModule;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.session.SessionLoggingService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.APPLICATION,
        table="IsisSessionLogEntry")
@javax.jdo.annotations.Queries( {
        @javax.jdo.annotations.Query(
                name="findBySessionId", language="JDOQL",
                value="SELECT "
                      + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                      + "WHERE sessionId == :sessionId"),
        @javax.jdo.annotations.Query(
                name="findByUsernameAndTimestampBetween", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE username == :username "
                        + "&& loginTimestamp >= :from "
                        + "&& logoutTimestamp <= :to "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByUsernameAndTimestampAfter", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE username == :username "
                        + "&& loginTimestamp >= :from "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByUsernameAndTimestampBefore", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE username == :username "
                        + "&& loginTimestamp <= :from "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByUsername", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE username == :username "
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
                name="findByUsernameAndTimestampStrictlyBefore", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE username == :username "
                        + "&& loginTimestamp < :from "
                        + "ORDER BY loginTimestamp DESC"),
        @javax.jdo.annotations.Query(
                name="findByUsernameAndTimestampStrictlyAfter", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                        + "WHERE username == :username "
                        + "&& loginTimestamp > :from "
                        + "ORDER BY loginTimestamp ASC"),
        @javax.jdo.annotations.Query(
                name="listAllActiveSessions", language="JDOQL",
                value="SELECT "
                      + "FROM org.isisaddons.module.sessionlogger.dom.SessionLogEntry "
                      + "WHERE logoutTimestamp == null "
                      + "ORDER BY loginTimestamp ASC")
})
@DomainObject(
        objectType = "IsisSessionLogEntry",
        editing = Editing.DISABLED
)
@DomainObjectLayout(named = "Session Log Entry")
@MemberGroupLayout(
        columnSpans={6,0,6},
        left={"Identifiers"},
        right={"Detail"})
public class SessionLogEntry {

    public static abstract class PropertyDomainEvent<T> extends SessionLoggerModule.PropertyDomainEvent<SessionLogEntry, T> {
        public PropertyDomainEvent(final SessionLogEntry source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final SessionLogEntry source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends SessionLoggerModule.CollectionDomainEvent<SessionLogEntry, T> {
        public CollectionDomainEvent(final SessionLogEntry source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final SessionLogEntry source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends SessionLoggerModule.ActionDomainEvent<SessionLogEntry> {
        public ActionDomainEvent(final SessionLogEntry source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final SessionLogEntry source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final SessionLogEntry source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public String title() {
        return String.format("%s: %s logged %s %s",
                getLoginTimestamp(),
                getUsername(),
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

    // //////////////////////////////////////
    // sessionId (property)
    // //////////////////////////////////////

    public static class SessionIdDomainEvent extends PropertyDomainEvent<String> {
        public SessionIdDomainEvent(final SessionLogEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public SessionIdDomainEvent(final SessionLogEntry source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String sessionId;

    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Column(allowsNull="false", length=15)
    @Property(
            domainEvent = CausedByDomainEvent.class
    )
    @MemberOrder(name="Identifiers",sequence = "12")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    // //////////////////////////////////////
    // user (property)
    // //////////////////////////////////////

    public static class UsernameDomainEvent extends PropertyDomainEvent<String> {
        public UsernameDomainEvent(final SessionLogEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public UsernameDomainEvent(final SessionLogEntry source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String username;

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.USER_NAME)
    @Property(
            domainEvent = UsernameDomainEvent.class
    )
    @MemberOrder(name="Identifiers",sequence = "10")
    public String getUsername() {
        return username;
    }

    public void setUsername(final String user) {
        this.username = user;
    }


    // //////////////////////////////////////
    // loginTimestamp (property)
    // //////////////////////////////////////

    public static class LoginTimestampDomainEvent extends PropertyDomainEvent<Timestamp> {
        public LoginTimestampDomainEvent(final SessionLogEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public LoginTimestampDomainEvent(final SessionLogEntry source, final Identifier identifier, final Timestamp oldValue, final Timestamp newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private Timestamp loginTimestamp;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            domainEvent = LoginTimestampDomainEvent.class
    )
    @MemberOrder(name="Identifiers",sequence = "20")
    public Timestamp getLoginTimestamp() {
        return loginTimestamp;
    }

    public void setLoginTimestamp(final Timestamp loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    // //////////////////////////////////////
    // logoutTimestamp (property)
    // //////////////////////////////////////

    public static class LogoutTimestampDomainEvent extends PropertyDomainEvent<Timestamp> {
        public LogoutTimestampDomainEvent(final SessionLogEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public LogoutTimestampDomainEvent(final SessionLogEntry source, final Identifier identifier, final Timestamp oldValue, final Timestamp newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private Timestamp logoutTimestamp;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            domainEvent = LogoutTimestampDomainEvent.class
    )
    @MemberOrder(name="Identifiers",sequence = "20")
    public Timestamp getLogoutTimestamp() {
        return logoutTimestamp;
    }

    public void setLogoutTimestamp(final Timestamp logoutTimestamp) {
        this.logoutTimestamp = logoutTimestamp;
    }

    // //////////////////////////////////////
    // causedBy (property)
    // //////////////////////////////////////

    public static class CausedByDomainEvent extends PropertyDomainEvent<SessionLoggingService.CausedBy> {
        public CausedByDomainEvent(final SessionLogEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public CausedByDomainEvent(
                final SessionLogEntry source, final Identifier identifier,
                final SessionLoggingService.CausedBy oldValue, final SessionLoggingService.CausedBy newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private SessionLoggingService.CausedBy causedBy;

    @javax.jdo.annotations.Column(allowsNull="false", length=18)
    @Property(
            domainEvent = CausedByDomainEvent.class
    )
    @MemberOrder(name="Detail",sequence = "20")
    public SessionLoggingService.CausedBy getCausedBy() {
        return causedBy;
    }

    public void setCausedBy(final SessionLoggingService.CausedBy causedBy) {
        this.causedBy = causedBy;
    }

    // //////////////////////////////////////
    // toString
    // //////////////////////////////////////

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "type,username,loginTimestamp,causedBy");
    }

    
    // //////////////////////////////////////
    // next, previous
    // //////////////////////////////////////

    public static class NextDomainEvent extends ActionDomainEvent {
        public NextDomainEvent(final SessionLogEntry source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = NextDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-step-forward"
    )
    @MemberOrder(sequence = "2")
    public SessionLogEntry next() {
        final List<SessionLogEntry> after = sessionLogEntryRepository.findByUsernameAndStrictlyAfter(getUsername(), getLoginTimestamp());
        return !after.isEmpty() ? after.get(0) : this;
    }

    public String disableNext() {
        return next() == this? "None after": null;
    }

    // //////////////////////////////////////

    public static class PreviousDomainEvent extends ActionDomainEvent {
        public PreviousDomainEvent(final SessionLogEntry source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = PreviousDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-step-backward"
    )
    @MemberOrder(sequence = "1")
    public SessionLogEntry previous() {
        final List<SessionLogEntry> before = sessionLogEntryRepository.findByUsernameAndStrictlyBefore(getUsername(), getLoginTimestamp());
        return !before.isEmpty() ? before.get(0) : this;
    }

    public String disablePrevious() {
        return previous() == this? "None before": null;
    }

    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private SessionLogEntryRepository sessionLogEntryRepository;

}
