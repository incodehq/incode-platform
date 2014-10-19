# isis-wicket-fullcalendar2 #

[![Build Status](https://travis-ci.org/isisaddons/isis-wicket-fullcalendar2.png?branch=master)](https://travis-ci.org/isisaddons/isis-wicket-fullcalendar2)

This component, intended for use with [Apache Isis](http://isis.apache.org)'s Wicket viewer, renders events for a collection of 
entities within a fullpage calendar.  Underneath the covers it uses this [fullcalendar](https://github.com/42Lines/wicket-fullcalendar) widget.

## Screenshots ##

The following screenshots show an example app's usage of the component.

#### Install fixtures ####

Install fixtures for the example app:

![](https://raw.github.com/isisaddons/isis-wicket-fullcalendar2/master/images/010-install-fixtures.png)

#### Parented collection as calendar ####

The todo item's collection contains a list of `Calendarable` entities (also todo items); this is indicated through a button to switch the view:

![](https://raw.github.com/isisaddons/isis-wicket-fullcalendar2/master/images/020-calendar-button-for-parented-collection.png)

Clicking the button shows the same entities on a fullpage calendar:

![](https://raw.github.com/isisaddons/isis-wicket-fullcalendar2/master/images/030-view-items-in-calendar.png)

#### Drill down ####

Clicking on the event in the calendar drills down to the corresponding entity:

![](https://raw.github.com/isisaddons/isis-wicket-fullcalendar2/master/images/040-drill-down.png)

#### Standalone collection as calendar

Invoking an action that returns a list of `Calendarable` entities:

![](https://raw.github.com/isisaddons/isis-wicket-fullcalendar2/master/images/050-view-all.png)

... also results in the button to view in a fullpage calendar:

![](https://raw.github.com/isisaddons/isis-wicket-fullcalendar2/master/images/060-calendar-button-for-standalone-collection.png)

Each item is shown in the calendar view:

![](https://raw.github.com/isisaddons/isis-wicket-fullcalendar2/master/images/070-toggle-calendars.png)

#### Calendars ####

Each entity can provides dates to either a single calendar or to multiple calendars.  In the example app each todo item
exposes its `dueBy` date to a single calendar, named after its `category`:

    @Programmatic
    @Override
    public String getCalendarName() {
        return getCategory().name();
    }

    @Programmatic
    @Override
    public CalendarEvent toCalendarEvent() {
        if(getDueBy() == null) {
            return null;
        }
        return new CalendarEvent(getDueBy().toDateTimeAtStartOfDay(), getCalendarName(), container.titleOf(this));
    }

The full page calendar uses colour coding to indicate the calendars, as well as checkboxes to show/hide each calendar.
Unchecking the calendar toggle hides all events in that calendar:

![](https://raw.github.com/isisaddons/isis-wicket-fullcalendar2/master/images/080-calendar-updated.png)


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 7 (nb: Isis currently does not support JDK 8)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-wicket-fullcalendar2.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## API & Usage ##

Each entity must implement either the `CalendarEventable` interface or the `Calendarable` interface:

### CalendarEventable interface ###

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

### Calendarable interface ###

While the `CalendarEventable` interface will fit many requirements, sometimes an object will have several dates associated with it.  For example, one could imagine an object with start/stop dates, or optionExercise/optionExpiry dates.

The `Calendarable` interface therefore allows the object to return a number of `CalenderEvent`s; each is qualified (identified) by a `calendarName`:

    public interface Calendarable {

        Set<String> getCalendarNames();
        ImmutableMap<String, CalendarEventable> getCalendarEvents();
    }


## How to configure/use ##

You can either use this extension "out-of-the-box", or you can fork this repo and extend to your own requirements. 

To use "out-of-the-box", add this component to your project's `dom` module's `pom.xml`, eg:

    <dependency>
        <groupId>org.isisaddons.wicket.fullcalendar2</groupId>
        <artifactId>isis-wicket-fullcalendar2-cpt</artifactId>
        <version>1.7.0</version>
    </dependency>

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-fullcalendar2-cpt).

If instead you want to extend this component's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml    ` - parent pom
* `cpt        ` - the component implementation
* `fixture    ` - fixtures, holding sample domain object classes and fixture scripts
* `webapp     ` - demo webapp (see above screenshots)

Only the `cpt` project is released to Maven central.  The versions of the other modules 
are purposely left at `0.0.1-SNAPSHOT` because they are not intended to be released.


## Change Log ##

* `1.7.0` - released against Isis 1.7.0
* `1.6.1` - (breaking change) changed package names for API to `org.isisaddons.wicket.fullcalendar2.cpt.applib`
* `1.6.0` - re-released as part of isisaddons, changed package names for API to `org.isisaddons.wicket.fullcalendar2.applib`

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

In addition to Apache Isis, this component depends on:

* `net.ftlines.wicket-fullcalendar:wicket-fullcalendar-core` (ASL v2.0 License)
* http://arshaw.com/fullcalendar/ (MIT License)
* http://jquery.com (MIT License)  


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

    sh release.sh 1.7.0 \
                  1.7.1-SNAPSHOT \
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
before trying again.  Note that in the `cpt`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).
