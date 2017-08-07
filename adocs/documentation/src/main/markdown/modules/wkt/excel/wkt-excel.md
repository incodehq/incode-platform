# isis-wicket-excel #
:_basedir: ../../../
:_imagesdir: images/

[![Build Status](https://travis-ci.org/isisaddons/isis-wicket-excel.png?branch=master)](https://travis-ci.org/isisaddons/isis-wicket-excel)

This component, intended for use with [Apache Isis](http://isis.apache.org)'s Wicket viewer, allows a collection of 
entities to be downloaded as an Excel spreadsheet (using [Apache POI](http://poi.apache.org)).

See also the [Isis Addons Excel module](https://github.com/isisaddons/isis-module-excel), which allows programmatic 
export and import, eg to support bulk updating/inserting.

## Screenshots ##

The module contributes an additional "view" for both standalone and parented collections:

For example, assuming some fixtures have been installed:

![](https://raw.github.com/isisaddons/isis-wicket-excel/master/images/010-install-fixtures.png)

Then the standalone collection of all incomplete todo items shows
an additional link to select the excel view:

![](https://raw.github.com/isisaddons/isis-wicket-excel/master/images/020-excel-tab.png)

In both cases this view is simply a button to download the collection as an Excel spreadsheet:

![](https://raw.github.com/isisaddons/isis-wicket-excel/master/images/030-download-link.png)

And the spreadsheet contains the contents of the collection:

![](https://raw.github.com/isisaddons/isis-wicket-excel/master/images/040-excel.png)


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 8 (>= 1.9.0) or Java JDK 7 (<= 1.8.0)
** note that the compile source and target remains at JDK 7
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-wicket-excel.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## How to configure/use ##

You can either use this extension "out-of-the-box", or you can fork this repo and extend to your own requirements. 

#### "Out-of-the-box" ####

To use "out-of-the-box", simply add this component to your classpath, eg:

    <dependency>
        <groupId>org.isisaddons.wicket.excel</groupId>
        <artifactId>isis-wicket-excel-cpt</artifactId>
        <version>1.13.0</version>
    </dependency>

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-excel-cpt).

You should then find that a new view is provided for all collections of entities (either as returned from an action, 
or as a parented collection), from which a link to download the spreadsheet can be accessed.  Check for later releases 
by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-excel-cpt).


#### "Out-of-the-box" (-SNAPSHOT) ####

If you want to use the current `-SNAPSHOT`, then the steps are the same as above, except:

* when updating the classpath, specify the appropriate -SNAPSHOT version:

<pre>
    &lt;version&gt;1.14.0-SNAPSHOT&lt;/version&gt;
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

If instead you want to extend this component's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml    ` - parent pom
* `cpt        ` - the component implementation
* `fixture    ` - fixtures, containing sample domain object classes and fixture scripts
* `webapp     ` - demo webapp (see above screenshots)

Only the `cpt` project is released to Maven central.  The versions of the other modules 
are purposely left at `0.0.1-SNAPSHOT` because they are not intended to be released.


## Related Modules ##

See also the [Excel module](https://github.com/isisaddons/isis-module-excel), which provides programmatic ability to
export or import objects to/from an Excel spreadsheet.


## Change Log ##

* `1.13.0` - released against Isis 1.13.0
* `1.12.0` - released against Isis 1.12.0
* `1.11.0` - released against Isis 1.11.0
* `1.10.0` - released against Isis 1.10.0
* `1.9.0` - released against Isis 1.9.0
* `1.8.0` - released against Isis 1.8.0
* `1.7.0` - released against Isis 1.7.0
* `1.6.1` - (breaking change) changed package names for API to `org.isisaddons.wicket.excel.cpt.applib`
* `1.6.0` - re-released as part of isisaddons, changed package names for API to `org.isisaddons.wicket.excel.applib`


## Legal Stuff ##

#### License ####

    Copyright 2013~2016 Dan Haywood

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

In addition to Apache Isis, this component depends on:

* `org.apache.poi:poi` (ASL v2.0 License)
* `org.apache.poi:poi-ooxml` (ASL v2.0 License)
* `org.apache.poi:poi-ooxml-schemas` (ASL v2.0 License)


##  Maven deploy notes ##

Only the `cpt` module is deployed, and is done so using Sonatype's OSS support (see 
[user guide](http://central.sonatype.org/pages/apache-maven.html)).

#### Release to Sonatype's Snapshot Repo ####

To deploy a snapshot, use:

    pushd cpt
    mvn clean deploy
    popd

The artifacts should be available in Sonatype's 
[Snapshot Repo](https://oss.sonatype.org/content/repositories/snapshots).

#### Release an Interim Build ####

If you have commit access to this project (or a fork of your own) then you can create interim releases using the `interim-release.sh` script.

The idea is that this will - in a new branch - update the `dom/pom.xml` with a timestamped version (eg `1.13.0.20161017-0738`).
It then pushes the branch (and a tag) to the specified remote.

A CI server such as Jenkins can monitor the branches matching the wildcard `origin/interim/*` and create a build.
These artifacts can then be published to a snapshot repository.

For example:

    sh interim-release.sh 1.14.0 origin

where

* `1.14.0` is the base release
* `origin` is the name of the remote to which you have permissions to write to.


#### Release to Maven Central ####

The `release.sh` script automates the release process.  It performs the following:

* performs a sanity check (`mvn clean install -o`) that everything builds ok
* bumps the `pom.xml` to a specified release version, and tag
* performs a double check (`mvn clean install -o`) that everything still builds ok
* releases the code using `mvn clean deploy`
* bumps the `pom.xml` to a specified release version

For example:

    sh release.sh 1.13.0 \
                  1.14.0-SNAPSHOT \
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
    git push origin 1.13.0

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `cpt`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).
