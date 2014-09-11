isis-module-excel
========================

[![Build Status](https://travis-ci.org/isiaddons/isis-module-excel.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-excel)

This module, intended for use with [Apache Isis](http://isis.apache.org), provides a domain service so that a 
collection of (view model) object scan be exported to an Excel spreadsheet, or recreated by importing from Excel.

The underlying technology used is [Apache POI](http://poi.apache.org).

## Screenshots ##

The following screenshots show an example app's usage of the module.

#### Installing the Fixture Data ####

The fixture data creates a set of todo items in various categories:

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/010-install-fixtures.png)

#### Exporting items using the (example) bulk update manager ####

The example app has a bulk update manager as a wrapper around the ExcelService:

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/020-run-bulk-update-manager.png)

The (example) bulk update manager allows the end-user to define a criteria to exporting a (sub)set of items:

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/030-export.png)

which are then downloaded ...

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/040-open-xlsx.png)

... and can be viewed in Microsoft Excel:

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/050-xlsx.png)

#### Importing Exporting Excel ####

Using Excel the user can update data:

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/060-xlsx-updated.png)

... and the use the (example) bulk update manager to import:

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/070-import.png)

specifying the updated spreadsheet in the dialog:

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/080-import-dialog.png)

#### View models represent the Excel rows ####

For each row in the spreadsheet the `ExcelService` instantiates a corresponding view model.

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/090-line-items.png)

The view model can then provide a bulk `apply` action... 

![](https://raw.github.com/isisaddons/isis-module-excel/master/images/100-bulk-apply.png)

 to update the corresponding entity:
 
![](https://raw.github.com/isisaddons/isis-module-excel/master/images/110-updated-todo-item.png)



## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

To use "out-of-the-box":

* update your classpath by adding this dependency in your `dom` project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.excel&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-excel-dom&lt;/artifactId&gt;
        &lt;version&gt;1.6.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
                    ...,\
                    org.isisaddons.module.excel,\
                    ...
</pre>

Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-excel-dom).

If instead you want to extend this module's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml   ` - parent pom
* `dom       ` - the module implementation, depends on Isis applib
* `fixture   ` - fixtures, holding a sample domain objects and fixture scripts; depends on `dom`
* `integtests` - integration tests for the module; depends on `fixture`
* `webapp    ` - demo webapp (see above screenshots); depends on `dom` and `fixture`

Only the `dom` project is released to Maven Central Repo.  The versions of the other modules are purposely left at 
`0.0.1-SNAPSHOT` because they are not intended to be released.



## API ##

The API exposed by `ExcelService` is:

    public class ExcelService {

        public static class Exception extends RuntimeException { ... }
        
        @Programmatic
        public <T> Blob toExcel(
            final List<T> domainObjects, 
            final Class<T> cls, 
            final String fileName) 
            throws ExcelService.Exception { ... }

        @Programmatic
        public <T extends ViewModel> List<T> fromExcel(
            final Blob excelBlob, 
            final Class<T> cls) 
            throws ExcelService.Exception { ... };
    }

## Usage ##

Given:

    public class ToDoItemExportImportLineItem extends AbstractViewModel { ... }

which are wrappers around `ToDoItem` entities:

    final List<ToDoItem> items = ...;
    final List<ToDoItemExportImportLineItem> toDoItemViewModels = 
        Lists.transform(items, 
            new Function<ToDoItem, ToDoItemExportImportLineItem>(){
                @Override
                public ToDoItemExportImportLineItem apply(final ToDoItem toDoItem) {
                    return container.newViewModelInstance(
                        ToDoItemExportImportLineItem.class, 
                        bookmarkService.bookmarkFor(toDoItem).getIdentifier());
                }
            });

then the following creates an Isis `Blob` (bytestream) containing the spreadsheet of these view models:

    return excelService.toExcel(toDoItemViewModels, ToDoItemExportImportLineItem.class, fileName);

and conversely:

    Blob spreadsheet = ...;
    List<ToDoItemExportImportLineItem> lineItems = 
        excelService.fromExcel(spreadsheet, ToDoItemExportImportLineItem.class);

recreates view models from a spreadsheet.


## Related Modules ##

See also the [Excel wicket extension](https://github.com/isisaddons/isis-wicket-excel), which makes every collection 
downloadable as an Excel spreadsheet.


## Legal Stuff

#### License ####

    Copyright 2013~2014 Dan Haywood

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

* `org.apache.poi:poi` (ASL v2.0 License)
* `org.apache.poi:poi-ooxml` (ASL v2.0 License)
* `org.apache.poi:poi-ooxml-schemas` (ASL v2.0 License)

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

    sh release.sh 1.6.0 \
                  1.6.1-SNAPSHOT \
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

     `sh bumpver.sh 1.6.0`

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

If (in the `dom`'s `pom.xml`) the `nexus-staging-maven-plugin` has the `autoReleaseAfterClose` setting set to `true`,
then the above command will automatically stage, close and the release the repo.  Sync'ing to Maven Central should 
happen automatically.  According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update 
[search](http://search.maven.org).

If instead the `autoReleaseAfterClose` setting is set to `false`, then the repo will require manually closing and 
releasing either by logging onto the [Sonatype's OSS staging repo](https://oss.sonatype.org) or alternatively by 
releasing from the command line using `mvn nexus-staging:release`.

Finally, don't forget to update the release to next snapshot, eg:

    sh bumpver.sh 1.6.1-SNAPSHOT

and then push changes.
