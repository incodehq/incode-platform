# isis-wicket-gmap3 #

[![Build Status](https://travis-ci.org/isisaddons/isis-wicket-gmap3.png?branch=master)](https://travis-ci.org/isisaddons/isis-wicket-gmap3)

This component, intended for use with [Apache Isis](http://isis.apache.org)'s Wicket viewer, allows an entity or collection of 
entities to be rendered within a map (using google's [gmap3](https://developers.google.com/maps/documentation/javascript/) API).

## Screenshots ##

The following screenshots show an example app's usage of the component.

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/screenshot-1.png)

![](https://raw.github.com/isisaddons/isis-wicket-gmap3/master/images/screenshot-2.png)


## API & Usage ##

### Rendering objects on a map ###

Make your entity implement `org.isisaddons.wicket.gmap3.applib.Locatable`, such that it provides a `Location` property of type `org.isisaddons.wicket.gmap3.applib.Location`.

This property will need to be annotated as `@javax.jdo.annotations.Persistent`. 

For example:

    import org.isisaddons.wicket.gmap3.applib.Locatable;
    import org.isisaddons.wicket.gmap3.applib.Location;

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

If you have configured the `LocationLookupService` (recommended), then you can write an action to lookup the `Location` using the service injected into some entity.  For example:

    public void lookupLocation(@Named("Description") String description) {
        setLocation(locationLookupService.lookup(description));
    }

Alternatively, you can allow the `Location` to be specified as a string.  The format is:

     mmm.mmm;nnn.nnn

where:

* `mmm.mmm` is the latitute, and
* `nnn.nnn` is the longitude 


## How to configure/use ##

You can either use this component "out-of-the-box", or you can fork this repo and extend to your own requirements. 

To use "out-of-the-box", first add this component to your classpath:

* in your project's parent module's `pom.xml`, add to the `<dependencyManagement>` section:

<pre>
    &lt;dependencyManagement&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;org.isisaddons.wicket.gmap3&lt;/groupId&gt;
            &lt;artifactId&gt;isis-wicket-gmap3-cpt&lt;/artifactId&gt;
            &lt;version&gt;1.6.0&lt;/version&gt;
            &lt;type&gt;pom&lt;/type&gt;
            &lt;scope&gt;import&lt;/scope&gt;
        &lt;/dependency&gt;
        ....
    &lt;/dependencyManagement&gt;
</pre>


* in your project's `dom` module's `pom.xml`, add a dependency on the `applib` module:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.wicket.gmap3&lt;/groupId&gt;
        &lt;artifactId&gt;isis-wicket-gmap3-cpt-applib&lt;/artifactId&gt;
    &lt;/dependency&gt;
</pre>

* in your project's `webapp` module's `pom.xml`, add a dependency on the `ui` module:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.wicket.gmap3&lt;/groupId&gt;
        &lt;artifactId&gt;isis-wicket-gmap3-cpt-ui&lt;/artifactId&gt;
    &lt;/dependency&gt;
</pre>

* although not mandatory, you might also want to use the `LocationLookupService` that's provided, allowing for simple descriptions (eg "10 Downing Street, London") to be converted into `Location`s.  Add this dependency in your project's own `dom` module's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.wicket.gmap3&lt;/groupId&gt;
        &lt;artifactId&gt;isis-wicket-gmap3-cpt-dom&lt;/artifactId&gt;
    &lt;/dependency&gt;
</pre>

Notes:
* check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-gmap3-cpt).




If using the `LocationLookupService`, then update the `WEB-INF\isis.properties` file:

<pre>
    isis.services = ...,\
                    org.isisaddons.wicket.gmap3.service.LocationLookupService,\
                    ...
</pre>

If instead you want to extend this component's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml    ` - parent pom
* `cpt        ` - the component' own parent pom
* `    applib ` - the component's API (to reference from `dom` project)
* `    dom    ` - the component's service implementation (to optionally reference from `dom` project)
* `    ui     ` - the component's UI implementation
* `fixture    ` - fixtures, holding a sample domain objects and fixture scripts
* `webapp     ` - demo webapp (see above screenshots); depends on `ext` and `fixture`
* `webapptests` - UI tests for the component; depends on `webapp`

Only the `cpt` project (and its submodules) is released to Maven central.  The versions of the other modules 
are purposely left at `0.0.1-SNAPSHOT` because they are not intended to be released.

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

Only the `dom` module is deployed, and is done so using Sonatype's OSS support (see 
[user guide](http://central.sonatype.org/pages/apache-maven.html)).

#### Release to Sonatype's Snapshot Repo ####

To deploy a snapshot, use:

    pushd cpt
    mvn clean deploy
    popd

The artifacts should be available in Sonatype's 
[Snapshot Repo](https://oss.sonatype.org/content/repositories/snapshots).

#### Release to Maven Central (scripted process) ####

The `release.sh` script automates the release process.  It performs the following:

* perform sanity check (`mvn clean install -o`) that everything builds ok
* bump the `pom.xml` to a specified release version, and tag
* perform a double check (`mvn clean install -o`) that everything still builds ok
* release the code using `mvn clean deploy`
* bump the `pom.xml` to a specified release version

For example:

    sh release.sh 1.6.1 \
                  1.6.2-SNAPSHOT \
                  dan@haywood-associates.co.uk \
                  "this is not really my passphrase"
    
where
* `$1` is the release version
* `$2` is the snapshot version
* `$3` is the email of the secret key (`~/.gnupg/secring.gpg`) to use for signing
* `$4` is the corresponding passphrase for that secret key.

If the script completes successfully, then push changes:

    git push
    
If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.

#### Release to Maven Central (manual process) ####

If you don't want to use `release.sh`, then the steps can be performed manually.

To start, call `bumpver.sh` to bump up to the release version, eg:

     `sh bumpver.sh 1.6.1`

which:
* edit the parent `pom.xml`, to change `${isis-module-command.version}` to version
* edit the `dom` module's pom.xml version
* commit the changes
* if a SNAPSHOT, then tag

Next, do a quick sanity check:

    mvn clean install -o
    
All being well, then release from the `cpt` module:

    pushd cpt
    mvn clean deploy -P release \
        -Dpgp.secretkey=keyring:id=dan@haywood-associates.co.uk \
        -Dpgp.passphrase="literal:this is not really my passphrase"
    popd

where (for example):
* "dan@haywood-associates.co.uk" is the email of the secret key (`~/.gnupg/secring.gpg`) to use for signing
* the pass phrase is as specified as a literal

Other ways of specifying the key and passphrase are available, see the `pgp-maven-plugin`'s 
[documentation](http://kohsuke.org/pgp-maven-plugin/secretkey.html)).

If (in the `dom`'s `pom.xml`) the `nexus-staging-maven-plugin` has the `autoReleaseAfterClose` setting set to `true`,
then the above command will automatically stage, close and the release the repo.  Sync'ing to Maven Central should 
happen automatically.  According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update 
[search](http://search.maven.org).

If instead the `autoReleaseAfterClose` setting is set to `false`, then the repo will require manually closing and 
releasing either by logging onto the [Sonatype's OSS staging repo](https://oss.sonatype.org) or alternatively by 
releasing from the command line using `mvn nexus-staging:release`.

Finally, don't forget to update the release to next snapshot, eg:

    sh bumpver.sh 1.6.2-SNAPSHOT

and then push changes.

