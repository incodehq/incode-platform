isis-wicket-wickedcharts
========================

[![Build Status](https://travis-ci.org/danhaywood/isis-wicket-wickedcharts.png?branch=master)](https://travis-ci.org/danhaywood/isis-wicket-wickedcharts)

An extension for [Apache Isis](http://isis.apache.org)' [Wicket viewer](http://isis.apache.org/components/viewers/wicket/about.html) with [Wicked Charts](https://code.google.com/p/wicked-charts/).  *Wicked Charts* is in turn an integration between [Apache Wicket](http://wicket.apache.org) and the [Highcharts](http://www.highcharts.com/) JS charting library).

The library provides two separate components/extensions for the Wicket viewer:

* `summarycharts`: render a standalone collection with `BigDecimal` properties as a chart.  (This component can be thought of as an enhancement of the base `summary` view provided by Wicket UI viewer).

* `scalarchart`: renders a standalone scalar value (from an action invocation) as a chart

**Please note that while this project and *Wicked Charts* are licensed under Apache 2.0 License, *Highcharts* itself is only free for non-commercial use.  See [here](http://shop.highsoft.com/highcharts.html) for further details.**


## Screenshots

The following screenshots are taken from the `zzzdemo` app (adapted from Isis' quickstart archetype).

#### Summary Charts

A collection with `BigDecimal` properties:
![](https://raw.github.com/danhaywood/isis-wicket-wickedcharts/master/images/summarychart-tab.png)

renders the returned chart with associated summary data:

![](https://raw.github.com/danhaywood/isis-wicket-wickedcharts/master/images/summarychart.png)


#### Scalar Chart

Result of an action to analyze `ToDoItem`s by their category:

![](https://raw.github.com/danhaywood/isis-wicket-wickedcharts/master/images/piechart.png)



## Summary Charts

### API & Usage

There is no special usage; a standalone collection of any entity with one or more properties of type `BigDecimal` will be rendered using the `summarycharts` extension.


### Maven Configuration

In your project's parent `pom.xml`, add to the `<dependencyManagement>` section:

    <dependencyManagement>
        <dependencies>
            ...
            <dependency>
                <groupId>com.danhaywood.isis.wicket.ui.components</groupId>
                <artifactId>danhaywood-isis-wicket-wickedcharts</artifactId>
                <version>1.0.0-SNAPSHOT</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            ...
        </dependencies>
    </dependencyManagement>

Then, add the dependency in your project's webapp `pom.xml` to the `summarycharts` module:

    <dependencies>
        ...
        <dependency>
            <groupId>com.danhaywood.isis.wicket.ui.components</groupId>
            <artifactId>danhaywood-isis-wicket-wickedcharts-summarycharts</artifactId>
        </dependency>
        ...
    </dependencies> 


## Scalar Chart

### API and Usage

Any action returning the `WickedChart` value type should be rendered as a chart.  The `WickedChart` value type is simply a wrapper around the wicked chart's `Options` class:

    import com.googlecode.wickedcharts.highcharts.options.Options;

    public class WickedChart implements Serializable {

        private Options options;
        
        public WickedChart(Options options) { ... }
        ...
    }

Any chart supported by *Wicked Charts* (see their [showcase](http://wicked-charts.appspot.com/) app) should work.  

### Maven Configuration

In your project's parent `pom.xml`, add to the `<dependencyManagement>` section:

    <dependencyManagement>
        <dependencies>
            ...
            <dependency>
                <groupId>com.danhaywood.isis.wicket</groupId>
                <artifactId>danhaywood-isis-wicket-wickedcharts</artifactId>
                <version>x.y.z</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            ...
        </dependencies>
    </dependencyManagement>

where `x.y.z` is the latest available version (search the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-wickedcharts)).


Add a dependency on the `applib` module In your project's DOM `pom.xml`:

    <dependencies>
        ...
        <dependency>
            <groupId>com.danhaywood.isis.wicket</groupId>
            <artifactId>danhaywood-isis-wicket-wickedcharts-applib</artifactId>
        </dependency>
        ...
    </dependencies> 

Then, add the required dependency in your project's webapp `pom.xml` to the `scalarchart` module:

    <dependencies>
        ...
        <dependency>
            <groupId>com.danhaywood.isis.wicket</groupId>
            <artifactId>danhaywood-isis-wicket-wickedcharts-scalarchart</artifactId>
        </dependency>
        ...
    </dependencies> 

### Limitations

Although the `WickedChart` has value semantics, it will not render as a chart if used as an entity property.

Such a property should be persistable, however.  Therefore a workaround is to hide the property and instead provide an action to show the chart.  For example:

    public class MyEntity {

        private WickedChart chart;
        @Hidden
        public WickedChart getChart() { ... }
        public void setChart(WickedChart chart) { ... }

        public WickedChart showChart() {
            return getChart();
        }
    }
    


## Legal Stuff

### License

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

### Dependencies

    <dependencies>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.apache.isis.core</groupId>
            <artifactId>isis-core-applib</artifactId>
            <version>${isis.version}</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
            <version>1.7</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.apache.isis.viewer</groupId>
            <artifactId>isis-viewer-wicket-ui</artifactId>
            <version>${isis-viewer-wicket.version}</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <!-- in turn, depends on: 
                 * http://highcharts.com/license 
                   (commercial license required unless personal/open source project)  
             -->
            <groupId>com.googlecode.wicked-charts</groupId>
            <artifactId>wicked-charts-wicket6</artifactId>
            <version>${wicked-charts.version}</version>
        </dependency>

    </dependencies>
