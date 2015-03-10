# isis-module-poly #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-poly.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-poly)

This module, intended for use within [Apache Isis](http://isis.apache.org), provides a set of helpers to support the
definition of polymorphic associations; that is: relationships from one persistent entity to another by means of a
(Java) interface.

Persistable polymorphic associations are important because they allow decoupling between classes using the
[dependency inversion principle](http://en.wikipedia.org/wiki/Dependency_inversion_principle); module dependencies can
therefore by kept acyclic.  This is key to long-term maintainability of the codebase (avoiding the [big ball of mud](http://en.wikipedia.org/wiki/Big_ball_of_mud) anti-pattern).

While JDO/DataNucleus has several [built-in strategies](http://www.datanucleus.org/products/datanucleus/jdo/orm/interfaces.html)
to support polymorphic associations, none allow both a persisted reference to be arbitrarily extensible as well as
supporting foreign keys (for RDBMS-enforced referential integrity).  The purpose of this module is therefore to provide
helper classes that use a different approach, namely the "table of two halves" pattern.

#### Table of Two Halves Pattern

The "table of two halves" pattern models the relationship tuple itself as a class hierarchy.  The supertype table holds
a generic polymorphic reference to the target object (leveraging Isis' [Bookmark Service](http://isis.apache.org/reference/services/bookmark-service.html))
while the subtyype table holds a foreign key is held within the subtype.

It is quite possible to implement the "table of two halves" pattern without using the helpers provided by this module;
indeed arguably there's more value in the demo application that accompanies this module (discussed below) than in the
helpers themselves.  Still, the helpers do provide useful structure to help implement the pattern.

## Demo Application ##

This module has a comprehensive demo application that demonstrates four different polymorphic associations:

- 1-to-1 and n-to-1: a `CommunicationChannel` may be owned by a `CommunicationChannelOwner`.
- 1-to-n: a `Case` may contain multiple `CaseContent`s
- 1-to-1: a `Case` may have a primary `CaseContent`.

The `CommunicationChannel` and `Case` are regular entities, while `CommunicationChannelOwner` and `CaseContent` are
(Java) interfaces.  The demo app has two entities, `FixedAsset` and `Party`, that both implement each of these
interfaces.  Each `FixedAsset` may own a single `CommunicationChannel`, while a `Party` may own multiple
`CommunicationChannel`s.  Meanwhile both `FixedAsset` and `Party` can be added as the contents of multiple `Case`s.

#### Communication Channel

The following UML diagram shows the "logical" polymorphic association between `CommunicationChannel` and its owning
`CommunicationChannelOwner`:

<img src="https://raw.github.com/isisaddons/isis-module-poly/master/images/comm-channel-uml.png" width="600">

This is realized using the following entities:

<img src="https://raw.github.com/isisaddons/isis-module-poly/master/images/comm-channel-link-uml.png" width="600">

Here the `CommunicationChannelOwnerLink` is a persistent entity that has subtypes for each of the implementations of
 the `CommunicationChannelOwner` interface, namely `CommunicationChannelOwnerLinkForFixedAsset` and
 `CommunicationChannelOwnerLinkForParty`.   This inheritance hierarchy can be persisted using any of the
 [standard strategies](http://www.datanucleus.org/products/datanucleus/jdo/orm/inheritance.html) supported by
 JDO/DataNucleus.

In the demo application the [NEW_TABLE](http://www.datanucleus.org/products/datanucleus/jdo/orm/inheritance.html#newtable)
 strategy is used, giving rise to these tables:

<img src="https://raw.github.com/isisaddons/isis-module-poly/master/images/comm-channel-rdbms.png" width="600">

#### Case Contents

The following UML diagram shows the (two) "logical" polymorphic assocations between `Case` and its `CaseContent`s:

<img src="https://raw.github.com/isisaddons/isis-module-poly/master/images/case-content-uml.png" width="600">

Note how `Case` actually has _two_ polymorphic associations: a 1:n to its contents, and a 1:1 to its primary content.

This is realized using the following entities:

<img src="https://raw.github.com/isisaddons/isis-module-poly/master/images/case-content-link-uml.png" width="600">

Here the `CaseContentLink` is a persistent entity that (as for communication channels) has subtypes for each of the
implementations of the `CaseContent` interface.  But because `Case` actually has two associations to `CaseContent`, there
is also a further `CasePrimaryContentLink` persistent entity, again with subtypes.

In the demo application the [NEW_TABLE](http://www.datanucleus.org/products/datanucleus/jdo/orm/inheritance.html#newtable)
  strategy is used for both, giving rise to these tables for the "case content" association:

<img src="https://raw.github.com/isisaddons/isis-module-poly/master/images/case-content-contents-rdbms.png" width="600">

and also to these for the "primary content" association:

<img src="https://raw.github.com/isisaddons/isis-module-poly/master/images/case-content-primary-rdbms.png" width="600">


## Screenshots ##

The screenshots below show the demo app's usage of the _poly_ module:

#### Install example fixtures ####

![](https://raw.github.com/isisaddons/isis-module-poly/master/images/010-xxx.png)



## How to run the Demo App ##

The prerequisite software is:

* Java JDK 7 (nb: Isis currently does not support JDK 8)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-poly.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`



## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

#### "Out-of-the-box" ####

To use "out-of-the-box":

* update your classpath by adding this dependency in your dom project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.poly&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-poly-dom&lt;/artifactId&gt;
        &lt;version&gt;1.9.0-SNAPSHOT&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
                    ...,\
                    org.isisaddons.module.poly.dom,\
                    ...
</pre>

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-poly-dom).


#### "Out-of-the-box" (-SNAPSHOT) ####

If you want to use the current `-SNAPSHOT`, then the steps are the same as above, except:

* when updating the classpath, specify the appropriate -SNAPSHOT version:

<pre>
    &lt;version&gt;1.9.0-SNAPSHOT&lt;/version&gt;
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

If instead you want to extend this module's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml`    // parent pom
* `dom`        // the module implementation, depends on Isis applib
* `fixture`    // fixtures, holding a sample domain objects and fixture scripts; depends on `dom`
* `integtests` // integration tests for the module; depends on `fixture`
* `webapp`     // demo webapp (see above screenshots); depends on `dom` and `fixture`

Only the `dom` project is released to Maven Central Repo.  The versions of the other modules are purposely left at 
`0.0.1-SNAPSHOT` because they are not intended to be released.
    


## API and Usage ##

The module consists of XXX.



## Change Log ##

* `1.9.0-SNAPSHOT` - to be released against Isis 1.9.0


## Legal Stuff ##
 
#### License ####

    Copyright 2014~2015 Dan Haywood

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

This module has no external dependencies.


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

#### Release to Maven Central ####

The `release.sh` script automates the release process.  It performs the following:

* performs a sanity check (`mvn clean install -o`) that everything builds ok
* bumps the `pom.xml` to a specified release version, and tag
* performs a double check (`mvn clean install -o`) that everything still builds ok
* releases the code using `mvn clean deploy`
* bumps the `pom.xml` to a specified release version

For example:

    sh release.sh 1.9.0 \
                  1.10.0-SNAPSHOT \
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
    git push origin 1.9.0

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).
