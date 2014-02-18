isis-wicket-fullcalendar2
=========================

[![Build Status](https://travis-ci.org/danhaywood/isis-wicket-fullcalendar2.png?branch=master)](https://travis-ci.org/danhaywood/isis-wicket-fullcalendar2)

Extension for Apache Isis' Wicket Viewer, to render events for a collection of entities within a fullpage calendar.  Underneath the covers it uses this [fullcalendar](https://github.com/42Lines/wicket-fullcalendar) widget.

## Screenshots

The following screenshots are taken from the `zzzdemo` app (adapted from Isis' quickstart archetype).  See below for further details.

### Standalone collection

![](https://raw.github.com/danhaywood/isis-wicket-fullcalendar2/master/images/standalone-collection.png)

### Parented collection in a custom dashboard view model

![](https://raw2.github.com/danhaywood/isis-wicket-fullcalendar2/master/images/dashboard.png)

### Parented collection in a regular entity

![](https://raw.github.com/danhaywood/isis-wicket-fullcalendar2/master/images/parented-collection.png)


## API & Usage

Each entity must implement either the `CalendarEventable` interface or the `Calendarable` interface:

### `CalendarEventable` interface

Of the two interfaces, `CalendarEventable` interface is the simpler, allowing the object to return a single `CalendarEvent`:

    public interface CalendarEventable {

        String getCalendarName();
        CalendarEvent toCalendarEvent();

    }

where:

* the `getCalendarName()` is used to group similar events together; in the UI these correspond to checkboxes rendered near the top.
* the `toCalendarEvent()` returns a `CalendarEvent` value type representing the data to be rendered on the calender. 

`CalendarEvent` itself is:

    public class CalendarEvent implements Serializable {

        private final DateTime dateTime;
        private final String calendarName;
        private final String title;
        private final String notes;
        
        public CalendarEvent(
                final DateTime dateTime, 
                final String calendarName, 
                final String title) {
            this(dateTime, calendarName, title, null);
        }

        public CalendarEvent(
                final DateTime dateTime, 
                final String calendarName, 
                final String title, 
                final String notes) {
            this.dateTime = dateTime;
            this.calendarName = calendarName;
            this.title = title;
            this.notes = notes;
        }

        ...
    }

In the demo app, the `ToDoItem` implements `CalendarEventable`.

### `Calendarable` interface

While the `CalendarEventable` interface will fit many requirements, sometimes an object will have several dates associated with it.  For example, one could imagine an object with start/stop dates, or optionExercise/optionExpiry dates.

The `Calendarable` interface therefore allows the object to return a number of `CalenderEvent`s; each is qualified (identified) by a `calendarName`:

    public interface Calendarable {

        Set<String> getCalendarNames();
        ImmutableMap<String, CalendarEventable> getCalendarEvents();
    }


## Maven Configuration

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