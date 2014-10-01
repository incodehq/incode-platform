# isis-wicket-gmap3 #

[![Build Status](https://travis-ci.org/isisaddons/isis-wicket-gmap3.png?branch=master)](https://travis-ci.org/isisaddons/isis-wicket-gmap3)

This component, intended for use with [Apache Isis](http://isis.apache.org)'s Wicket viewer, allows an entity or collection of 
entities to be rendered within a map (using google's [gmap3](https://developers.google.com/maps/documentation/javascript/) API).

## Screenshots ##

The following screenshots show the example app's usage of the component.

#### Install fixtures ####

Start by installing fixtures:

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/010-install-fixtures.png)

#### Parented collection as gmap ####

The todo item's collection contains a list of `Locatable` entities (also todo items); this is indicated through a button to switch the view:

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/020-gmap-button-available-on-parented-collection.png)

Clicking the button shows the same entities on a gmap3:

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/030-view-collection-in-gmap3-next-update-location.png)

#### Update location using service ####

The previous screensot shows the todo item also provides an "update location" action:

    public Gmap3WicketToDoItem updateLocation(@Named("Address") final String address) {
        final Location location = this.locationLookupService.lookup(address);
        setLocation(location);
        return this;
    }

When invoked:

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/040-update-location-invoke.png)

... updates the location:

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/050-location-updated.png)

#### Standalone location as gmap ####

Invoking an action that returns a list of `Locatable` entities:

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/060-view-all-items.png)

... also results in the button to view in a gmap3:

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/070-gmap-button-available-on-standalone-collection.png)

... which then renders the items in a map.  Note the tooltips:

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/080-view-collection-in-gmap3.png)

#### Click through ####

Clicking on a map marker drills down to the entity:

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/090-click-through-to-entity.png)

## API & Usage ##

### Rendering objects on a map ###

Make your entity implement `org.isisaddons.wicket.gmap3.applib.Locatable`, such that it provides a `Location` property of type `org.isisaddons.wicket.gmap3.applib.Location`.

This property will need to be annotated as `@javax.jdo.annotations.Persistent`. 

For example:

    import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
    import org.isisaddons.wicket.gmap3.cpt.applib.Location;

    public class ToDoItem implements Locatable {

        ...

        @javax.jdo.annotations.Persistent
        private Location location;
        
        @MemberOrder(name="Detail", sequence = "10")
        @Optional
        public Location getLocation() { 
            return location;
        }
        public void setLocation(Location location) {
            this.location = location;
        }
    }

You should then find that any collections of entities that have `Locatable` properties (either returned from an action, or as a parented collection) will be rendered in a map.

### End-user entry of `Location`s ###

By injecting the provided `LocationLookupService` domain service, you can write an action to lookup the `Location`.
For example:

    public void lookupLocation(@Named("Description") String description) {
        setLocation(locationLookupService.lookup(description));
    }

Alternatively, the `Location` can also be specified directly as a string.  The format is:

     mmm.mmm;nnn.nnn

where:

* `mmm.mmm` is the latitude, and
* `nnn.nnn` is the longitude 


## How to configure/use ##

You can either use this component "out-of-the-box", or you can fork this repo and extend to your own requirements. 

To use "out-of-the-box", add the component to your project's `dom` module's `pom.xml`:

    <dependency>
        <groupId>org.isisaddons.wicket.gmap3</groupId>
        <artifactId>isis-wicket-gmap3-cpt</artifactId>
        <version>1.6.0</version>
    </dependency>

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-gmap3-cpt).

If instead you want to extend this component's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml    ` - parent pom
* `cpt        ` - the component implementation
* `fixture    ` - fixtures, holding sample domain object classes and fixture scripts
* `webapp     ` - demo webapp (see above screenshots)

Only the `cpt` project is released to Maven central.  The versions of the other modules 
are purposely left at `0.0.1-SNAPSHOT` because they are not intended to be released.


## Change Log ##

* `1.6.0` - re-released as part of isisaddons, with classes under package `org.isisaddons.wicket.gmap3`


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

* `org.wicketstuff:wicketstuff-gmap3` (ASL v2.0 License)


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
before trying again.  Note that in the `cpt`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).
