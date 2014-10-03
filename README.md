# isis-wicket-wickedcharts #

[![Build Status](https://travis-ci.org/isisaddons/isis-wicket-wickedcharts.png?branch=master)](https://travis-ci.org/isisaddons/isis-wicket-wickedcharts)

This component, intended for use with [Apache Isis](http://isis.apache.org)'s Wicket viewer, integrates [Wicked Charts](https://code.google.com/p/wicked-charts/).  
*Wicked Charts* is in turn an integration between [Apache Wicket](http://wicket.apache.org) and the [Highcharts](http://www.highcharts.com/) JS charting library).

**Please note that while this project and *Wicked Charts* are licensed under Apache 2.0 License, *Highcharts* itself is only free for non-commercial use.  See [here](http://shop.highsoft.com/highcharts.html) for further details.**

There are in fact two separate components:

* `summarycharts`: render a standalone collection with `BigDecimal` properties as a chart.  (This component can be thought of as an enhancement of the base `summary` view provided by Isis Wicket viewer).

* `scalarchart`: renders a standalone scalar value (from an action invocation) as a chart


## Screenshots ##

The following screenshots show an example app's usage of the component.

#### Install fixtures ####

Install fixtures for the example app:

![](https://raw.github.com/isisaddons/isis-wicket-wickedcharts/master/images/010-install-fixtures.png)

Note that the example entity (todo item) has two numeric (`BigDecimal`) properties: 

![](https://raw.github.com/isisaddons/isis-wicket-wickedcharts/master/images/020-entity-with-numeric-properties.png)

#### Summary Charts ####

Invoking an action that returns a collection of entities:

![](https://raw.github.com/isisaddons/isis-wicket-wickedcharts/master/images/030-all-entities.png)

... shows an additional button to view those entities in a summary chart:

![](https://raw.github.com/isisaddons/isis-wicket-wickedcharts/master/images/040-standalone-collection-additional-button-for-summary-chart.png)

Clicking on the button renders a chart where the values of all numeric (`BigDecimal`) properties are plotted:

![](https://raw.github.com/isisaddons/isis-wicket-wickedcharts/master/images/050-summary-chart.png)

#### Scalar Charts ####

Arbitrary charts can be returned from any action.  For example this action:

![](https://raw.github.com/isisaddons/isis-wicket-wickedcharts/master/images/060-arbitrary-charts.png)

... renders a pie chart splitting out the example Todo entities by their category:

![](https://raw.github.com/isisaddons/isis-wicket-wickedcharts/master/images/070-scalar-chart.png)


## API & Usage ##

### Summary Charts ###

There is no special usage; a standalone collection of any entity with one or more properties of type `BigDecimal` 
will be rendered using the `summarycharts` extension.


### Scalar Chart ###

Any action returning the `WickedChart` value type should be rendered as a chart.  The `WickedChart` value type is 
simply a wrapper around the wicked chart's `Options` class:

    import com.googlecode.wickedcharts.highcharts.options.Options;

    public class WickedChart implements Serializable {

        private Options options;
        
        public WickedChart(Options options) { ... }
        ...
    }

Any chart supported by *Wicked Charts* (see their [showcase](http://wicked-charts.appspot.com/) app) should work.  


## How to configure/use ##

* In your project's `dom` module's `pom.xml`, add:

    <dependency>
        <groupId>org.isisaddons.wicket.wickedcharts</groupId>
        <artifactId>isis-wicket-wickedcharts-cpt</artifactId>
        <version>1.6.0</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
    ....

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-wickedcharts-cpt).

If instead you want to extend these components' functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml    ` - parent pom
* `cpt        ` - the component implementation
* `fixture    ` - fixtures, holding a sample domain objects and fixture scripts
* `webapp     ` - demo webapp (see above screenshots)

Only the `cpt` project (and its submodules) is released to Maven central.  The versions of the other modules 
are purposely left at `0.0.1-SNAPSHOT` because they are not intended to be released.


## Limitations ##

Although the `WickedChart` class (in the `scalarchart`'s API) has value semantics, it will (currently) not render as a 
chart if used as an entity property.

Such a property should be persistable, however.  

Therefore a workaround is to hide the property and instead provide an action to show the chart.  

For example:

    public class MyEntity {

        private WickedChart chart;
        @Hidden
        public WickedChart getChart() { ... }
        public void setChart(WickedChart chart) { ... }

        public WickedChart showChart() {
            return getChart();
        }
    }
    

## Change Log ##

* `1.6.0` - re-released as part of isisaddons, changed package names for API to `org.isisaddons.wicket.wickedcharts`


## Legal Stuff ##

**Please note that while this project and *Wicked Charts* are licensed under Apache 2.0 License, *Highcharts* itself 
is only free for non-commercial use.  See [here](http://shop.highsoft.com/highcharts.html) for further details.**

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

In addition to Apache Isis, this component depends on:

* `commons-codec:commons-codec` (ASL v2.0 License)
* `com.googlecode.wicked-charts:wicked-charts-wicket6` (ASL v2.0 License)
* http://highcharts.com/license  (commercial license required unless personal/open source project)  


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

