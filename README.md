# isis-module-servletapi #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-servletapi.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-servletapi)

This module, intended for use with [Apache Isis](http://isis.apache.org), provides access to various elements of the 
Servlet API, namely the `ServletContext`, the `HttpServletRequest` and the `HttpServletResponse`.

For each of these APIs a corresponding "provider" domain service exists; for example `ServletContextProvider` service
provides access to the `ServletContext`.

## Screenshots ##

The following screenshots show an example app's usage of the module with some sample data:

![](https://raw.github.com/isisaddons/isis-module-servletapi/master/images/010-install-fixtures.png)

The demo object has all of the various "provider" domain serviecs injected into it.  It uses the `ServletContextProvider`
 and the `HttpServletRequestProvider` services to simply show the servlet context name and user's locale:

![](https://raw.github.com/isisaddons/isis-module-servletapi/master/images/020-servlet-and-request.png)

To demonstrate the use of the `HttpServletResponseProvider`, the demo object provides an "addHeader" action:

![](https://raw.github.com/isisaddons/isis-module-servletapi/master/images/030-response.png)

When invoked, this adds a HTTP header to the response:

![](https://raw.github.com/isisaddons/isis-module-servletapi/master/images/040-response-header.png)

## How to run the Demo App ##

The prerequisite software is:

* Java JDK 8 (>= 1.9.0) or Java JDK 7 (<= 1.8.0)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-servletapi.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 7 (nb: Isis currently does not support JDK 8)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-audit.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

#### "Out-of-the-box" ####

To use "out-of-the-box":

* update your classpath by adding this dependency in your `dom` project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.servletapi&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-servletapi-dom&lt;/artifactId&gt;
        &lt;version&gt;1.8.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
                    ...,\
                    org.isisaddons.module.servletapi.dom,\
                    ...
</pre>

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-servletapi-dom)).


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

* `pom.xml   ` - parent pom
* `dom       ` - the module implementation, depends on Isis applib
* `fixture   ` - fixtures, holding a sample domain objects and fixture scripts; depends on `dom`
* `integtests` - integration tests for the module; depends on `fixture`
* `webapp    ` - demo webapp (see above screenshots); depends on `dom` and `fixture`

Only the `dom` project is released to Maven Central Repo.  The versions of the other modules are purposely left at 
`0.0.1-SNAPSHOT` because they are not intended to be released.

## API & Implementation ##

The `ServletContextProvider` defines the following API:

<pre>
public class ServletContextProvider {
    public ServletContext getServletContext() { ... }
}
</pre>

The `HttpServletRequestProvider` defines the following API:

<pre>
public class HttpServletRequestProvider {
    public HttpServletRequest getHttpServletRequest() { ... }
}
</pre>

And finally the `HttpServletResponseProvider` defines the following API:

<pre>
public class HttpServletResponseProvider {
    public HttpServletResponse getHttpServletResponse() { ... }
}
</pre>

These actions are all programmatic and do not appear in the UI.

## Change Log ##

* `1.8.0` - released against Isis 1.8.0


## Legal Stuff ##
 
#### License ####

    Copyright 2015 Dan Haywood

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

The module implementation currently depends on Wicket (so cannot be used within Restful Objects viewer).

##  Maven deploy notes ##

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
