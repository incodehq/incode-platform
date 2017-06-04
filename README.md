# isis-module-poly #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-poly.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-poly)

This module, intended for use within [Apache Isis](http://isis.apache.org), provides a set of helpers to support the
definition of polymorphic associations; that is: relationships from one persistent entity to another by means of a
(Java) interface.

#### Rationale

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
while the subtype table holds a foreign key is held within the subtype.

It is quite possible to implement the "table of two halves" pattern without using the helpers provided by this module;
indeed arguably there's more value in the demo application that accompanies this module (discussed below) than in the
helpers themselves.  Still, the helpers do provide useful structure to help implement the pattern.

## Demo Application ##

This module has a comprehensive demo application that demonstrates four different polymorphic associations:

- 1-to-1 and n-to-1: a `CommunicationChannel` may be owned by a `CommunicationChannelOwner`.
- 1-to-n: a `Case` may contain multiple `CaseContent`s
- 1-to-1: a `Case` may have a primary `CaseContent`.

The `CommunicationChannel` and `Case` are regular entities, while `CommunicationChannelOwner` and `CaseContent` are
(Java) interfaces.

The demo app also has two entities, `FixedAsset` and `Party`, that both implement each of these
interfaces.  Each `FixedAsset` may "own" a single `CommunicationChannel`, while a `Party` may "own" multiple
`CommunicationChannel`s.  Meanwhile both `FixedAsset` and `Party` can be added as the "contents" of multiple `Case`s, and
either can be used as a `Case`'s "primary content".

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

Note how `Case` actually has _two_ polymorphic associations: a 1:n to its "contents", and a 1:1 to its "primary content".

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

The screenshots below show the demo app's usage of the _poly_ module.  We start by installing some fixture data:

![](https://raw.github.com/isisaddons/isis-module-poly/master/images/010-install-fixtures.png)

This sets up 3 parties, 3 fixed assets which between them have 9 communication channels.  There are also 3 cases and
   the parties and fixed assets are variously contained within.  This is summarized on the home page:

![](https://raw.github.com/isisaddons/isis-module-poly/master/images/020-dashboard.png)

If we navigate to the `Party` entity, we can see that it shows a collection of `CommunicationChannel`s that the party
owns, and also a collection of the `Case`s within which the party is contained:

![](https://raw.github.com/isisaddons/isis-module-poly/master/images/030-party.png)

The `FixedAsset` entity is similar in that it also has a collection of `Case`s.  However, in our demo app we have a
business rule that the fixed asset can own only a single `CommunicationChannel`.

![](https://raw.github.com/isisaddons/isis-module-poly/master/images/040-fixed-asset.png)

On the `Party` entity we can add (and remove) `CommunicationChannel`s:

![](https://raw.github.com/isisaddons/isis-module-poly/master/images/050-party-add-comm-channel.png)

In this demo app, because communication channels are _not_ shared by entities, this will actually create and persist
the corresponding `CommunicationChannel`.

We can also add (or remove) from `Case`s:

![](https://raw.github.com/isisaddons/isis-module-poly/master/images/060-party-add-to-case.png)

Here the rule is slightly different: the `Case` already exists and so the party is merely associated with an existing case.

From the `Case` entity's perspective, we can see its contents and also its primary content:

![](https://raw.github.com/isisaddons/isis-module-poly/master/images/070-case.png)

As might be expected, we have an action to set (or clear) the primary content:

![](https://raw.github.com/isisaddons/isis-module-poly/master/images/080-case-set-primary-contents.png)


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 8 (>= 1.9.0) or Java JDK 7 (<= 1.8.0)
** note that the compile source and target remains at JDK 7
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-poly.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`



## Design

The key design idea is to leverage Isis' [event bus service](http://isis.apache.org/reference/services/event-bus-service.html) to determine which concrete subtype should be created and persisted to hold the association.

* when the association needs to be created, an event is posted to the event bus
* the subscriber updates the event with the details of the subtype to be persisted.
* if no subscriber updates the event, then the association cannot be created and an exception is thrown.

The helper classes provided by this module factor out some of the boilerplate relating to this design, however there
is (necessarily) quite a lot of domain-specific code.  What's important is understanding the design and how to replicate
it.

The recipe for the pattern is:

<table>
<tr>
<th>
#
</th>
<th>
Step
</th>
<th>
Example
</th>
</tr>

<tr>
<td>
    1
</td>
<td>
    Create an interface for the target of the association
</td>
<td>
    <ul>
    <li><code>CommunicationChannelOwner</code>
    </li>
    <li><code>CaseContent</code>
    </li>
    </ul>
</td>
</tr>

<tr>
<td>
    2
</td>
<td>
    Create a persistent entity corresponding to the association
</td>
<td>
    <ul>
    <li><code>CommunicationChannelOwnerLink</code> for the <code>CommunicationChannel</code>/"owner" association
    </li>
    <li><code>CaseContentLink</code> for <code>Case</code>/"contents" association
    </li>
    <li><code>CasePrimaryContentLink</code> for <code>Case</code>/"primary content" association
    </li>
    </ul>
</td>
</tr>

<tr>
<td>
    3
</td>
<td>
    Create an "instantiate event".  <p/>
    We suggest using a nested static class of the link entity:
</td>
<td>
    <ul>
    <li><code>CommunicationChannelOwnerLink.InstantiateEvent</code>
    </li>
    <li><code>CaseContentLink.InstantiateEvent</code>
    </li>
    <li><code>CasePrimaryContentLink.InstantiateEvent</code>
    </li>
    </ul>
</td>
</tr>

<tr>
<td>
    4
</td>
<td>
    Create a corresponding repository service for that link persistent entity:
</td>
<td>
    <ul>
    <li><code>CommunicationChannelOwnerLinks</code>
    </li>
    <li><code>CaseContentLinks</code>
    </li>
    <li><code>CasePrimaryContentLinks</code>
    </li>
    </ul>
</td>
</tr>

<tr>
<td>
    5
</td>
<td>
    Create a subtype for each implementation of the target interface:
</td>
<td>
    <ul>
    <li><code>CommunicationChannelOwnerLinkForFixedAsset</code> and <code>CommunicationChannelOwnerLinkForParty</code>
    </li>
    <li><code>CaseContentLinkForFixedAsset</code> and <code>CaseContentLinkForParty</code>
    </li>
    <li><code>CasePrimaryContentLinkForFixedAsset</code> and <code>CasePrimaryContentLinkForParty</code>
    </li>
    </ul>
</td>
</tr>

<tr>
<td>
    6
</td>
<td>
    Create a subscriber to the event for each implementation of the target interface.<p/>
    We suggest using a nested static class of the subtype:
</td>
<td>
    <ul>
    <li><code>CommunicationChannelOwnerLinkForFixedAsset.</code> <code>InstantiateSubscriber</code> and <code>CommunicationChannelOwnerLinkForParty.</code> <code>InstantiateSubscriber</code>
    </li>
    <li><code>CaseContentLinkForFixedAsset.</code> <code>InstantiateSubscriber</code> and <code>CaseContentLinkForParty.</code><code>InstantiateSubscriber</code>
    </li>
    <li><code>CasePrimaryContentLinkForFixedAsset.</code> <code>InstantiateSubscriber</code> and <code>CasePrimaryContentLinkForParty.</code> <code>InstantiateSubscriber</code>
    </li>
    </ul>
</td>
</tr>
</table>


## API and Usage

The module itself consist of the following classes:

* `PolymorphicAssociationLink` - an abstract class from which to derive the `*Link` entity
* `PolymorphicAssociationLink.InstantiateEvent` - a superclass for the "instantiate event"
* `PolymorphicAssociationLink.Factory` - a utility class that broadcasts the event and persists the link using the requested subtype

Let's look at each in more detail, relating back to the "communication channel owner" association in the demo app.

### PolymorphicAssociationLink

A link is in essence a tuple between two entities.  One of these links is direct "subject"; the other is the polymorphic reference.  The `PolymorphicAssociationLink` class is intended to be used base class for all `*Link` entities (step 2 in the pattern recipe), and defines this structure:

    public abstract class PolymorphicAssociationLink<
                                S, P, L extends PolymorphicAssociationLink<S, P, L>>
            implements Comparable<L> {

        protected PolymorphicAssociationLink(final String titlePattern) { ... }

        public abstract S getSubject();
        public abstract void setSubject(S subject);

        public abstract String getPolymorphicObjectType();
        public abstract void setPolymorphicObjectType(final String polymorphicObjectType);

        public abstract String getPolymorphicIdentifier();
        public abstract void setPolymorphicIdentifier(final String polymorphicIdentifier);

        public P getPolymorphicReference() { ... }
        public void setPolymorphicReference(final P polymorphicReference) { ... }

        public int compareTo(final PolymorphicAssociationLink other) { ... }
    }

The subclass is required to implement the `subject`, `polymorphicObjectType` and the `polymorphicIdentifier` properties;
these should delegate to the "concrete" properties.

For example, the `CommunicationChannelOwnerLink` looks like:

    public abstract class CommunicationChannelOwnerLink
            extends PolymorphicAssociationLink<
                        CommunicationChannel, CommunicationChannelOwner,
                        CommunicationChannelOwnerLink> {

        public CommunicationChannelOwnerLink() {
            super("{polymorphicReference} owns {subject}");
        }

        public CommunicationChannel getSubject() {
            return getCommunicationChannel();
        }
        public void setSubject(final CommunicationChannel subject) {
            setCommunicationChannel(subject);
        }

        public String getPolymorphicObjectType() {
            return getOwnerObjectType();
        }
        public void setPolymorphicObjectType(final String polymorphicObjectType) {
            setOwnerObjectType(polymorphicObjectType);
        }

        public String getPolymorphicIdentifier() {
            return getOwnerIdentifier();
        }
        public void setPolymorphicIdentifier(final String polymorphicIdentifier) {
            setOwnerIdentifier(polymorphicIdentifier);
        }

        // JDO persisted property
        private CommunicationChannel communicationChannel;

        // JDO persisted property
        private String ownerObjectType;

        // JDO persisted property
        private String ownerIdentifier;
    }

Thus, the abstract properties defined by `PolymorphicAssociationLink` just delegate to corresponding persisted (JDO annotated)
properties in `CommunicationChannelOwnerLink`.

Also note the pattern passed to the constructor; this is used to generate a title.


### PolymorphicAssociationLink.InstantiateEvent

The `PolymorphicAssociationLink.InstantiateEvent` is the base class to derive an instantiate event type for each
polymorphic association (step 3 in the pattern recipe).  Having derived event classes means that the event subscribers need only receive the exact
events that they care about.

The `InstantiateEvent` has the following structure:

    public abstract static class InstantiateEvent<
                                    S, P, L extends PolymorphicAssociationLink<S, P, L>>
            extends java.util.EventObject {

        protected InstantiateEvent(
                final Class<L> linkType,
                final Object source,
                final S subject,
                final P polymorphicReference) { ... }

        public S getSubject() { ... }
        public P getPolymorphicReference() { ... }

        public Class<? extends L> getSubtype() { ... }
        public void setSubtype(final Class<? extends L> subtype) { ... }
    }

Any subclass is required to take the last three parameters in its constructor; the event is instantiated reflectively by `PolymorphicAssociationLink.Factory`.

For example, the `CommunicationChannelOwnerLink.InstantiateEvent` is simply:

        public static class InstantiateEvent
                extends PolymorphicAssociationLink.InstantiateEvent<
                            CommunicationChannel, CommunicationChannelOwner,
                            CommunicationChannelOwnerLink> {

            public InstantiateEvent(
                    final Object source,
                    final CommunicationChannel subject,
                    final CommunicationChannelOwner owner) {
                super(CommunicationChannelOwnerLink.class, source, subject, owner);
            }
        }


### PolymorphicAssociationLink.Factory

The final class `PolymorphicAssociationLink.Factory` is responsible for broadcasting the event and then persisting the
appropriate subtype for the link.  It has the following structure:

    public static class Factory<S,P,L extends PolymorphicAssociationLink<S,P,L>,
                                E extends InstantiateEvent<S,P,L>> {

        public Factory(
                final Object eventSource,
                final Class<S> subjectType,
                final Class<P> polymorphicReferenceType,
                final Class<L> linkType, final Class<E> eventType) { ... }

        public void createLink(final S subject, final P polymorphicReference) { ... }

    }

Unlike the other two classes, the factory is not subclassed.  Instead, it should be instantiated as appropriate.  Typically
this will be in a repository service for the `*Link` entity (step 4 in the pattern recipe).

For example, with the communication channel example the `Factory` is instantiated in the `CommunicationChannelOwnerLinks`
repository service:

    public class CommunicationChannelOwnerLinks {

        PolymorphicAssociationLink.Factory<
                CommunicationChannel,
                CommunicationChannelOwner,
                CommunicationChannelOwnerLink,
                CommunicationChannelOwnerLink.InstantiateEvent> linkFactory;

        @PostConstruct
        public void init() {
            linkFactory = container.injectServicesInto(
                    new PolymorphicAssociationLink.Factory<>(
                            this,
                            CommunicationChannel.class,
                            CommunicationChannelOwner.class,
                            CommunicationChannelOwnerLink.class,
                            CommunicationChannelOwnerLink.InstantiateEvent.class
                    ));

        }

        public void createLink(
                final CommunicationChannel communicationChannel,
                final CommunicationChannelOwner owner) {
            linkFactory.createLink(communicationChannel, owner);
        }
    }

Note that it is necessary to inject services into the factory (`container.injectServicesInto(...)`).


### Completing the Pattern

The helper classes provided by this module are actually only used by the "subject" domain entity (or the containing package for said entity); steps 1 through 4 in the pattern recipe.  But what about the implementation for an entity (such as `FixedAsset`) that wishes to be used in such a polymorphic association, ie the final steps 5 and 6?

Step 5 of the pattern requires a subtype of the `*Link` entity specific to the subtype to be reference.  For example,
for `FixedAsset` this looks like:

    public class CommunicationChannelOwnerLinkForFixedAsset
            extends CommunicationChannelOwnerLink {

        @Override
        public void setPolymorphicReference(final CommunicationChannelOwner polyReference) {
            super.setPolymorphicReference(polyReference);
            setFixedAsset((FixedAsset) polyReference);
        }

        // JDO persisted property
        private FixedAsset fixedAsset;

    }

where the inherited `setPolymorphicReference(...)` method is overridden to also populate the JDO persisted property
(`fixedAsset` in this case).

And, finally, step 6 defines a subscriber on the instantiate event.  We recommend this is a nested static class of the
`*Link` subtype, and so:

    public class CommunicationChannelOwnerLinkForFixedAsset
                            extends CommunicationChannelOwnerLink {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class InstantiationSubscriber extends AbstractSubscriber {

            @Programmatic
            @Subscribe
            public void on(final CommunicationChannelOwnerLink.InstantiateEvent ev) {
                if(ev.getPolymorphicReference() instanceof FixedAsset) {
                    ev.setSubtype(CommunicationChannelOwnerLinkForFixedAsset.class);
                }
            }
        }
    }

The thing to note is that although there are quite a few steps (1 through 4, in fact) to make an association polymorphic,
the steps to then reuse that polymorphic association (steps 5 and 6) are really rather trivial.


## Some quick asides

The demo application has a couple of other interesting implementation details - not to do with polymorphic associations -
but noteworthy nonetheless.

#### Use of event bus for cascade delete

With the `Case` class there is a "case contents" and a "primary case content"; the idea being that the primary content
should be one in the "contents" collection.

If the case content object that happens to be primary is dissociated from the case, then a
`CaseContentContributions.RemoveFromCaseDomainEvent` domain event is broadcast.  A subscriber listens on this to
delete the primary case link:

    public class CasePrimaryContentSubscriber extends AbstractSubscriber {

        @Subscribe
        public void on(final CaseContentContributions.RemoveFromCaseDomainEvent ev) {
            switch (ev.getEventPhase()) {
                case EXECUTING:
                    final CasePrimaryContentLink link =
                                casePrimaryContentLinks.findByCaseAndContent(
                                                    ev.getCase(), ev.getContent());
                    if(link != null) {
                        container.remove(link);
                    }
                    break;
            }
        }
    }


#### Contributed properties for collections of an interface type

It (currently) isn't possible to define (fully abstract) properties on interfaces, meaning that by default a collection
of objects implementing an interface (eg `Case`'s "caseContents" collection) would normally only show the icon of
the object; not particularly satisfactory.

However, Isis *does* support the notion of contributed properties to interfaces.  The demo application uses this trick
for the "caseContents" in the `CaseContentContributions` domain service:

    public class CaseContentContributions {

        @Action( semantics = SemanticsOf.SAFE )
        @ActionLayout( contributed = Contributed.AS_ASSOCIATION )
        @PropertyLayout( hidden = Where.OBJECT_FORMS )
        public String title(final CaseContent caseContent) {
            return container.titleOf(caseContent);
        }
    }

Moreover, this trick contributes to all implementations (`FixedAsset` and `Party`).

There is however a small gotcha, in that we only want this contributed property to be viewed on tables.  The
`@Property(hidden=Where.OBJECT_FORMS)` ensures that it is not shown anywhere else.


## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

#### "Out-of-the-box" ####

To use "out-of-the-box":

* update your classpath by adding this dependency in your dom project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.poly&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-poly-dom&lt;/artifactId&gt;
        &lt;version&gt;1.13.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* if using `AppManifest`, then update its `getModules()` method:

    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                ...
                org.isisaddons.module.poly.PolyModule.class,
                ...
        );
    }

* otherwise, update your `WEB-INF/isis.properties`:

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
    &lt;version&gt;1.14.0-SNAPSHOT&lt;/version&gt;
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
    


## Change Log ##

* `1.13.0` - released against Isis 1.13.0; fixes link:https://github.com/isisaddons/isis-module-poly/issues/1[#1 - extend Factory to be able to check before-hand whether a link type is supported]
* `1.12.0` - released against Isis 1.12.0
* `1.11.0` - released against Isis 1.11.0
* `1.10.0` - released against Isis 1.10.0
* `1.9.0` - released against Isis 1.9.0


## Legal Stuff ##
 
#### License ####

    Copyright 2014~2016 Dan Haywood

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



#### Release an Interim Build ####

If you have commit access to this project (or a fork of your own) then you can create interim releases using the `interim-release.sh` script.

The idea is that this will - in a new branch - update the `dom/pom.xml` with a timestamped version (eg `1.13.0.20161017-0738`).
It then pushes the branch (and a tag) to the specified remote.

A CI server such as Jenkins can monitor the branches matching the wildcard `origin/interim/*` and create a build.
These artifacts can then be published to a snapshot repository.

For example:

    sh interim-release.sh 1.14.0 origin

where

* `1.14.0` is the base release
* `origin` is the name of the remote to which you have permissions to write to.



#### Release to Maven Central ####

The `release.sh` script automates the release process.  It performs the following:

* performs a sanity check (`mvn clean install -o`) that everything builds ok
* bumps the `pom.xml` to a specified release version, and tag
* performs a double check (`mvn clean install -o`) that everything still builds ok
* releases the code using `mvn clean deploy`
* bumps the `pom.xml` to a specified release version

For example:

    sh release.sh 1.13.0 \
                  1.14.0-SNAPSHOT \
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
    git push origin 1.13.0

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).
