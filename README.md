# isis-module-tags #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-tags.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-tags)

This module, intended for use within [Apache Isis](http://isis.apache.org), provides the ability to add multiple tags
(or labels) to any entity.

For example, an `Email` entity could be associated with a `priority` tag of 'urgent', 'normal' or 'low', and could also
have a `category` tag of 'work', 'family', 'friends'.

The idea is to allow a user to label an entity by offering a choice of other existing tags associated with other 
entities of the same type and also tag type.   That is, the combination of the entity type (eg `Email`) and the tag type 
(eg `priority`) creates a named set of available tags (ie 'urgent', 'normal', low').  Tags for other entities (eg `Customer`)
or other tag types (eg `category`) are kept separate.

As well as listing existing tags, new tags can of course also be created, and existing tags updated or removed.  


## API ##

The main functionality is exposed through the `Tags` domain service, which provides two methods:

    public class Tags {
    
        @Programmatic
        public List<String> choices(final Object taggedObject, final String tagKey) { ... }

        @Programmatic
        public Tag tagFor(
                final Object taggedObject,
                final Tag existingTag, 
                final String tagKey, final String tagValue) { ... }
    }

where:

* `choices(...)` returns a list of tags as strings for the object being tagged (eg `Email`) and for the tag key 
   (eg `priority`)

* `tagFor(...)` will create/update a tag for the object being tagged (eg `Email`), the tag key (eg `priority`)
   and the tag value (eg 'urgent').  The existing tag (if any) is passed in so that it can be removed if the tag value 
   is left as null. 
   
   
## Maven Configuration ##

In the `pom.xml` for your "dom" module, add:
    
    <dependency>
        <groupId>org.isisaddons.module.tags</groupId>
        <artifactId>isis-module-tags-dom</artifactId>
        <version>x.y.z</version>
    </dependency>

where `x.y.z` is the latest available in the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-tags-dom)).


## Registering the service ##

The `Tags` domain service is annotated with `@DomainService`, so add to the `packagePrefix` key:

    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=...,\
                                                                org.isisaddons.module.tags.dom,\
                                                                ...

    

## Legal Stuff ##
 
#### License ####

    Copyright 2014 Dan Haywood

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

There are no third-party dependencies.


##  Maven deploy notes

Only the `dom` module is deployed, and is done so using Sonatype's OSS support (see 
[user guide](http://central.sonatype.org/pages/apache-maven.html)).

#### Release to Sonatype's Snapshot Repo ####

To deploy a snapshot, use:

    pushd dom
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

    sh release.sh 1.6.1 1.6.2-SNAPSHOT dan@haywood-associates.co.uk "this is not really my passphrase"
    
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
    
All being well, then release from the `dom` module:

    pushd dom
    mvn clean deploy -P release \
        -Dpgp.secretkey=keyring:id=dan@haywood-associates.co.uk \
        -Dpgp.passphrase="literal:this is not really my passphrase"
    popd

where (for example):
* "dan@haywood-associates.co.uk" is the email of the secret key (`~/.gnupg/secring.gpg`) to use for signing
* the pass phrase is as specified as a literal

Other ways of specifying the key and passphrase are available, see the `pgp-maven-plugin`'s 
[documentation](http://kohsuke.org/pgp-maven-plugin/secretkey.html)).

If (in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the `autoReleaseAfterClose` setting set to `true`,
then the above command will automatically stage, close and the release the repo.  Sync'ing to Maven Central should 
happen automatically.  According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update 
[search](http://search.maven.org).

If instead the `autoReleaseAfterClose` setting is set to `false`, then the repo will require manually closing and 
releasing either by logging onto the [Sonatype's OSS staging repo](https://oss.sonatype.org) or alternatively by 
releasing from the command line using `mvn nexus-staging:release`.

Finally, don't forget to update the release to next snapshot, eg:

    sh bumpver.sh 1.6.2-SNAPSHOT

and then push changes.
