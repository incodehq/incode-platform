package org.isisaddons.module.excel.fixture.demoapp.demomodule.contributions;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.module.excel.dom.WorksheetContent;
import org.isisaddons.module.excel.dom.WorksheetSpec;
import org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItem;
import org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItemMenu;

@Mixin(method = "act")
public class ExcelDemoToDoItem_exportAll {

    private final ExcelDemoToDoItem toDoItem;

    public ExcelDemoToDoItem_exportAll(final ExcelDemoToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Blob act() {
        final List<ExcelDemoToDoItem> allItems = this.excelModuleDemoToDoItems.allInstances();
        final List<WorksheetContent> worksheetContents = Lists.newArrayList(
                new WorksheetContent(allItems, new WorksheetSpec(ExcelDemoToDoItem.class, "all")));
        return excelService.toExcel(worksheetContents, "toDoItems.xlsx");
    }

    
    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private ExcelDemoToDoItemMenu excelModuleDemoToDoItems;


}
