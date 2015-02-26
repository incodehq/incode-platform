# isis-module-settings #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-settings.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-settings)

This module, intended for use with [Apache Isis](http://isis.apache.org), is a provides two services provide the 
ability to persist configuration settings using Isis' own JDO Objectstore.

With `ApplicationSettingsService` these settings have global scope; for the `UserSettingsService` the settings are 
scoped per user.

The settings themselves are keyed by a simple string, and can store any of boolean, String, int, long and `LocalDate`. 
The implementation persists these values in a single raw format, but the API exposed by the services aims to be type-safe. 

## Screenshots ##

The following screenshots show an example app's usage of the module.

#### Installing the Fixture Data ####

Install sample fixtures:

![](https://raw.github.com/isisaddons/isis-module-settings/master/images/010-install-fixtures.png)

#### App Settings ####

List all (demo) application settings:

![](https://raw.github.com/isisaddons/isis-module-settings/master/images/020-list-appsettings.png)

... listed in a table:

![](https://raw.github.com/isisaddons/isis-module-settings/master/images/030-appsettings.png)

... and inspect detail:

![](https://raw.github.com/isisaddons/isis-module-settings/master/images/040-appsetting-detail.png)

#### User Settings ####

List all (demo) user settings:

![](https://raw.github.com/isisaddons/isis-module-settings/master/images/050-list-usersettings.png)

... listed in a table:

![](https://raw.github.com/isisaddons/isis-module-settings/master/images/060-usersettings.png)

... and inspect detail:

![](https://raw.github.com/isisaddons/isis-module-settings/master/images/070-usersetting-detail.png)


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 7 (nb: Isis currently does not support JDK 8)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-settings.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## Relationship to Apache Isis Core ##

Isis Core 1.6.0 included the `org.apache.isis.module:isis-module-settings:1.6.0` Maven artifact (and its submodules, `isis-module-settings-applib` and `isis-module-settings-impl-jdo`.  This module is a direct copy of that code, with the following changes:

* package names have been altered from `org.apache.isis` to `org.isisaddons.module.settings`
* the `persistent-unit` (in the JDO manifest) has changed from `isis-module-settings` to 
  `org-isisaddons-module-settings-dom`
* for simplicity, the applib and impl submodules have been combined into a single module
  
Otherwise the functionality is identical; warts and all!

Isis 1.7.0 no longer ships with `org.apache.isis.module:isis-module-settings`; instead use this addon module.


## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

#### "Out-of-the-box" ####

To use "out-of-the-box":

* update your classpath by adding this dependency in your dom project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.settings&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-settings-dom&lt;/artifactId&gt;
        &lt;version&gt;1.8.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>

    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
            ...,\
            org.isisaddons.module.settings,\
            ...
</pre>


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


## API ##

### ApplicationSettingsService and ApplicationSettingsServiceRW ###

The module defines two interfaces for application settings.  The first, `ApplicationSettingsService`, provides read-only access:

<pre>
    public interface ApplicationSettingsService {
        ApplicationSetting find(String key);
        List<ApplicationSetting> listAll();
    }
</pre>

The second, `ApplicationSettingsServiceRW`, extends the first and allows settings to be created:

<pre>
public interface ApplicationSettingsServiceRW extends ApplicationSettingsService {
    ApplicationSetting newBoolean(String name, String description, Boolean defaultValue);
    ApplicationSetting newString(String name, String description, String defaultValue);
    ApplicationSetting newLocalDate(String name, String description, LocalDate defaultValue);
    ApplicationSetting newInt(String name, String description, Integer defaultValue);
    ApplicationSetting newLong(String name, String description, Long defaultValue);
}
</pre>

### UserSettingsService and UserSettingsServiceRW ###

The module defines two interfaces for user settings.  These are almost identical to the application settings above, the 
significant difference being each setting is additional identified by the username that 'owns' it.

The first interface, `UserSettingsService`, provides read-only access:

<pre>
public interface UserSettingsService {
    UserSetting find(String user, String key);
    List<UserSetting> listAll();
    List<UserSetting> listAllFor(String user);
}
</pre>

The second, `UserSettingsServiceRW`, extends the first and allows settings to be created:

<pre>
public interface UserSettingsServiceRW extends UserSettingsService {
    UserSetting newBoolean(String user, String name, String description, Boolean defaultValue);
    UserSetting newString(String user, String name, String description, String defaultValue);
    UserSetting newLocalDate(String user, String name, String description, LocalDate defaultValue);
    UserSetting newInt(String user, String name, String description, Integer defaultValue);
    UserSetting newLong(String user, String name, String description, Long defaultValue);
}
</pre>

## Implementation ##

The `ApplicationSettingsServiceJdo` implements `ApplicationSettingsServiceRW` (and therefore also `ApplicationSettingsService`).

Similarly, the `UserSettingsServiceJdo` implements `UserSettingsServiceRW` (and therefore also `UserSettingsService`.

In 1.7.0, it was necessary to explicitly register these services in `isis.properties`, rationale being that the service
contributes functionality that appears in the user interface.  The module also provided "hidden" equivalents
(`ApplicationSettingsServiceJdoHidden` and `UserSettingsServiceJdoHidden`) which could be registered which also
implement the same services, but do not contribute actions to the UI.

In 1.8.0 the above policy is reversed: the `ApplicationSettingsServiceJdo` and `UserSettingsServiceJdo`
services are both automatically registered, and both will provide functionality that will appear in the user interface.
If this is not required, then either use security permissions or write a vetoing subscriber on the event bus to hide
this functionality, eg:

    @DomainService(nature = NatureOfService.DOMAIN)
    public class HideIsisAddonsSettingsFunctionality {

        @Programmatic @PostConstruct
        public void postConstruct() { eventBusService.register(this); }

        @Programmatic @PreDestroy
        public void preDestroy() { eventBusService.unregister(this); }

        @Programmatic @Subscribe
        public void on(final SettingsModule.ActionDomainEvent<?> event) { event.hide(); }

        @Inject
        private EventBusService eventBusService;
    }


The two "hidden" equivalent services are deprecated in 1.8.0.


## Change Log ##

* `1.8.0` - released against Isis 1.8.0.  Services are automatically registered; their UI can be suppressed using subscriptions.
* `1.7.0` - released against Isis 1.7.0
* `1.6.0` - re-released as part of isisaddons, with classes under package `org.isisaddons.module.settings`



## Legal Stuff ##
 
#### License ####

    Copyright 2013~2015 Dan Haywood

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

##  Maven deploy notes ##

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

    sh release.sh 1.9.0 \
                  1.10.0-SNAPSHOT \
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
    git push origin 1.9.0

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).

