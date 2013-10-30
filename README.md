isis-wicket-fullcalendar2
=========================

[![Build Status](https://travis-ci.org/danhaywood/isis-wicket-fullcalendar2.png?branch=master)](https://travis-ci.org/danhaywood/isis-wicket-fullcalendar2)

Extension for Apache Isis' Wicket Viewer, to render events for a collection of entities within a fullpage calendar.  Uses https://github.com/42Lines/wicket-fullcalendar 

Each entity must implement either the `Calendarable` or the `CalendarEventable` interface:

* the `Calendarable` interface allows the object to return a number of `CalenderEvent`s; each is qualified (identified) by a `calendarName`

* the `CalendarEventable` interface is more straightforward, allowing the object to return a single `CalendarEvent`. 

The `CalendarEvent` is a value type representing the data to be rendered on the calender.   As well as a date (obviously) and title, each event also has a `calendarName` such that sets of events in a given named calendar can be toggled on or off.


### Screenshots

TODO


### Configuration

In your project's parent `pom.xml`, add to the `<dependencyManagement>` section:

    <dependencyManagement>
        <dependencies>
            ...
            <dependency>
                <groupId>com.danhaywood.isis.wicket</groupId>
                <artifactId>danhaywood-isis-wicket-fullcalendar2</artifactId>
                <version>x.y.z</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            ...
        </dependencies>
    </dependencyManagement>

where `x.y.z` is the latest available version (search the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-fullcalendar2)).

In your project's DOM `pom.xml`, add a dependency on the `applib` module:

    <dependencies>
        ...
        <dependency>
            <groupId>com.danhaywood.isis.wicket</groupId>
            <artifactId>danhaywood-isis-wicket-fullcalendar2-applib</artifactId>
        </dependency>
        ...
    </dependencies> 

In your project's webapp `pom.xml`, add a dependency on the `ui` module:

    <dependencies>
        ...
        <dependency>
            <groupId>com.danhaywood.isis.wicket</groupId>
            <artifactId>danhaywood-isis-wicket-fullcalendar2-ui</artifactId>
        </dependency>
        ...
    </dependencies> 


### Usage

Your entity can either implement  `com.danhaywood.isis.wicket.fullcalendar2.applib.Calendarable` or it can implement `com.danhaywood.isis.wicket.fullcalendar2.applib.CalendarEventable`.  

TODO... examples



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
            <!-- in turn, depends on: 
                 * http://arshaw.com/fullcalendar/ (MIT License)
                 * http://jquery.com (MIT License)  
             -->
            <groupId>net.ftlines.wicket-fullcalendar</groupId>
            <artifactId>wicket-fullcalendar-core</artifactId>
            <version>${wicket-fullcalendar.version}</version>
        </dependency>
    </dependencies>