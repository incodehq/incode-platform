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


## Relationship to Apache Isis Core ##

Isis Core 1.6.0 included the `org.apache.isis.core:isis-module-settings:1.6.0` Maven artifact (and its submodules,
`isis-module-settings-applib` and `isis-module-settings-impl-jdo`.  This module is a direct copy of that code, with 
the following changes:

* package names have been altered from `org.apache.isis` to `org.isisaddons.module.settings`
* the `persistent-unit` (in the JDO manifest) has changed from `isis-module-settings` to 
  `org-isisaddons-module-settings-dom`
* for simplicity, the applib and impl submodules have been combined into a single module
  
Otherwise the functionality is identical; warts and all!

At the time of writing the plan is to remove these modules from Isis Core (so it won't be in Isis 1.7.0), and instead 
continue to develop it solely as one of the [Isis Addons](http://www.isisaddons.org) modules.


## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

To use "out-of-the-box":

* update your classpath by adding this dependency in your dom project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.settings&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-settings-dom&lt;/artifactId&gt;
        &lt;version&gt;1.6.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services = ...,\
            org.isisaddons.module.settings.dom.jdo.ApplicationSettingsServiceJdo,\
            org.isisaddons.module.settings.dom.jdo.UserSettingsServiceJdo,\
            ...
</pre>

As the screenshots above show, these two services appear in the user interface.  If instead you want to interact
with the services programmatically, you can _instead_ register:

<pre>
    isis.services = ...,\
            org.isisaddons.module.settings.dom.jdo.ApplicationSettingsServiceJdoHidden,\
            org.isisaddons.module.settings.dom.jdo.UserSettingsServiceJdoHidden,\
            ...
</pre>

Note that none of these services are annotated with `@DomainService` (to enable the above choice); hence the necessity 
to register the appropriate implementation in `isis.properties`.


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

The `ApplicationSettingsServiceJdo` implements `ApplicationSettingsServiceRW` (and therefore also `ApplicationSettingsService`.

The `ApplicationSettingsServiceJdoHidden` is implemented as a subclass of `ApplicationSettingsServiceJdo`; it hides all 
 actions from the user interface.
 
Similarly, the `UserSettingsServiceJdo` implements `UserSettingsServiceRW` (and therefore also `UserSettingsService`.

The `UserSettingsServiceJdoHidden` is implemented as a subclass of `UserSettingsServiceJdo`; it hides all 
 actions from the user interface.


## Change Log ##

* `1.6.0` - re-released as part of isisaddons, with classes under package `org.isisaddons.module.settings`



## Legal Stuff ##
 
#### License ####

    Copyright 2013~2014 Dan Haywood

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

    sh release.sh 1.6.0 \
                  1.6.1-SNAPSHOT \
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

    git push

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).

