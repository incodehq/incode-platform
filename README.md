# isis-module-togglz #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-togglz.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-togglz)

This module, intended for use with [Apache Isis](http://isis.apache.org), provides ...

The module consists of ...

## Screenshots ##

The following screenshots show an example app's usage of the module.

#### Login as administrator ####

![](https://raw.github.com/isisaddons/isis-module-togglz/master/images/010-login-as-admin.png)

#### Feature disabled ####

In the demo app the "Togglz Demo Objects" service has three actions, all of which are protected behind features.  Two of these (for "create" and "listAll") are enabled by default, but one (for "findByName") is disabled by default, meaning that the action is suppressed from the UI:

![](https://raw.github.com/isisaddons/isis-module-togglz/master/images/020-findByName-feature-disabled.png)

#### Togglz Console ####

Users with the appropriate role ("isis-module-togglz-admin" can access the Togglz console, which lists all features:

![](https://raw.github.com/isisaddons/isis-module-togglz/master/images/030-togglz-console-list-all.png)

Using the console, we can edit the feature:

![](https://raw.github.com/isisaddons/isis-module-togglz/master/images/040-enable-feature.png)

so it is now enabled:

![](https://raw.github.com/isisaddons/isis-module-togglz/master/images/050-feature-enabled.png)

#### Feature enabled ####

Back in the demo app the feature ("findByName") is now visible:

![](https://raw.github.com/isisaddons/isis-module-togglz/master/images/060-findByName-feature-enabled.png)

#### Feature persistence ####

The module uses Isis addons' [settings module](http://github.com/isisaddons/isis-module-settings) for feature persistence.  

![](https://raw.github.com/isisaddons/isis-module-togglz/master/images/070-list-app-settings.png)

Each feature's state is serialized to/from JSON:

![](https://raw.github.com/isisaddons/isis-module-togglz/master/images/080-setting-created-for-feature.png)


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 8
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-togglz.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

#### "Out-of-the-box" ####

To use "out-of-the-box":

* update the classpath in your project's `dom` module `pom.xml` to reference the togglz library:

<pre>
    &lt;properties&gt;
        &lt;togglz.version&gt;2.1.0.Final&lt;/togglz.version&gt;
    &lt;/properties&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.togglz&lt;/groupId&gt;
        &lt;artifactId&gt;togglz-core&lt;/artifactId&gt;
        &lt;version&gt;${togglz.version}&lt;/version&gt;
    &lt;/dependency&gt;
</pre>


* as described in the [Togglz documentation](http://www.togglz.org/documentation/overview.html), create a "feature enum" class that enumerates your features.  This should extend from `org.togglz.core.Feature`.  

  For example, the demo app's feature enum class is:

<pre>
  public enum TogglzDemoFeature implements org.togglz.core.Feature {
    
    @Label("Enable create")
    @EnabledByDefault
    create,

    @Label("Enable findByName")
    findByName,

    @Label("Enable listAll")
    @EnabledByDefault
    listAll;

    public boolean isActive() {
      return FeatureContext.getFeatureManager().isActive(this);
    }
  }
</pre>

* use your feature class in your app as required.

  For example, the demo app uses its feature enum to selectively hide actions of the `TogglzDemoObjects` domain service:

<pre>
  public class TogglzDemoObjects {
    ...
    public List<TogglzDemoObject> listAll() { ... }
    public boolean hideListAll() {
      return !TogglzDemoFeature.listAll.isActive();
    }
  }
</pre>

* update your classpath by adding this dependency in your project's `webapp` (*not* `dom` !) module's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.togglz&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-togglz-glue&lt;/artifactId&gt;
        &lt;version&gt;1.9.0&lt;/version&gt;
    &lt;/dependency&gt;

    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.security&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-security-dom&lt;/artifactId&gt;
        &lt;version&gt;${isis-module-security.version}&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.settings&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-settings-dom&lt;/artifactId&gt;
        &lt;version&gt;${isis-module-settings.version}&lt;/version&gt;
    &lt;/dependency&gt;

</pre>

* in your project's `webapp` module, write a subclass of `TogglzModuleFeatureManagerProviderAbstract` (provided by this module) that registers your feature enum:

<pre>
  public class CustomTogglzModuleFeatureManagerProvider extends TogglzModuleFeatureManagerProviderAbstract {
    protected CustomTogglzModuleFeatureManagerProvider() {
      super(TogglzDemoFeature.class);
    }
  }
</pre>

* in your project's `webapp` module, in `src/main/resources`, register the provider by creating a file `META-INF/services/org.togglz.core.spi.FeatureManagerProvider` whose contents is the fully qualified class name of your feature manager provider implementation.

  For example, the demo app's file consists of:
  
<pre>
  org.isisaddons.module.togglz.webapp.CustomTogglzModuleFeatureManagerProvider
</pre>

* in your project's `webapp` module, update your `WEB-INF/isis.properties`.

  This module uses Isis Addons' [settings module](https://github.com/isisaddons/isis-module-settings) for feature persistence:

<pre>
  isis.services.ServicesInstallerFromAnnotation.packagePrefix=\
                                ...\        
                                org.isisaddons.module.settings,\
                                ...
</pre>

* in your project's `webapp` module, update your `WEB-INF/web.xml`, after the Shiro configuration but before Isis' configuration (so that the filters are applied in the order Shiro -> Togglz -> Isis):

<pre>
    &lt;!-- bootstrap Togglz --&gt;
    &lt;context-param&gt;
        &lt;param-name&gt;org.togglz.FEATURE_MANAGER_PROVIDED&lt;/param-name&gt;
        &lt;!-- prevent the filter from bootstrapping so is done lazily later once Isis has itself bootstrapped --&gt;
        &lt;param-value&gt;true&lt;/param-value&gt;
    &lt;/context-param&gt;

    &lt;filter&gt;
        &lt;filter-name&gt;TogglzFilter&lt;/filter-name&gt;
        &lt;filter-class&gt;org.togglz.servlet.TogglzFilter&lt;/filter-class&gt;
    &lt;/filter&gt;
    &lt;filter-mapping&gt;
        &lt;filter-name&gt;TogglzFilter&lt;/filter-name&gt;
        &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
    &lt;/filter-mapping&gt;
</pre>

* in your `integtests` module, update the `pom.xml` for togglz's JUnit support:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.togglz&lt;/groupId&gt;
        &lt;artifactId&gt;togglz-junit&lt;/artifactId&gt;
        &lt;scope&gt;test&lt;/scope&gt;
    &lt;/dependency&gt;
</pre>

* also in your `integtests` module, make sure that the `TogglzRule` (documented [here](http://www.togglz.org/documentation/testing.html) on the togglz website)  is enabled for any tests that depend on features.

  In the demo app, this means adding the following to `TogglzModuleIntegTest` base class:

<pre>
  @Rule
  public TogglzRule togglzRule = TogglzRule.allEnabled(TogglzDemoFeature.class);
</pre>

* optional: if you want to install the Togglz console, then in your project's `webapp` module, update your `WEB-INF/web.xml`:

<pre>
    &lt;!-- enable the togglz console (for FeatureToggleService) --&gt;
    &lt;servlet&gt;
        &lt;servlet-name&gt;TogglzConsoleServlet&lt;/servlet-name&gt;
        &lt;servlet-class&gt;org.togglz.console.TogglzConsoleServlet&lt;/servlet-class&gt;
    &lt;/servlet&gt;
    &lt;servlet-mapping&gt;
        &lt;servlet-name&gt;TogglzConsoleServlet&lt;/servlet-name&gt;
        &lt;url-pattern&gt;/togglz/*&lt;/url-pattern&gt;
    &lt;/servlet-mapping&gt;
</pre>

  The togglz console will be available at http://localhost:8080/togglz/index

* if you have configured the Togglz console (above), then you'll also need to setup users to have `isis-module-togglz-admin` role.

  The demo app uses simple Shiro-based configuration, which means updating the `WEB-INF/shiro.ini` file, eg:
  
<pre>
  sven = pass, admin_role, isis-module-togglz-admin
</pre>

  If you are using some other security mechanism, eg Isis addons [security module](https://github.com/isisaddons/isis-module-security), then define a role with the same name and grant to users.


Notes:
* Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-togglz-glue)).
* Make sure the `togglz.version` defined in your `dom` module matches the one used in the version of the `isis-module-togglz-glue` module (currently `2.1.0.Final`).

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

### TogglzService ###

The `TogglzService` defines the following API:

<pre>
public interface TogglzService {
}
</pre>


## Implementation ##

## Supporting Services ##

## Related Modules/Services ##

... referenced by the [Isis Add-ons](http://www.isisaddons.org) website.


## Known issues ##


## Change Log ##

* `1.x.x` - released against Isis 1.x.x.


## Legal Stuff ##
 
#### License ####

    Copyright 2014-2015 Dan Haywood

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
