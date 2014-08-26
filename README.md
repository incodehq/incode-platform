# isis-module-docx #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-docx.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-docx)

This module, intended for use with [Apache Isis](http://isis.apache.org), provides a mail-merge capability of input 
data into an MS Word `.docx` template.

The module consists of a single domain service, `DocxService`.  This provides an API to merge a `.docx` template
against its input data.  The input data is represented as a simple HTML file.

The service supports several data types:

- plain text
- rich text
- date
- bulleted list
- tables

The implementation uses [docx4j](http://www.docx4java.org), [guava](https://code.google.com/p/guava-libraries/) and
[jdom2](http://www.jdom.org).  Databinding to custom XML parts (the `.docx` file format's in-built support) is *not*
used (as repeating datasets - required for lists and tables - was not supported prior to Word 2013).


## Screenshots ##

The following screenshots show an example app's usage of the module.

#### Installing the Fixture Data

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/example-app-home-page.png)
![](https://raw.github.com/isisaddons/isis-module-docx/master/images/example-app-install-fixtures.png)
![](https://raw.github.com/isisaddons/isis-module-docx/master/images/example-app-order-entity.png)

#### The .docx template

The `CustomerConfirmation` example domain service acts as an intelligent wrapper around the `CustomerConfirmation.docx`
template (in this example, loaded as a simple resource from the classpath).  The `CustomerConfirmation`'s
 responsibilities are to convert an `Order` into the HTML input for the `DocxService`, and then to actually call the
 `DocxService`.

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-docx-template.png)

The template `.docx` is marked up using smart tags, as specified on the
[DEVELOPER](http://msdn.microsoft.com/en-us/library/bb608625.aspx "How to show the DEVELOPER tab in Word") tab.

The actual `.docx` can be found [here](https://github.com/isisaddons/isis-module-docx/blob/master/fixture/src/main/java/org/isisaddons/module/docx/fixture/dom/templates/CustomerConfirmation.docx?raw=true).

### Generated Document

The `CustomerConfirmation` service contributes two actions to the `Order` entity.

The first is to generate the `.docx` document:

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-generated-download.png)
![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-generated-view.png)

The second is a prototype action to inspect the input HTML document that is fed into the `DocxService`:

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-input-download.png)
![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-input-view.png)


Note how the table rows are repeated for each `OrderLine` item, and similarly a new bullet list for each `Order`
preference.


## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

To use "out-of-the-box":

* update your classpath by adding this dependency in your dom project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.docx&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-docx-dom&lt;/artifactId&gt;
        &lt;version&gt;1.6.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
                    ...,\
                    org.isisaddons.module.docx.dom,\
                    ...
</pre>

Notes:
* Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-docx-dom).

If instead you want to extend this module's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml`    // parent pom
* `dom`        // the module implementation, depends on Isis applib
* `fixture`    // fixtures, holding a sample domain objects and fixture scripts; depends on `dom`
* `integtests` // integration tests for the module; depends on `fixture`
* `webapp`     // demo webapp (see above screenshots); depends on `dom` and `fixture`

Only the `dom` project is released to Maven Central Repo.  The versions of the other modules are purposely left at 
`0.0.1-SNAPSHOT` because they are not intended to be released.


## API & Implementation ##

The main API is:
 
    public void merge(
             String html, 
             InputStream docxTemplate, 
             OutputStream docxTarget, 
             MatchingPolicy matchingPolicy) 
         throws LoadInputException, 
                LoadTemplateException, 
                MergeException

The `MatchingPolicy` specifies whether unmatched input values or unmatched placeholders in the template are allowed
or should be considered as a failure.

Overloaded versions of the `merge(...)` method exist:

  - the `html` may instead be provided as a `org.w3c.dom.Document`
  - the `docxTemplate` may instead be provided as a doc4j `WordprocessingMLPackage` (an in-memory object structure
    that could be considered as analogous to an w3c `Document`, but representing a `.docx`).

The `WordprocessingMLPackage` can be obtained from a supplementary API method:

    public WordprocessingMLPackage loadPackage(
            InputStream docxTemplate) 
        throws LoadTemplateException

This exists because the parsing of the input stream into a `WordprocessingMLPackage` is not particularly quick.
Therefore clients may wish to cache this in-memory object structure.  If calling the overloaded version of `merge(...)` that accepts the `WordprocessingMLPackage` then the service performs a defensive copy of the template.

In the example app the `CustomerConfirmation` domain service does indeed cache this package in its `init()` method.



## input HTML ##

The input data is provided as an XHTML form, and the service merges using the `@id` attribute of the XHTML against the
tag of the smart tag field in the `.docx`.

To specify a **plain** field, use:

    <p id="CustomerId" class="plain">12345</p>

To specify a **date** field, use:

    <p id="RenewalDate" class="date">20-Jan-2013</p>

To specify a **rich** field, use:

    <p id="PromoText" class="rich">
        Roll up, roll up, step right this way!
    </p>

To specify a **list** field, use:

    <ul id="Albums">
        <li>
            <p>Please Please Me</p>
            <p>1963</p>
        </li>
        <li>
            <p>Help</p>
        </li>
        <li>
            <p>Sgt Peppers Lonely Hearts Club Band</p>
            <p>1965</p>
            <p>Better than Revolver, or not?</p>
        </li>
    </ul>

To specify a **table** field, use:

    <table id="BandMembers">
        <tr>
            <td>John Lennon</td>
            <td>Rhythm guitar</td>
        </tr>
        <tr>
            <td>Paul McCartney</td>
            <td>Bass guitar</td>
        </tr>
        <tr>
            <td>George Harrison</td>
            <td>Lead guitar</td>
        </tr>
        <tr>
            <td>Ringo Starr</td>
            <td>Drums</td>
        </tr>
    </table>


## Generated output ##

For simple data types such as plain text, rich text and date, the service simply substitutes the input data into the
placeholder fields in the `.docx`.

For lists, the service expects the contents of the placeholder to be a bulleted list, with an optional second paragraph
of a different style.  The service clones the paragraphs for each item in the input list.  If the input specifies more
than one paragraph in the list item, then the second paragraph from the template is used for those additional paragraphs.

For tables, the service expects the placeholder to be a table, with a header and either one or two body rows.  The
header is left untouched, the body rows are used as the template for the input data.  Any surplus cells in the input
data are ignored.
    
    

## Related Modules/Services ##

Other modules can be found at the [Isis Add-ons](http://www.isisaddons.org) website.

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

    <dependencies>
        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.docx4j</groupId>
            <artifactId>docx4j</artifactId>
            <version>2.8.1</version>
        </dependency>
        <dependency>
            <!-- ASL v2.0 -->
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>16.0.1</version>
        </dependency>
        <dependency>
            <!-- https://raw.github.com/hunterhacker/jdom/master/LICENSE.txt -->
            <!-- Similar to Apache License but with the acknowledgment clause removed -->
            <groupId>org.jdom</groupId>
            <artifactId>jdom2</artifactId>
            <version>2.0.3</version>
        </dependency>
    </dependencies>

    
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

#### Release to Maven Central (scripted process) ####

The `release.sh` script automates the release process.  It performs the following:

* perform sanity check (`mvn clean install -o`) that everything builds ok
* bump the `pom.xml` to a specified release version, and tag
* perform a double check (`mvn clean install -o`) that everything still builds ok
* release the code using `mvn clean deploy`
* bump the `pom.xml` to a specified release version

For example:

    sh release.sh 1.6.1 \
                  1.6.2-SNAPSHOT \
                  dan@haywood-associates.co.uk \
                  "this is not really my passphrase"
    
where
* `$1` is the release version
* `$2` is the snapshot version
* `$3` is the email of the secret key (`~/.gnupg/secring.gpg`) to use for signing
* `$4` is the corresponding passphrase for that secret key.

If the script completes successfully, then push changes:

    git push
    
If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.

#### Release to Maven Central (manual process) ####

If you don't want to use `release.sh`, then the steps can be performed manually.

To start, call `bumpver.sh` to bump up to the release version, eg:

     `sh bumpver.sh 1.6.1`

which:
* edit the parent `pom.xml`, to change `${isis-module-command.version}` to version
* edit the `dom` module's pom.xml version
* commit the changes
* if a SNAPSHOT, then tag

Next, do a quick sanity check:

    mvn clean install -o
    
All being well, then release from the `dom` module:

    pushd dom
    mvn clean deploy -P release \
        -Dpgp.secretkey=keyring:id=dan@haywood-associates.co.uk \
        -Dpgp.passphrase="literal:this is not really my passphrase"
    popd

where (for example):
* "dan@haywood-associates.co.uk" is the email of the secret key (`~/.gnupg/secring.gpg`) to use for signing
* the pass phrase is as specified as a literal

Other ways of specifying the key and passphrase are available, see the `pgp-maven-plugin`'s 
[documentation](http://kohsuke.org/pgp-maven-plugin/secretkey.html)).

If (in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the `autoReleaseAfterClose` setting set to `true`,
then the above command will automatically stage, close and the release the repo.  Sync'ing to Maven Central should 
happen automatically.  According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update 
[search](http://search.maven.org).

If instead the `autoReleaseAfterClose` setting is set to `false`, then the repo will require manually closing and 
releasing either by logging onto the [Sonatype's OSS staging repo](https://oss.sonatype.org) or alternatively by 
releasing from the command line using `mvn nexus-staging:release`.

Finally, don't forget to update the release to next snapshot, eg:

    sh bumpver.sh 1.6.2-SNAPSHOT

and then push changes.
