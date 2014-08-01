# isis-module-audit #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-audit.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-audit)

This module, intended for use within [Apache Isis](http://isis.apache.org), 
provides an implementation of Isis' `AuditingService3` API that persists
audit entries using Isis' own (JDO) objectstore.  Typically this will be to
a relational database; the module's `AuditEntryJdo` entity is mapped to the
"IsisAuditEntry" table.

## Screenshots ##

The following screenshots show an example app's usage of the module.

#### Installing the Fixture Data

![](https://raw.github.com/isisaddons/isis-module-audit/master/images/01-install-fixtures.png)

#### Object with initial (creation) audit entries

![](https://raw.github.com/isisaddons/isis-module-audit/master/images/02-first-object-with-initial-audit-entries.png)

#### Changing two properties on an object...

![](https://raw.github.com/isisaddons/isis-module-audit/master/images/03-changing-two-properties-on-object.png)

#### ... two audit entries created

![](https://raw.github.com/isisaddons/isis-module-audit/master/images/04-two-audit-entries-created.png)

#### Navigate to audit entry, view other audit entries, and navigate ...

![](https://raw.github.com/isisaddons/isis-module-audit/master/images/05-navigate-to-audit-entry-see-other-audit-entries.png)

#### ... back to audited object

![](https://raw.github.com/isisaddons/isis-module-audit/master/images/06-navigate-back-to-audited-object.png)


## How to Configure/Use ##

You can either use this module "out-of-the-box", or you can fork this repo
and extend to your own requirements. 

To use "out-of-the-box:

* update your classpath by adding this dependency in your dom project's `pom.xml`:

    <dependency>
        <groupId>org.isisaddons.module.audit</groupId>
        <artifactId>isis-module-audit-dom</artifactId>
        <version>x.y.z</version>
    </dependency>

    * where `x.y.z` is the latest available in the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-audit-dom)).

* update your `WEB-INF/isis.properties`:

    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=...,\
                                                                org.isisaddons.module.audit.dom,\
                                                                ...

    isis.services = ...,\
                    org.apache.isis.objectstore.jdo.applib.service.audit.AuditingServiceJdoContributions,\
                    ...

The `AuditingServiceJdoContributions` service is optional but recommended;
see below for more information.

If instead you want to extend this module's functionality, then we recommend 
that you fork this repo.  The repo is structured as follows:

* `pom.xml`    // parent pom
* `dom`        // the module implementation, depends on Isis applib
* `fixture`    // fixtures, holding a sample domain objects and fixture scripts; depends on `dom`
* `integtests` // integration tests for the module; depends on `fixture`
* `webapp`     // demo webapp (see above screenshots); depends on `dom` and `fixture`

Only the `dom` project is released to Maven central repo.  The versions of the
other modules are purposely left at `0.0.1-SNAPSHOT` because they are not intended
to be released.

## API ##

The `AuditingService3` defines the following API:

    @Programmatic
    public void audit(
            final UUID transactionId, 
            final String targetClass, 
            final Bookmark target, 
            final String memberIdentifier, 
            final String propertyId,
            final String preValue, 
            final String postValue, 
            final String user, 
            final java.sql.Timestamp timestamp);

Isis will automatically call this method on the service implementation if 
configured.  The method is called often, once for every individual
property of a domain object that is changed.

## Implementation ##

* This implementation was originally developed within Isis itself, as part of
the JDO Objectstore.  This implementation is identical (is a copy of) the
`org.apache.isis.module:isis-module-audit-jdo:1.6.0` Maven artifact; only the
package names have been changed.*

The `AuditingService3` API is implemented in this module by the 
`org.apache.isis.objectstore.jdo.applib.service.audit.AuditingServiceJdo` 
class.  This implementation simply persists an audit entry (`AuditEntryJdo`) 
each time it is called.   This results in a fine-grained audit trail.

The persisted `AuditEntryJdo` directly maps to the `AuditingService3` API:

    public class AuditEntryJdo 
        ... 
        private UUID transactionId;
        private String targetClass;
        private String targetStr;
        private String memberIdentifier;
        private String propertyId;
        private String preValue;
        private String postValue;
        private String user;
        private Timestamp timestamp;
        ... 
    }

where:

* `transactionId` is a unique identifier (a GUID) of the transaction 
  in which this audit entry was persisted.
* `timestamp` is the timestamp for the transaction
* `targetClass` holds the class of the audited object, eg 
  `com.mycompany.myapp.Customer`
* `targetStr` stores a serialized form of the `Bookmark`, in other words a
  provides a mechanism to look up the audited object, eg `CUS:L_1234` to 
  identify customer with id 1234.  ("CUS" corresponds to the `@ObjectType` 
  annotation/facet).
* `memberIdentifier` is the fully-qualified class and property Id (similar to
  the way that Javadoc words, eg `com.mycompany.myapp.Customer#firstName`)
* `propertyId` is the property identifier, eg `firstName`
* `preValue` holds a string representation of the property's value prior to it 
  being changed.  f the object has been created then it holds the value 
  "[NEW]".  If the string is too long, it will be truncated with ellipses 
  '...'.
* `preValue` holds a string representation of the property's value after it
  was changed.  f the object has been deleted  then it holds the value 
  "[DELETED]".  If the string is too long, it will be truncated with ellipses
  '...'.

The combination of `transactionId`, `targetStr` and `propertyId` make up
an alternative key to uniquely identify an audit entry.  However, there is 
(deliberately) no uniqueness constraint to enforce this rule.

The `AuditEntryJdo` entity is designed such that it can be rendered on an
Isis user interface if required.
    
## Relationship to other services ##

As well as defining the `AuditingService3` API, Isis' applib also defines
several other closely related services.

The `CommandContext` defines the `Command` class which provides request-scoped
information about an action invocation.  Commands can be thought of as being
the cause of an action; they are created "before the fact".  Some of the 
parameters passed to `AuditingService3` - such as `target`, `user`, and 
`timestamp` - correspond exactly to the `Command` class.

The `CommandService` service is an optional service that acts as a `Command`
factory and allows `Command`s to be persisted.  `CommandService`'s
API introduces the concept of a `transactionId`; once again this is the same
value as is passed to the `AuditingService3`.

The `PublishingService` is another optional service that allows an 
event to be published when either an object has changed or an actions has
been invoked.   There are some similarities between publishing to auditing;
they both occur "after the fact".  However the publishing service's 
primary use case is to enable inter-system co-ordination (in DDD terminology,
between bounded contexts).  As such, publishing is much coarser-grained than 
auditing, and not every change need be published.  Publishing also uses the 
`transactionId`.

The `CommandService` and `PublishingService` are optional; as with the 
`AuditingService3`, Isis will automatically use call each if the service
implementation if discovered on the classpath. 

If all these services are configured - such that  commands, audit entries and
published events are all persisted, then the `transactionId` that is common
to all enables seamless navigation between each.  (This is implemented 
through contributed actions/properties/collections; `AuditEntryJdo` 
implements the `HasTransactionId` interface in Isis' applib, and it is this
interface that each module has services that contribute to).

## Complementary Services ##

As well as the `AuditingServiceJdo` service (that implements the
`AuditingService3` API), the module also provides two further domain services:

* `AuditingServiceJdoRepository` provides the ability to search for persisted
  (`AuditEntryJdo`) audit entries.  None of its actions are visible in the
  user interface (they are all `@Programmatic`) and so this service is
  automatically registered.

* `AuditingServiceJdoContributions` provides the `auditEntries` contributed 
  collection to the `HasTransactionId` interface.  This will therefore 
  display all audit entries that occurred in a given transaction, in other
  words whenever a command, a published event or another audit entry is
  displayed.
  
## Known issues ##

In `1.6.0` a call to `DomainObjectContainer#flush()` is required in order that
any newly created objects are populated.  Note that a flush is performed
prior to any repository call, so there may not be any need to call.         

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
