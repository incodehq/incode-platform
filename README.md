# isis-metamodel-paraname8 #

[![Build Status](https://travis-ci.org/isisaddons/isis-metamodel-paraname8.png?branch=master)](https://travis-ci.org/isisaddons/isis-metamodel-paraname8)

This extension for Apache Isis' metamodel support means that the name of action parameters can be inferred from the parameter name itself; that is, there is no need to annotate the parameter.

## Background

Prior to Java 8 it was not possible to obtain the parameter names of methods using reflection.  Since Isis builds the UI from the code features, this required the parameters to be annotated with `@ParameterLayout(named=...)` or `@Named(...)`.

In Java 8 the Reflections API has been extended so that the parameter name is available (with the proviso that the code must also be compiled with a new `-parameters` compile flag).

This module provides an implemenation of Apache Isis' `FacetFactory` for Apache Isis so that this parameter name can be used, thereby avoiding the need to annotate action parameters. 


## Screenshot and Corresponding Code ##

From the demo app, here's the screenshot of an action to create a new object:

![](https://raw.github.com/isisaddons/isis-metamodel-paraname8/master/images/01-create-menu.png)

which renders the following prompt:

![](https://raw.github.com/isisaddons/isis-metamodel-paraname8/master/images/02-create-prompt.png)

The corresponding code is simply:
    
    public Paraname8DemoObject create(final String name) {
        ...
    }

Compare this to the "normal" way, which required using either the `@ParameterLayout(named=...)` annotation:

    public Paraname8DemoObject create(
            final @ParameterLayout(named="Name") String name) {
        ...
    }
    
or alternatively the older `@Named(...)` annotation:

    public Paraname8DemoObject create(
            final @Named("Name") String name) {
        ...
    }


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 8 (8.0.5 or later is required; `-parameters` was broken in very early builds of Java 8)
* [maven 3](http://maven.apache.org) (3.2.1 or later is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-metamodel-paraname8.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## How to Configure/Use ##

To use "out-of-the-box":

* in your project's root `pom.xml`, update the `maven-compiler-plugin` definition (in `<build>/<pluginManagement>/<plugins>`) to compile with JDK8, and specify the `-parameters` argument:

<pre>
    &lt;plugin&gt;
        &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
        &lt;artifactId&gt;maven-compiler-plugin&lt;/artifactId&gt;
        &lt;version&gt;3.1&lt;/version&gt;
        &lt;configuration&gt;
            &lt;source&gt;1.8&lt;/source&gt;
            &lt;target&gt;1.8&lt;/target&gt;
            &lt;compilerArgument&gt;-parameters&lt;/compilerArgument&gt;
        &lt;/configuration&gt;
        &lt;executions&gt;
            &lt;execution&gt;
                &lt;id&gt;source&lt;/id&gt;
                &lt;phase&gt;compile&lt;/phase&gt;
            &lt;/execution&gt;
            &lt;execution&gt;
                &lt;id&gt;test&lt;/id&gt;
                &lt;phase&gt;test-compile&lt;/phase&gt;
            &lt;/execution&gt;
        &lt;/executions&gt;
    &lt;/plugin&gt;
</pre>

* update your classpath in the `pom.xml` of your project's `integtests` module and also its `webapp` module:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.metamodel.paraname8&lt;/groupId&gt;
        &lt;artifactId&gt;isis-metamodel-paraname8-dom&lt;/artifactId&gt;
    &lt;/dependency&gt;
</pre>

* in your project's `webapp` module, update your `WEB-INF/isis.properties`:

<pre>
    isis.reflector.facets.include=\
                org.isisaddons.metamodel.paraname8.NamedFacetOnParameterParaname8Factory
</pre>

* in your project's `integtest` module, update your bootstrapping code to also install this facet factory:

<pre>
    private static IsisConfiguration testConfiguration() {
        final IsisConfigurationForJdoIntegTests testConfiguration = 
            new IsisConfigurationForJdoIntegTests();
        
        ...

        testConfiguration.put(
                "isis.reflector.facets.include",
                "org.isisaddons.metamodel.paraname8.NamedFacetOnParameterParaname8Factory");

        return testConfiguration;
    }
</pre>

Notes:
* Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-audit-dom).


## Configuring your IDE ##

Most IDEs compile the Java source code independently of Maven; this is certainly the case with both IntelliJ IDEA and Eclipse.  You will therefore need to ensure that the IDE is set up to build using the `-parameters` flag.
 
For IntelliJ IDEA, for example, this can be found under the "Settings" dialog:
 
![](https://raw.github.com/isisaddons/isis-metamodel-paraname8/master/images/03-intellij-support.png)
 
Other IDEs should have similar dialogues.

You'll also need to make sure that the IDE is set up to build and run with JDK8.  In IntelliJ, this can be found under the "Project Structure" dialog.

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
            
