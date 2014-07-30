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
   
   
## Maven Configuration

In the `pom.xml` for your "dom" module, add:
    
    <dependency>
        <groupId>org.isisaddons.module.tags</groupId>
        <artifactId>isis-module-tags-dom</artifactId>
        <version>x.y.z</version>
    </dependency>

where `x.y.z` is the latest available in the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-tags-dom)).


## Registering the service

The `Tags` domain service is annotated with `@DomainService`, so add to the `packagePrefix` key:

    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=...,\
                                                                org.isisaddons.module.tags.dom,\
                                                                ...

    

## Legal Stuff ##
 
### License ###

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


### Dependencies

There are no third-party dependencies.
