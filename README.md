# isis-module-fakedata #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-fakedata.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-fakedata)

This module, intended for use with [Apache Isis](http://isis.apache.org), provides a domain service that generates
fake random data.  The random values generated can then be used within unit and integration tests.

The module consists of a single domain service `FakeDataDomainService`.  This can be injected into fixtures and
integration tests just like any other domain service.

In addition, this module also acts as a useful regression suite for DataNucleus' persistence of value types (including 
our custom mappings of Isis' own value types).

## Screenshots ##

The example app consists of a single domain entity that has a property for each of the value types supported by Isis.

Installing the fixture scenario:

![](https://raw.github.com/isisaddons/isis-module-fakedata/master/images/010-install-fixtures.png)

will return an example demo object:

![](https://raw.github.com/isisaddons/isis-module-fakedata/master/images/020-demo-object.png)


## Fixtures and Integration Tests

Probably of more interest are the fixtures and integration tests that actually use the `FakeDataService`. 

For example the `FakeDataDemoObjectUpdate` fixture script will update a demo object using the provided values (set as 
properties of the fixture script itself).   However, if no value has been set by the calling test, then a random value,
obtained from `FakeDataService`, will be used instead:
 
    public class FakeDataDemoObjectUpdate extends DiscoverableFixtureScript {

        private FakeDataDemoObject fakeDataDemoObject; 
        public FakeDataDemoObject getFakeDataDemoObject() { ... }
        public void setFakeDataDemoObject(final FakeDataDemoObject fakeDataDemoObject) { ... }

        ...
        private Boolean someBoolean;
        public Boolean getSomeBoolean() { ... }
        public void setSomeBoolean(final Boolean someBoolean) { ... }

        private Character someChar;
        public Character getSomeChar() { ... }
        public void setSomeChar(final Character someChar) { ... }
        
        private Byte someByte;
        public Byte getSomeByte() { ... }
        public void setSomeByte(final Byte someByte) { ... }
        ...
        
        protected void execute(final ExecutionContext executionContext) {

            ...
            this.defaultParam("someBoolean", executionContext, fakeDataService.booleans().any());
            this.defaultParam("someChar", executionContext, fakeDataService.chars().any());
            this.defaultParam("someByte", executionContext, fakeDataService.bytes().any());
            ...
    
            // updates
            final FakeDataDemoObject fakeDataDemoObject = getFakeDataDemoObject();
            
            ...
            wrap(fakeDataDemoObject).updateSomeBoolean(getSomeBoolean());
            wrap(fakeDataDemoObject).updateSomeByte(getSomeByte());
            wrap(fakeDataDemoObject).updateSomeShort(getSomeShort());
            ... 
        }
    
        @javax.inject.Inject
        private FakeDataService fakeDataService;
    }

The `FakeDataService` can also be used within integration tests.  For example, in `FakeDataDemoObjectsTest` a fake
value is used to obtain a blob for update:

        @Test
        public void when_blob() throws Exception {

            // given
            Assertions.assertThat(fakeDataDemoObject.getSomeBlob()).isNull();

            final Blob theBlob = fakeDataService.isisBlobs().anyPdf();


            // when
            updateScript.setFakeDataDemoObject(fakeDataDemoObject);
            updateScript.setSomeBlob(theBlob);

            fixtureScripts.runFixtureScript(updateScript, null);

            nextTransaction();


            // then
            fakeDataDemoObject = wrap(fakeDataDemoObjects).listAll().get(0);

            Assertions.assertThat(fakeDataDemoObject.getSomeBlob()).isNotNull();
            Assertions.assertThat(fakeDataDemoObject.getSomeBlob().getMimeType().toString()).isEqualTo("application/pdf");
        }

Note the use of `FakeDataService` in the "given" to obtain a PDF blob.

## How to run the Demo App ##

The prerequisite software is:

* Java JDK 7 (nb: Isis currently does not support JDK 8)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-fakedata.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

#### "Out-of-the-box" ####

To use "out-of-the-box":

* update your classpath by adding this dependency in your project's `dom` module's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.fakedata&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-fakedata-dom&lt;/artifactId&gt;
        &lt;version&gt;1.9.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

NB: not yet released, use -SNAPSHOT (below)

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
                    ...,\
                    org.isisaddons.module.fakedata.dom,\
                    ...
</pre>

Notes:
* Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-fakedata-dom)).

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

## API and Implementation##

The `FakeDataService` defines the following API:

    public interface FakeDataService {
    }
        public Name name() { ... }
        public Comms comms() { ... }
        public Lorem lorem() { ... }
        public Address address() { ... }
        public CreditCard creditCard() { ... }
        public Book book() { ... }
    
        public Bytes bytes() { ... }
        public Shorts shorts() { ... }
        public Integers ints() { ... }
        public Longs longs() { ... }
        public Floats floats() { ... }
        public Doubles doubles() { ... }
        public Chars chars() { ... }
        public Booleans booleans() { ... }
    
        public Strings strings() { ... }
        
        public Collections collections() { ... }
        public Enums enums() { ... }

        public JavaUtilDates javaUtilDates() { ... }
        public JavaSqlDates javaSqlDates() { ... }
        public JavaSqlTimestamps javaSqlTimestamps() { ... }
        public JodaLocalDates jodaLocalDates() { ... }
        public JodaDateTimes jodaDateTimes() { ... }
        public JodaPeriods jodaPeriods() { ... }

        public BigDecimals bigDecimals() { ... }
        public BigIntegers bigIntegers() { ... }
        
        public Urls urls() { ... }
        public Uuids uuids() { ... }

        public IsisPasswords isisPasswords() { ... }
        public IsisMoneys isisMoneys() { ... }
        public IsisBlobs isisBlobs() { ... }
        public IsisClobs isisClobs() { ... }
        
    }
    
where each of the returned classes then provides suitable methods for obtaining values within that domain of values.
For example, `Names` provides:

    public class Names ... {
        public String fullName() { ... }
        public String firstName() { ... }
        public String lastName() { ... }
        public String prefix() { ... }
        public String suffix() { ... }
    }

while `IsisBlobs` provides:

    public class IsisBlobs ... {
        public Blob any() { ... }
        public Blob anyJpg() { ... }
        public Blob anyPdf() { ... }
    }

## Known issues ##

None currently.

## Change Log ##

* Not yet released to Maven


## Legal Stuff ##
 
#### License ####

    Copyright 2014-2015 Dan Haywood

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
