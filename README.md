isis-domainservice-excel
========================

[![Build Status](https://travis-ci.org/danhaywood/isis-domainservice-excel.png?branch=master)](https://travis-ci.org/danhaywood/isis-domainservice-excel)

Integrates with [Apache Isis](http://isis/apache.org)', providing a domain service so that a collection of (view model) object scan be exported to an Excel spreadsheet, or recreated by importing from Excel.  The underlying technology used is [Apache POI](http://poi.apache.org).

## API & Implementation

The API exposed by `ExcelService` is:

    public interface ExcelService {

        public static class Exception extends RuntimeException { ... }
        
        @Programmatic
        public <T> Blob toExcel(
            final List<T> domainObjects, 
            final Class<T> cls, final 
            String fileName) 
            throws ExcelService.Exception;

        @Programmatic
        public <T extends ViewModel> List<T> fromExcel(
            final Blob excelBlob, 
            final Class<T> cls) 
            throws ExcelService.Exception;
    }

The class that implements this API is `com.danhaywood.isis.domainservice.excel.impl.ExcelServiceImpl`.    

## Usage

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

## Maven Configuration

In the root `pom.xml`, add:

    <dependency>
        <groupId>com.danhaywood.isis.domainservice</groupId>
        <artifactId>danhaywood-isis-domainservice-excel</artifactId>
        <version>x.y.z</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>

where `x.y.z` currently is 1.4.0-SNAPSHOT (though the plan is to release this code into the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-domainservice-excel)).

In the `pom.xml` for your "dom" module, add:
    
    <dependency>
        <groupId>com.danhaywood.isis.domainservice</groupId>
        <artifactId>danhaywood-isis-domainservice-excel-applib</artifactId>
    </dependency>

In the `pom.xml` for your "webapp" module, add:

    <dependency>
        <groupId>com.danhaywood.isis.domainservice</groupId>
        <artifactId>danhaywood-isis-domainservice-excel-impl</artifactId>
    </dependency>

## Registering the service

In the `WEB-INF\isis.properties` file, add:

    isis.services = ...,\
                    # Excel domain service, \
                    com.danhaywood.isis.domainservice.excel.impl.ExcelServiceImpl,\
                    ...

## Legal Stuff

### License

    Copyright 2013 Dan Haywood

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

This module depends upon the following:

    <dependencies>
        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.apache.isis.viewer</groupId>
            <artifactId>isis-viewer-wicket-ui</artifactId>
            <version>${isis-viewer-wicket.version}</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>${poi.version}</version>
        </dependency>
        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>${poi.version}</version>
        </dependency>
        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml-schemas</artifactId>
            <version>${poi.version}</version>
        </dependency>        
    </dependencies>
