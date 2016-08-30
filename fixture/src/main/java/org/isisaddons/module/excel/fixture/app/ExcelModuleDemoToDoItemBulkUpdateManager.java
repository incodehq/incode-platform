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

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.module.excel.dom.WorksheetContent;
import org.isisaddons.module.excel.dom.WorksheetSpec;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem.Category;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem.Subcategory;

import lombok.Getter;
import lombok.Setter;

@DomainObject(
        nature = Nature.VIEW_MODEL
)
@DomainObjectLayout(
        named ="Import/export manager",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class ExcelModuleDemoToDoItemBulkUpdateManager {


    public static final WorksheetSpec WORKSHEET_SPEC =
            new WorksheetSpec(ExcelModuleDemoToDoItemBulkUpdateLineItem.class, "line-items");


    public String title() {
        return "Import/export manager";
    }
    


    @Getter @Setter
    private String fileName;

    //region > changeFileName (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    public ExcelModuleDemoToDoItemBulkUpdateManager changeFileName(final String fileName) {
        setFileName(fileName);
        return toDoItemExportImportService.newBulkUpdateManager(this);
    }
    public String default0ChangeFileName() {
        return getFileName();
    }

    //endregion


    @Getter @Setter
    private Category category;

    @Getter @Setter
    private Subcategory subcategory;

    @Getter @Setter
    private boolean complete;

    //region > select (action)

    @Action
    public ExcelModuleDemoToDoItemBulkUpdateManager select(
            @ParameterLayout(named="Category") final Category category,
            @ParameterLayout(named="Subcategory") @Parameter(optionality = Optionality.OPTIONAL) final Subcategory subcategory,
            @ParameterLayout(named="Completed?") final boolean completed) {
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

    //endregion


    //region > toDoItems (derived collection)

    @SuppressWarnings("unchecked")
    @Collection
    public List<ExcelModuleDemoToDoItem> getToDoItems() {
        return container.allMatches(ExcelModuleDemoToDoItem.class,
                Predicates.and(
                    ExcelModuleDemoToDoItem.Predicates.thoseOwnedBy(currentUserName()),
                    ExcelModuleDemoToDoItem.Predicates.thoseCompleted(isComplete()),
                    ExcelModuleDemoToDoItem.Predicates.thoseCategorised(getCategory(), getSubcategory())));
    }

    //endregion


    //region > export (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    public Blob export() {
        final String fileName = withExtension(getFileName(), ".xlsx");
        final List<ExcelModuleDemoToDoItem> items = getToDoItems();
        return toExcel(fileName, items);
    }

    public String disableExport() {
        return getFileName() == null? "file name is required": null;
    }

    private static String withExtension(final String fileName, final String fileExtension) {
        return fileName.endsWith(fileExtension) ? fileName : fileName + fileExtension;
    }

    private Blob toExcel(final String fileName, final List<ExcelModuleDemoToDoItem> items) {
        final List<ExcelModuleDemoToDoItemBulkUpdateLineItem> toDoItemViewModels = Lists.transform(items, toLineItem());
        return excelService.toExcel(new WorksheetContent(toDoItemViewModels, WORKSHEET_SPEC), fileName);
    }

    private Function<ExcelModuleDemoToDoItem, ExcelModuleDemoToDoItemBulkUpdateLineItem> toLineItem() {
        return new Function<ExcelModuleDemoToDoItem, ExcelModuleDemoToDoItemBulkUpdateLineItem>(){
            @Override
            public ExcelModuleDemoToDoItemBulkUpdateLineItem apply(final ExcelModuleDemoToDoItem toDoItem) {
                final ExcelModuleDemoToDoItemBulkUpdateLineItem template = new ExcelModuleDemoToDoItemBulkUpdateLineItem();
                template.modifyToDoItem(toDoItem);
                return toDoItemExportImportService.newLineItem(template);
            }
        };
    }
    //endregion

    //region > import (action)
    @Action
    @ActionLayout(
            named = "Import"
    )
    @MemberOrder(name="toDoItems", sequence="2")
    public List<ExcelModuleDemoToDoItemBulkUpdateLineItem> importBlob(
            @ParameterLayout(named="Excel spreadsheet") final Blob spreadsheet) {
        final List<ExcelModuleDemoToDoItemBulkUpdateLineItem> lineItems =
                excelService.fromExcel(spreadsheet, WORKSHEET_SPEC);
        container.informUser(lineItems.size() + " items imported");
        return lineItems;
    }
    //endregion


    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private ExcelModuleDemoToDoItemBulkUpdateMenu toDoItemExportImportService;

    //endregion

}
