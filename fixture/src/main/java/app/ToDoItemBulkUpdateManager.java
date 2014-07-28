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

import com.danhaywood.isis.domainservice.excel.applib.ExcelService;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import dom.todo.ToDoItem;
import dom.todo.ToDoItems;
import dom.todo.ToDoItem.Category;
import dom.todo.ToDoItem.Subcategory;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Blob;

@MemberGroupLayout(left={"File", "Criteria"})
@Bookmarkable
public class ToDoItemBulkUpdateManager extends AbstractViewModel {

    // //////////////////////////////////////
    
    public String title() {
        return "Import/export manager";
    }
    
    // //////////////////////////////////////
    // ViewModel implementation
    // //////////////////////////////////////
    

    @Override
    public String viewModelMemento() {
        return toDoItemExportImportService.mementoFor(this);
    }

    @Override
    public void viewModelInit(String mementoStr) {
        toDoItemExportImportService.initOf(mementoStr, this);
    }

    
    // //////////////////////////////////////
    // fileName (property)
    // changeFileName
    // //////////////////////////////////////

    private String fileName;
    
    @MemberOrder(name="File", sequence="1")
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // //////////////////////////////////////

    @ActionSemantics(Of.IDEMPOTENT)
    @Named("Change")
    @MemberOrder(name="fileName", sequence="1")
    public ToDoItemBulkUpdateManager changeFileName(final String fileName) {
        setFileName(fileName);
        return toDoItemExportImportService.newBulkUpdateManager(this);
    }
    public String default0ChangeFileName() {
        return getFileName();
    }

    
    // //////////////////////////////////////
    // category (property)
    // subcategory (property)
    // complete (property)
    // select (action)
    // //////////////////////////////////////
    
    private Category category;

    @MemberOrder(name="Criteria", sequence="1")
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    // //////////////////////////////////////

    private Subcategory subcategory;

    @MemberOrder(name="Criteria", sequence="2")
    public Subcategory getSubcategory() {
        return subcategory;
    }
    public void setSubcategory(final Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    // //////////////////////////////////////

    private boolean complete;

    @MemberOrder(name="Criteria", sequence="3")
    public boolean isComplete() {
        return complete;
    }
    public void setComplete(boolean completed) {
        this.complete = completed;
    }
    
    // //////////////////////////////////////

    @Named("Change")
    @MemberOrder(name="complete", sequence="1")
    public ToDoItemBulkUpdateManager select(
            @Named("Category") final Category category,
            @Named("Subcategory") @Optional final Subcategory subcategory,
            @Named("Completed?") final boolean completed) {
        setCategory(category);
        setSubcategory(subcategory);
        setComplete(completed);
        return toDoItemExportImportService.newBulkUpdateManager(this);
    }
    public Category default0Select() {
        return getCategory();
    }
    public Subcategory default1Select() {
        return getSubcategory();
    }
    public boolean default2Select() {
        return isComplete();
    }
    public List<Subcategory> choices1Select(
            final Category category) {
        return Subcategory.listFor(category);
    }
    public String validateSelect(
            final Category category, 
            final Subcategory subcategory, 
            final boolean completed) {
        return Subcategory.validate(category, subcategory);
    }

    private String currentUserName() {
        return container.getUser().getName();
    }


    // //////////////////////////////////////
    // allToDos
    // //////////////////////////////////////

    @SuppressWarnings("unchecked")
    @Render(Type.EAGERLY)
    public List<ToDoItem> getToDoItems() {
        return container.allMatches(ToDoItem.class, 
                Predicates.and(
                    ToDoItem.Predicates.thoseOwnedBy(currentUserName()), 
                    ToDoItem.Predicates.thoseCompleted(isComplete()),
                    ToDoItem.Predicates.thoseCategorised(getCategory(), getSubcategory())));
    }


    // //////////////////////////////////////
    // export (action)
    // //////////////////////////////////////
    
    @MemberOrder(name="toDoItems", sequence="1")
    @ActionSemantics(Of.SAFE)
    public Blob export() {
        final String fileName = withExtension(getFileName(), ".xlsx");
        final List<ToDoItem> items = getToDoItems();
        return toExcel(fileName, items);
    }

    public String disableExport() {
        return getFileName() == null? "file name is required": null;
    }

    private static String withExtension(String fileName, String fileExtension) {
        return fileName.endsWith(fileExtension) ? fileName : fileName + fileExtension;
    }

    private Blob toExcel(final String fileName, final List<ToDoItem> items) {
        final List<ToDoItemBulkUpdateLineItem> toDoItemViewModels = Lists.transform(items, toLineItem());
        return excelService.toExcel(toDoItemViewModels, ToDoItemBulkUpdateLineItem.class, fileName);
    }

    private Function<ToDoItem, ToDoItemBulkUpdateLineItem> toLineItem() {
        return new Function<ToDoItem, ToDoItemBulkUpdateLineItem>(){
            @Override
            public ToDoItemBulkUpdateLineItem apply(final ToDoItem toDoItem) {
                ToDoItemBulkUpdateLineItem template = new ToDoItemBulkUpdateLineItem();
                template.modifyToDoItem(toDoItem);
                return toDoItemExportImportService.newLineItem(template);
            }
        };
    }


    // //////////////////////////////////////
    // import (action)
    // //////////////////////////////////////

    @MemberOrder(name="toDoItems", sequence="2")
    @Named("Import")
    public List<ToDoItemBulkUpdateLineItem> importBlob(
            final @Named("Excel spreadsheet") Blob spreadsheet) {
        List<ToDoItemBulkUpdateLineItem> lineItems = 
                excelService.fromExcel(spreadsheet, ToDoItemBulkUpdateLineItem.class);
        container.informUser(lineItems.size() + " items imported");
        return lineItems;
    }
    

    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private ToDoItemBulkUpdateService toDoItemExportImportService;

}
