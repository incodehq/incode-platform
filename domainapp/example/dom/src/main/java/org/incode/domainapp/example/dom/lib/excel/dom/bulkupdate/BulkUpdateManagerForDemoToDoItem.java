package org.incode.domainapp.example.dom.lib.excel.dom.bulkupdate;

import java.util.List;

import javax.annotation.Nullable;

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
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.module.excel.dom.WorksheetContent;
import org.isisaddons.module.excel.dom.WorksheetSpec;

import org.incode.domainapp.example.dom.demo.todo.DemoToDoItem;
import org.incode.domainapp.example.dom.demo.todo.Category;
import org.incode.domainapp.example.dom.demo.todo.Subcategory;

import lombok.Getter;
import lombok.Setter;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "exampleLibExcel.BulkUpdateManagerForDemoToDoItem"
)
@DomainObjectLayout(
        named ="Import/export manager",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class BulkUpdateManagerForDemoToDoItem {


    public static final WorksheetSpec WORKSHEET_SPEC =
            new WorksheetSpec(BulkUpdateLineItemForDemoToDoItem.class, "line-items");


    public String title() {
        return "Import/export manager";
    }
    


    @Getter @Setter
    private String fileName;

    //region > changeFileName (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    public BulkUpdateManagerForDemoToDoItem changeFileName(final String fileName) {
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
    public BulkUpdateManagerForDemoToDoItem select(
            final Category category,
            @Nullable
            final Subcategory subcategory,
            @ParameterLayout(named="Completed?")
            final boolean completed) {
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
    public List<DemoToDoItem> getToDoItems() {
        return container.allMatches(DemoToDoItem.class,
                Predicates.and(
                    DemoToDoItem.Predicates.thoseOwnedBy(currentUserName()),
                    DemoToDoItem.Predicates.thoseCompleted(isComplete()),
                    DemoToDoItem.Predicates.thoseCategorised(getCategory(), getSubcategory())));
    }

    //endregion


    //region > export (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    public Blob export() {
        final String fileName = withExtension(getFileName(), ".xlsx");
        final List<DemoToDoItem> items = getToDoItems();
        return toExcel(fileName, items);
    }

    public String disableExport() {
        return getFileName() == null? "file name is required": null;
    }

    private static String withExtension(final String fileName, final String fileExtension) {
        return fileName.endsWith(fileExtension) ? fileName : fileName + fileExtension;
    }

    private Blob toExcel(final String fileName, final List<DemoToDoItem> items) {
        final List<BulkUpdateLineItemForDemoToDoItem> toDoItemViewModels = Lists.transform(items, toLineItem());
        return excelService.toExcel(new WorksheetContent(toDoItemViewModels, WORKSHEET_SPEC), fileName);
    }

    private Function<DemoToDoItem, BulkUpdateLineItemForDemoToDoItem> toLineItem() {
        return new Function<DemoToDoItem, BulkUpdateLineItemForDemoToDoItem>(){
            @Override
            public BulkUpdateLineItemForDemoToDoItem apply(final DemoToDoItem toDoItem) {
                final BulkUpdateLineItemForDemoToDoItem template = new BulkUpdateLineItemForDemoToDoItem();
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
    public List<BulkUpdateLineItemForDemoToDoItem> importBlob(
            @Parameter(fileAccept = ".xlsx")
            @ParameterLayout(named="Excel spreadsheet")
            final Blob spreadsheet) {
        final List<BulkUpdateLineItemForDemoToDoItem> lineItems =
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
    private BulkUpdateMenuForDemoToDoItem toDoItemExportImportService;

    //endregion

}
