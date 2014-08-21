# isis-module-command #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-command.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-command)

This module, intended for use with [Apache Isis](http://isis.apache.org), provides 

The module consists of ...

## Screenshots ##

The following screenshots show an example app's usage of the module.

#### Installing the Fixture Data




## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

To use "out-of-the-box":

* update your classpath by adding this dependency in your dom project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.command&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-command-dom&lt;/artifactId&gt;
        &lt;version&gt;1.6.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
                    ...,\
                    org.isisaddons.module.command.dom,\
                    ...

    isis.services = ...,\
                    org.isisaddons.module.command.CommandServiceContributions,\
                    org.isisaddons.module.command.BackgroundCommandServiceContributions,\
                    ...

The `CommandServiceContributions` and `BackgroundCommandServiceContributions` services are optional but recommended; see below for more information.

If instead you want to extend this module's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml   ` - parent pom
* `dom       ` - the module implementation, depends on Isis applib
* `fixture   ` - fixtures, holding a sample domain objects and fixture scripts; depends on `dom`
* `integtests` - integration tests for the module; depends on `fixture`
* `webapp    ` - demo webapp (see above screenshots); depends on `dom` and `fixture`

Only the `dom` project is released to     Check for versions available in the 
[Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-audit-dom)).  The versions of the other modules 
are purposely left at `0.0.1-SNAPSHOT` because they are not intended to be released.

## API ##


## Implementation ##

## Supporting Services ##

## Related Modules/Services ##

... referenced by the [Isis Add-ons](http://www.isisaddons.org) website.



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
