# isis-wicket-summernote
Apache Isis module that provides WYSIWYG rich editor based on Summernote

[![Build Status](https://travis-ci.org/isisaddons/isis-wicket-summernote.png?branch=master)](https://travis-ci.org/isisaddons/isis-wicket-summernote)

This component, intended for use with [Apache Isis](http://isis.apache.org)'s Wicket viewer, integrates [Summernote editor](http://summernote.org).
*Summernote* is a JavaScript library based on [Bootstrap](http://getbootstrap.com/) that helps you create WYSIWYG editors online.



## Screenshots ##

The following screenshots show the example app's usage of the component with some sample fixture data:

![](https://raw.github.com/isisaddons/isis-wicket-summernote/master/images/010-install-fixtures.png)

Edit mode:

![](https://raw.github.com/isisaddons/isis-wicket-summernote/master/images/020-edit-mode.png)

View mode:

![](https://raw.github.com/isisaddons/isis-wicket-summernote/master/images/030-view-mode.png)

... shows an additional button to view those entities in a summary chart:


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 8 (>= 1.9.0)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-wicket-summernote.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host

Then log on using user: `sven`, password: `pass`


## API & Usage ##

Annotate any `String` property with `@org.isisaddons.wicket.summernote.cpt.applib.SummernoteEditor`.
You may use the annotation attributes to configure some aspects of the rich editor, e.g. its maximum height.


    import org.isisaddons.wicket.summernote.cpt.applib.SummernoteEditor;

    public class SummernoteEditorToDoItem implements Comparable<SummernoteEditorToDoItem> {
        ...
        private String notes = "";
        
        @javax.jdo.annotations.Column(allowsNull="true", length=400)
        @SummernoteEditor(height = 100, maxHeight = 300)
        public String getNotes() {
            return notes;
        }
        ...
    }.


## How to configure/use ##

You can either use this component "out-of-the-box", or you can fork this repo and extend to your own requirements.

#### "Out-of-the-box" ####

To use "out-of-the-box", add the component to your project's `dom` module's `pom.xml`:

    <dependency>
        <groupId>org.isisaddons.wicket.summernote</groupId>
        <artifactId>isis-wicket-summernote-cpt</artifactId>
        <version>1.11.0</version>
    </dependency>

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-wicket-summernote-cpt).


#### "Out-of-the-box" (-SNAPSHOT) ####

If you want to use the current `-SNAPSHOT`, then the steps are the same as above, except:

* when updating the classpath, specify the appropriate -SNAPSHOT version:

<pre>
    &lt;version&gt;1.11.0-SNAPSHOT&lt;/version&gt;
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

If instead you want to extend these components' functionality, then we recommend that you fork this repo.  The repo is
structured as follows:

* `pom.xml    ` - parent pom
* `cpt        ` - the component implementation
* `fixture    ` - fixtures, holding a sample domain objects and fixture scripts
* `webapp     ` - demo webapp (see above screenshots)

Only the `cpt` project (and its submodules) is released to Maven central.  The versions of the other modules
are purposely left at `0.0.1-SNAPSHOT` because they are not intended to be released.


## Change Log ##

* `1.11.0` - First version. Released against Isis 1.11.0

## Legal Stuff ##

Summernote editor is licenced under MIT licence.
IsisAddon Summernote is licenced under Apache 2 licence.

#### License ####

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


##  Maven deploy notes ##

Only the `cpt` module is deployed, and is done so using Sonatype's OSS support (see
[user guide](http://central.sonatype.org/pages/apache-maven.html)).

#### Release to Sonatype's Snapshot Repo ####

To deploy a snapshot, use:

    pushd cpt
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

    sh release.sh 1.10.0 \
                  1.11.0-SNAPSHOT \
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
    git push origin 1.11.0

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.

According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).
