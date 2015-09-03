# isis-wicket-wickedcharts #

[![Build Status](https://travis-ci.org/isisaddons/isis-wicket-wickedcharts.png?branch=master)](https://travis-ci.org/isisaddons/isis-wicket-wickedcharts)

This component, intended for use with [Apache Isis](http://isis.apache.org)'s Wicket viewer, integrates [Wicked Charts](https://code.google.com/p/wicked-charts/).  
*Wicked Charts* is in turn an integration between [Apache Wicket](http://wicket.apache.org) and the [Highcharts](http://www.highcharts.com/) JS charting library).

**Please note that while this project and *Wicked Charts* are licensed under Apache 2.0 License, *Highcharts* itself is only free for non-commercial use.  See [here](http://shop.highsoft.com/highcharts.html) for further details.**

There are in fact two separate components:

* `summarycharts`: render a standalone collection with `BigDecimal` properties as a chart.  (This component can be thought of as an enhancement of the base `summary` view provided by Isis Wicket viewer).

* `scalarchart`: renders a standalone scalar value (from an action invocation) as a chart


## Screenshots ##

The following screenshots show the example app's usage of the component with some sample fixture data:

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


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 8 (>= 1.9.0) or Java JDK 7 (<= 1.8.0)
** note that the compile source and target remains at JDK 7
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-wicket-wickedcharts.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


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

You can either use this component "out-of-the-box", or you can fork this repo and extend to your own requirements.

#### "Out-of-the-box" ####

To use "out-of-the-box", add the component to your project's `dom` module's `pom.xml`:

    <dependency>
        <groupId>org.isisaddons.wicket.wickedcharts</groupId>
        <artifactId>isis-wicket-wickedcharts-cpt</artifactId>
        <version>1.9.0</version>
    </dependency>

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-wickedcharts-cpt).


#### "Out-of-the-box" (-SNAPSHOT) ####

If you want to use the current `-SNAPSHOT`, then the steps are the same as above, except:

* when updating the classpath, specify the appropriate -SNAPSHOT version:

<pre>
    &lt;version&gt;1.10.0-SNAPSHOT&lt;/version&gt;
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

* `1.9.0` - released against Isis 1.9.0
* `1.8.0` - released against Isis 1.8.0
* `1.7.0` - released against Isis 1.7.0
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

    sh release.sh 1.10.0 \
                  1.11.0-SNAPSHOT \
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
    git push origin 1.10.0

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).

