# isis-module-stringinterpolator #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-stringinterpolator.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-stringinterpolator)

This module, intended for use within [Apache Isis](http://isis.apache.org), provides a mechanism to interpolate string 
templates with either Isis system properties or values obtained from a domain object.

One use case for this service is in building URLs based on an object's state, parameterized
by environment (prod/test/dev etc).  These URLs could be anything; for example, to a reporting service:

    ${property['reportServerBase']}/ReportViewer.aspx?/InvoicesDue&propertyId=${this.property.id}

where:

* `${property['reportServerBase']}` is an Isis system property
* `${this.property.id}` is an expression that is evaluated in the context of a domain object (`this`), returning
   `this.getProperty().getId()`

Isis system properties are exposed as the `properties` map, while the target object is exposed as the `this` object.


## Screenshots ##

The screenshots below show an example app's usage of the _stringinterpolator_ module:

#### Install example fixtures ####

![](https://raw.github.com/isisaddons/isis-module-stringinterpolator/master/images/010-install-fixtures.png)

... returning an example entity:

![](https://raw.github.com/isisaddons/isis-module-stringinterpolator/master/images/020-example-entity.png)

The `Open` (contributed) action is:

    public URL open(ToDoItem toDoItem) throws MalformedURLException {
        String urlStr = stringInterpolatorService.interpolate(
            toDoItem, "${properties['isis.website']}/${this.documentationPage}");
        return new URL(urlStr);
    }

where `WEB-INF/isis.properties` contains:

    isis.website=http://isis.apache.org

and where (as the screenshot shows) `ToDoItem` entity has the structure:

    public class ToDoItem ... {
    
        private String description;
        private String documentationPage;

        // getters and setters omitted 
    }


Invoking the `Open` action computes the `urlStr` local variable, and then (because the action returns a `URL`), results
in the browser opening the appropriate web page:

![](https://raw.github.com/isisaddons/isis-module-stringinterpolator/master/images/030-opened-page.png)


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 8 (>= 1.9.0) or Java JDK 7 (<= 1.8.0)
** note that the compile source and target remains at JDK 7
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-stringinterpolator.git
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
        &lt;groupId&gt;org.isisaddons.module.stringinterpolator&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-stringinterpolator-dom&lt;/artifactId&gt;
        &lt;version&gt;1.13.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
                    ...,\
                    org.isisaddons.module.stringinterpolator.dom,\
                    ...
</pre>

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-stringinterpolator-dom).


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
    


## API and Usage ##

The module consists of a single domain service, `StringInterpolatorService`.  

The interpolation replaces each occurrence of `${...}` with its interpolated value.  The expression in within the
braces is interpreted using [OGNL](http://commons.apache.org/proper/commons-ognl/).
 
#### Object graph interpolation ####

The main API exposed by this service provides object-graph interpolation:
 
    public class StringInterpolatorService {

        // called by Isis (which passes in all Isis properties)
        @PostConstruct
        public void init(final Map<String,String> properties) { ... }

        // public API
        public String interpolate(Object domainObject, String template) { ... }
        
        ...
    }

Using this API makes `domainObject` available as `this` in the template.

For example, assuming an instance of the `Customer` class, that in turn has relationships to the `Address` class:

    class Customer {
        private String firstName;
        private String lastName;
        private Address address;
        private Address billingAddress;
        
        // getters and setters omitted
    }
    class Address {
        private int houseNumber;
        private String town;
        private String postalCode;
        
        // getters and setters omitted
    }

then the following are valid expressions:

* `${this.firstName}`
* `${this.lastName != null? this.lastName : ''}`
* `${this.address.houseNumber}`

#### Object graph interpolation (using the lower-level API) ####

The service also offers a lower-level API which allows multiple objects to be made accessible from the context:

    public class StringInterpolatorService {

        public static class Root {
            ...
            public Root(final Object context) {
                this._this = context;
            }
            public Object getThis() { return _this; }
            ...
        }

        // public API
        public String interpolate(Root root, String template) { ... }
        
        ...
    }

The `Root` class can be extended as necessary.

For example, create a custom subclass of the `Root` class:

    final class CustomRoot extends StringInterpolatorService.Root {
        private Customer customer;
        public CustomRoot(Object context, Customer customer) {
            super(context);
            this.customer = customer;
        }
        public Customer getCustomer() {
            return customer;
        }
    }

The example above exposes the `customer` property.  This can then be used in the template, eg:

    @Test
    public void simple() throws Exception {
        String interpolated = service.interpolate(
            new CustomRoot(null, customer), "${customer.firstName}");
        assertThat(interpolated, is("Fred"));
    }


#### Strict Mode (applies to both APIs) ####

By default, any expression that cannot be parsed or would generate an exception (eg null pointer exception) is instead
returned unchanged in the interpolated string.

The service also provides a "strict" mode, which is useful for testing expressions:

    StringInterpolatorService service = new StringInterpolatorService().withStrict(true);
    
If enabled, then an exception is thrown instead.


## Related Modules/Services ##

Other modules can be found at the [Isis Add-ons](http://www.isisaddons.org) website.


## Change Log ##

* `1.13.0` - released against Isis 1.13.0
* `1.12.0` - released against Isis 1.12.0
* `1.11.0` - released against Isis 1.11.0
* `1.10.0` - released against Isis 1.10.0
* `1.9.0` - released against Isis 1.9.0
* `1.8.0` - released against Isis 1.8.0
* `1.7.0` - released against Isis 1.7.0
* `1.6.0` - re-released as part of isisaddons, with classes under package `org.isisaddons.module.stringinterpolator`


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

In addition to Apache Isis, this module depends on:

* `ognl:ognl` (ASL v2.0 License)


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
