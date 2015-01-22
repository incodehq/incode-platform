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


## Screenshots and Usage ##

The following screenshots and code fragments show an example app's usage of the module.

#### Installing the Fixture Data ####

Installing fixture data...

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/example-app-install-fixtures.png)

... creates a single demo `Order` entity, with properties of different data types and a collection of child (`OrderLine`) entities: 

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/example-app-order-entity.png)


#### The .docx template ####

The template `.docx` itself is marked up using smart tags, as specified on the
[DEVELOPER](http://msdn.microsoft.com/en-us/library/bb608625.aspx "How to show the DEVELOPER tab in Word") tab.

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-docx-template.png)

The actual `.docx` used in the example app can be found [here](https://github.com/isisaddons/isis-module-docx/blob/master/fixture/src/main/java/org/isisaddons/module/docx/fixture/dom/templates/CustomerConfirmation.docx?raw=true).

#### Generating the Document ####

In the example app's design the `CustomerConfirmation` example domain service is in essence an intelligent wrapper
around the `CustomerConfirmation.docx` template.  It contributes two actions to `Order`, the more
significant of which is `downloadCustomerConfirmation()`.  

The `.docx` is simply loaded as a simple resource from the classpath:
  
<pre>
@DomainService
public class CustomerConfirmation {

    private WordprocessingMLPackage wordprocessingMLPackage;

    @PostConstruct
    public void init() throws IOException, LoadTemplateException {
        final byte[] bytes = Resources.toByteArray(Resources.getResource(
                                this.getClass(), "CustomerConfirmation.docx"));
        wordprocessingMLPackage = docxService.loadPackage(new ByteArrayInputStream(bytes));
    }
    ...
}
</pre>
  
A more sophisticated service implementation could perhaps have retrieved and cached the .docx template bytes from a 
`Blob` property of a `CommunicationTemplate` entity, say.

Then, in the `downloadCustomerConfirmation` contributed action the `CustomerConfirmation` performs several steps:
* it converts the `Order` into the HTML input for the `DocxService`
* it calls the `DocxService` to convert this HTML into a `.docx` file
* finally it emits the generated `.docx` as a Blob; in the web browser this is then downloaded:

This can be seen below:

<pre>
public Blob downloadCustomerConfirmation(
        final Order order) throws IOException, JDOMException, MergeException {

    final org.w3c.dom.Document w3cDocument = asInputW3cDocument(order);

    final ByteArrayOutputStream docxTarget = new ByteArrayOutputStream();
    docxService.merge(
        w3cDocument, wordprocessingMLPackage, docxTarget, DocxService.MatchingPolicy.LAX);

    final String blobName = "customerConfirmation-" + order.getNumber() + ".docx";
    final String blobMimeType = 
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    final byte[] blobBytes = docxTarget.toByteArray();

    return new Blob(blobName, blobMimeType, blobBytes);
}
</pre>

Invoking this action is shown below:

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-generated-download.png)

which when opened in MS Word looks like:

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-generated-view.png)

The `CustomerConfirmation` service also contributes a second (prototype) action to allow the input HTML document
(fed into the `DocxService`) to be inspected:

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-input-download.png)

which when opened in a simple text editor looks like:

![](https://raw.github.com/isisaddons/isis-module-docx/master/images/customer-confirmation-input-view.png)

Note how the table rows are repeated for each `OrderLine` item, and similarly a new bullet list for each `Order`
preference.


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 7 (nb: Isis currently does not support JDK 8)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-docx.git
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
        &lt;groupId&gt;org.isisaddons.module.docx&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-docx-dom&lt;/artifactId&gt;
        &lt;version&gt;1.7.0&lt;/version&gt;
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


#### "Out-of-the-box" (-SNAPSHOT) ####

If you want to use the current `-SNAPSHOT`, then the steps are the same as above, except:

* when updating the classpath, specify the appropriate -SNAPSHOT version:

<pre>
    &lt;version&gt;1.8.0-SNAPSHOT&lt;/version&gt;
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
        

        
## Change Log ##

* `1.7.0` - released against Isis 1.7.0
* `1.6.0` - re-released as part of isisaddons, with classes under package `org.isisaddons.module.docx`


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

In addition to Apache Isis, this module depends on:

* `org.docx4j:docx4j` (ASL v2.0 License)
* `org.jdom:jdom2` (ASL v2.0 License)
    
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

    sh release.sh 1.8.0 \
                  1.9.0-SNAPSHOT \
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

    git push

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).
