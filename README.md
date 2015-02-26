# isis-module-sessionlogger #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-sessionlogger.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-sessionlogger)

This module, intended for use within [Apache Isis](http://isis.apache.org), provides an implementation of Isis' 
`SessionLoggingService` API that persists audit entries using Isis' own (JDO) objectstore.  Typically this will be to a
relational database; the module's `SessionLogEntry` entity is mapped to the "IsisSessionLogEntry" table.

## Screenshots ##

The sessionlogger module automatically creates log entries whenever a user logs on or logs out.  The currently logged on users of the application (that is: those for whom there is a valid non-expired HTTP session) can be found from the activity menu:

![](https://raw.github.com/isisaddons/isis-module-sessionlogger/master/images/010-active-sessions.png)

In the screenshot below there are two currently active users:

![](https://raw.github.com/isisaddons/isis-module-sessionlogger/master/images/020-active-sessions-listed.png)

The module also allows current and previously active sessions to be searched for:

![](https://raw.github.com/isisaddons/isis-module-sessionlogger/master/images/030-find-sessions.png)

The list of sessions can optionally be filtered by user and date range:

![](https://raw.github.com/isisaddons/isis-module-sessionlogger/master/images/040-find-sessions-prompt.png)

... returning matching sessions:

![](https://raw.github.com/isisaddons/isis-module-sessionlogger/master/images/050-find-sessions-listed.png)


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 7 (nb: Isis currently does not support JDK 8)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-audit.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## How to Configure/Use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

#### "Out-of-the-box" ####

To use "out-of-the-box":

* update your classpath by adding this dependency in your project's `dom` module's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.sessionlogger&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-sessionlogger-dom&lt;/artifactId&gt;
        &lt;version&gt;1.8.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

Remaining steps to configure:

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=\
                    ...,\
                    org.isisaddons.module.sessionlogger.dom,\
                    ...

</pre>

Notes:
* Check for releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-sessionlogger-dom).
* The `SessionLoggingServiceMenu` service is optional but recommended; see below for more information.


#### "Out-of-the-box" (-SNAPSHOT) ####

If you want to use the current `-SNAPSHOT`, then the steps are the same as above, except:

* when updating the classpath, specify the appropriate -SNAPSHOT version:

<pre>
    &lt;version&gt;1.9.0-SNAPSHOT&lt;/version&gt;
</pre>

* add the repository definition to pick up the most recent snapshot (we use the Cloudbees continuous integration service).  We suggest defining the repository in a `<profile>`:

<pre>
    &lt;profile&gt;
        &lt;id&gt;cloudbees-snapshots&lt;/id&gt;
        &lt;activation&gt;
            &lt;activeByDefault&gt;true&lt;/activeByDefault&gt;
        &lt;/activation&gt;
        &lt;repositories&gt;
            &lt;repository&gt;
                &lt;id&gt;snapshots-repo&lt;/id&gt;
                &lt;url&gt;http://repository-estatio.forge.cloudbees.com/snapshot/&lt;/url&gt;
                &lt;releases&gt;
                    &lt;enabled&gt;false&lt;/enabled&gt;
                &lt;/releases&gt;
                &lt;snapshots&gt;
                    &lt;enabled&gt;true&lt;/enabled&gt;
                &lt;/snapshots&gt;
            &lt;/repository&gt;
        &lt;/repositories&gt;
    &lt;/profile&gt;
</pre>

#### Forking the repo ####

If instead you want to extend this module's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml   ` - parent pom
* `dom       ` - the module implementation, depends on Isis applib
* `fixture   ` - fixtures, holding a sample domain objects and fixture scripts; depends on `dom`
* `integtests` - integration tests for the module; depends on `fixture`
* `webapp    ` - demo webapp (see above screenshots); depends on `dom` and `fixture`

Only the `dom` project is released to Maven Central Repo.  The versions of the other modules are purposely left at 
`0.0.1-SNAPSHOT` because they are not intended to be released.

## API ##

The `SessionLoggingService` defines the following API:

    public interface SessionLoggingService {

        public enum Type {
            LOGIN,
            LOGOUT
        }

        public enum CausedBy {
            USER,
            SESSION_EXPIRATION
        }

        @Programmatic
        void log(Type type, String username, Date date, CausedBy causedBy);
    }


Isis will automatically call this method on the service implementation if configured to run the Wicket viewer.

## Implementation ##

The `SessionLoggingService` API is implemented in this module by the `org.isisaddons.module.sessionlogger.SessionLoggingServiceDefault` class.
This implementation simply inserts a session log entry (`SessionLogEntry`) when either a user logs on, logs out or if
their session expires.

The `SessionLogEntry` properties directly correspond to parameters of the `SessionLoggingService` `log()` API:

    public class SessionLogEntry
        ...
        private String sessionId;
        private String username;
        private SessionLoggingService.Type type;
        private Timestamp loginTimestamp;
        private Timestamp logoutTimestamp;
        private SessionLoggingService.CausedBy causedBy;
        ...
    }

where:

* `sessionId` identifies the user's session. Primary key. (*Note*: it is not the http session id!)
* `username` identifies the user that has logged in/out
* `type` determines whether this was a login or logout.
* `loginTimestamp` is the date that the login of the session event occurred
* `logoutTimestamp` is the date that the logout of the session event occurred
* `causedBy`indicates whether the session was logged out due to session expiry

The `SessionLogEntry` entity is designed such that it can be rendered on an Isis user interface if required.
    
## Supporting Services ##

As well as the `SessionLoggingServiceDefault` service (that implements the `SessionLoggingService` API), the module
also provides two further domain services:

* `SessionLogEntryRepository` provides the ability to search for persisted (`SessionLogEntry`) entries.  None of its
  actions are visible in the user interface (they are all `@Programmatic`) and so this service is automatically
  registered.

* `SessionLoggingServiceMenu` provides the secondary "Activity" menu for listing all active sessions and for searching for session entries by user and by date.

The `SessionLoggingServiceMenu` is automatically registered as a domain service; as such its actions will appear in the
user interface. If this is not required, then either use security permissions or write a vetoing subscriber on the
event bus to hide this functionality, eg:

    @DomainService(nature = NatureOfService.DOMAIN)
    public class HideIsisAddonsSessionLoggerFunctionality {

        @Programmatic @PostConstruct
        public void postConstruct() { eventBusService.register(this); }

        @Programmatic @PreDestroy
        public void preDestroy() { eventBusService.unregister(this); }

        @Programmatic @Subscribe
        public void on(final SessionLoggerModule.ActionDomainEvent<?> event) { event.hide(); }

        @Inject
        private EventBusService eventBusService;
    }


## Related Modules/Services ##

There is some overlap with the`AuditingService3` API, which audits changes to entities by end-users.  Implementations
of this service are referenced by the [Isis Add-ons](http://www.isisaddons.org) website.


## Known issues or Limitations ##

The Restful Objects viewer currently does not support this service.


## Change Log ##

* `1.8.0` - against Isis 1.8.0


## Legal Stuff ##
 
#### License ####

    Copyright 2015 Martin Grigorov & Dan Haywood

    Licensed under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

#### Dependencies ####

There are no third-party dependencies.


##  Maven deploy notes

Only the `dom` module is deployed, and is done so using Sonatype's OSS support (see 
[user guide](http://central.sonatype.org/pages/apache-maven.html)).

#### Release to Sonatype's Snapshot Repo ####

To deploy a snapshot, use:

    pushd dom
    mvn clean deploy
    popd

The artifacts should be available in Sonatype's 
[Snapshot Repo](https://oss.sonatype.org/content/repositories/snapshots).

#### Release to Maven Central ####

The `release.sh` script automates the release process.  It performs the following:

* performs a sanity check (`mvn clean install -o`) that everything builds ok
* bumps the `pom.xml` to a specified release version, and tag
* performs a double check (`mvn clean install -o`) that everything still builds ok
* releases the code using `mvn clean deploy`
* bumps the `pom.xml` to a specified release version

For example:

    sh release.sh 1.8.0 \
                  1.9.0-SNAPSHOT \
                  dan@haywood-associates.co.uk \
                  "this is not really my passphrase"
    
where
* `$1` is the release version
* `$2` is the snapshot version
* `$3` is the email of the secret key (`~/.gnupg/secring.gpg`) to use for signing
* `$4` is the corresponding passphrase for that secret key.

Other ways of specifying the key and passphrase are available, see the `pgp-maven-plugin`'s 
[documentation](http://kohsuke.org/pgp-maven-plugin/secretkey.html)).

If the script completes successfully, then push changes:

    git push origin master
    git push origin 1.8.0

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).
            
