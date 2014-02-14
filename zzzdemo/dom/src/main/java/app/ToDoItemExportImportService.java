/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package app;

import java.util.List;

import javax.annotation.PostConstruct;

import com.danhaywood.isis.domainservice.excel.applib.ExcelService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import dom.todo.ToDoItem;
import dom.todo.ToDoItems;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.value.Blob;

@Named("Bulk Edit")
public class ToDoItemExportImportService {

    private static final String EXPORT_FILE_NAME_XLSX = "export.xlsx";

    // //////////////////////////////////////

    
    @PostConstruct
    public void init() {
        if(bookmarkService == null) {
            throw new IllegalStateException("Require BookmarkService to be configured");
        }
        if(excelService == null) {
            throw new IllegalStateException("Require ExcelService to be configured");
        }
    }
    
    // //////////////////////////////////////
    // export (action)
    // //////////////////////////////////////

    public Blob export() {
        final String fileName = EXPORT_FILE_NAME_XLSX;
        final List<ToDoItem> items = toDoItems.allToDos();
        final List<ToDoItemExportImportLineItem> toDoItemViewModels = Lists.transform(items, toLineItem());
        return excelService.toExcel(toDoItemViewModels, ToDoItemExportImportLineItem.class, fileName);
    }

    private Function<ToDoItem, ToDoItemExportImportLineItem> toLineItem() {
        return new Function<ToDoItem, ToDoItemExportImportLineItem>(){
            @Override
            public ToDoItemExportImportLineItem apply(final ToDoItem toDoItem) {
                return container.newViewModelInstance(ToDoItemExportImportLineItem.class, identifierFor(toDoItem));
            }};
    }

    
    // //////////////////////////////////////
    // importBlob (action)
    // //////////////////////////////////////

    @Named("Import")
    public void importBlob(final @Named("Excel spreadsheet") Blob spreadsheet) {
        List<ToDoItemExportImportLineItem> lineItems = 
                excelService.fromExcel(spreadsheet, ToDoItemExportImportLineItem.class);
        container.informUser(lineItems.size() + " items imported");
    }
    
    // //////////////////////////////////////

    @Programmatic
    public String identifierFor(ToDoItem toDoItem) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(toDoItem);
        return bookmark.getIdentifier();
    }

    @Programmatic
    public ToDoItem lookupByIdentifier(String identifier) {
        Bookmark bookmark = bookmarkService.bookmarkFor(ToDoItem.class, identifier);
        return (ToDoItem)bookmarkService.lookup(bookmark);
    }
    
    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private ToDoItems toDoItems; 
    
    @javax.inject.Inject
    private BookmarkService bookmarkService;

}
