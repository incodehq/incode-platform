isis-domainservice-excel
========================

[![Build Status](https://travis-ci.org/danhaywood/isis-domainservice-excel.png?branch=master)](https://travis-ci.org/danhaywood/isis-domainservice-excel)

Integrates with [Apache Isis](http://isis/apache.org)', providing a domain service so that a collection of (view model) object scan be exported to an Excel spreadsheet, or recreated by importing from Excel.  The underlying technology used is [Apache POI](http://poi.apache.org).

### Usage

Add this component to your classpath, eg:

    <dependency>
        <groupId>com.danhaywood.isis.domainservice</groupId>
        <artifactId>danhaywood-isis-domainservice-excel</artifactId>
        <version>x.y.z</version>
    </dependency>

where `x.y.z` is the latest available version (search the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-domainservice-excel)).



## Legal Stuff

### License

    Copyright 2013 Dan Haywood

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
