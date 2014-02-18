isis-wicket-excel
=================

[![Build Status](https://travis-ci.org/danhaywood/isis-wicket-excel.png?branch=master)](https://travis-ci.org/danhaywood/isis-wicket-excel)

Integrates with [Apache Isis](http://isis/apache.org)' Wicket Viewer, to allow a collection of entities to be downloaded as an Excel spreadsheet (using [Apache POI](http://poi.apache.org)).

See also the [Excel domain service](https://github.com/danhaywood/isis-domainservice-excel), which allows programmatic export and import, eg to support bulk updating/inserting.

## Screenshots

The following screenshots are taken from the `zzzdemo` app (adapted from Isis' quickstart archetype).

![](https://raw.github.com/danhaywood/isis-wicket-excel/master/images/excel-tab.png)

![](https://raw.github.com/danhaywood/isis-wicket-excel/master/images/download-link.png)

![](https://raw.github.com/danhaywood/isis-wicket-excel/master/images/excel.png)

## Maven Configuration

Simply add this component to your classpath, eg:

    <dependency>
        <groupId>com.danhaywood.isis.wicket</groupId>
        <artifactId>danhaywood-isis-wicket-excel</artifactId>
        <version>x.y.z</version>
    </dependency>

where `x.y.z` is the latest available version (search the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-excel)).

You should then find that a new view is provided for all collections of entities (either as returned from an action, or as a parented collection), from which a link to download the spreadsheet can be accessed.

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
            <groupId>org.apache.isis.viewer</groupId>
            <artifactId>isis-viewer-wicket-ui</artifactId>
            <version>${isis-viewer-wicket.version}</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>${poi.version}</version>
        </dependency>
        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>${poi.version}</version>
        </dependency>
        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml-schemas</artifactId>
            <version>${poi.version}</version>
        </dependency>        
    </dependencies>
