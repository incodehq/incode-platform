/*
 *  Copyright 2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
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
package org.isisaddons.module.excel.fixture.app;

import java.util.List;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem.Category;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem.Subcategory;
import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.value.Blob;

@Named("Import/export manager")
@MemberGroupLayout(left={"File", "Criteria"})
@Bookmarkable
public class ExcelModuleDemoToDoItemBulkUpdateManager extends AbstractViewModel {

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
    public ExcelModuleDemoToDoItemBulkUpdateManager changeFileName(final String fileName) {
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
    public ExcelModuleDemoToDoItemBulkUpdateManager select(
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
    public List<ExcelModuleDemoToDoItem> getToDoItems() {
        return container.allMatches(ExcelModuleDemoToDoItem.class,
                Predicates.and(
                    ExcelModuleDemoToDoItem.Predicates.thoseOwnedBy(currentUserName()),
                    ExcelModuleDemoToDoItem.Predicates.thoseCompleted(isComplete()),
                    ExcelModuleDemoToDoItem.Predicates.thoseCategorised(getCategory(), getSubcategory())));
    }


    // //////////////////////////////////////
    // export (action)
    // //////////////////////////////////////
    
    @MemberOrder(name="toDoItems", sequence="1")
    @ActionSemantics(Of.SAFE)
    public Blob export() {
        final String fileName = withExtension(getFileName(), ".xlsx");
        final List<ExcelModuleDemoToDoItem> items = getToDoItems();
        return toExcel(fileName, items);
    }

    public String disableExport() {
        return getFileName() == null? "file name is required": null;
    }

    private static String withExtension(String fileName, String fileExtension) {
        return fileName.endsWith(fileExtension) ? fileName : fileName + fileExtension;
    }

    private Blob toExcel(final String fileName, final List<ExcelModuleDemoToDoItem> items) {
        final List<ExcelModuleDemoToDoItemBulkUpdateLineItem> toDoItemViewModels = Lists.transform(items, toLineItem());
        return excelService.toExcel(toDoItemViewModels, ExcelModuleDemoToDoItemBulkUpdateLineItem.class, fileName);
    }

    private Function<ExcelModuleDemoToDoItem, ExcelModuleDemoToDoItemBulkUpdateLineItem> toLineItem() {
        return new Function<ExcelModuleDemoToDoItem, ExcelModuleDemoToDoItemBulkUpdateLineItem>(){
            @Override
            public ExcelModuleDemoToDoItemBulkUpdateLineItem apply(final ExcelModuleDemoToDoItem toDoItem) {
                ExcelModuleDemoToDoItemBulkUpdateLineItem template = new ExcelModuleDemoToDoItemBulkUpdateLineItem();
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
    public List<ExcelModuleDemoToDoItemBulkUpdateLineItem> importBlob(
            final @Named("Excel spreadsheet") Blob spreadsheet) {
        List<ExcelModuleDemoToDoItemBulkUpdateLineItem> lineItems =
                excelService.fromExcel(spreadsheet, ExcelModuleDemoToDoItemBulkUpdateLineItem.class);
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
    private ExcelModuleDemoToDoItemBulkUpdateService toDoItemExportImportService;

}
