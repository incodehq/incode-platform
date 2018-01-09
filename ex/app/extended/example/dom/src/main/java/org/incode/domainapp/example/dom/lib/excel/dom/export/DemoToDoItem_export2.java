package org.incode.domainapp.example.dom.lib.excel.dom.export;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.module.excel.dom.WorksheetContent;
import org.isisaddons.module.excel.dom.WorksheetSpec;

import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItem;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItemMenu;

@Mixin(method = "act")
public class DemoToDoItem_export2 {

    private final DemoToDoItem toDoItem;

    public DemoToDoItem_export2(final DemoToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    @Action(
            invokeOn = InvokeOn.OBJECT_ONLY // ISIS-705 ... bulk actions returning Blobs are not yet supported
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Blob act() {
        if(actionInvocationContext.isLast()) {
            // ie current object only
            final List toDoItems = actionInvocationContext.getDomainObjects();
            final List<DemoToDoItem> allItems = this.excelModuleDemoToDoItems.allInstances();
            final List<WorksheetContent> worksheetContents = Lists.newArrayList(
                    new WorksheetContent(toDoItems, new WorksheetSpec(DemoToDoItem.class, "current")),
                    new WorksheetContent(allItems, new WorksheetSpec(DemoToDoItem.class, "all")));
            return excelService.toExcel(worksheetContents, "toDoItems.xlsx");
        } else {
            return null;
        }
    }

    
    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private DemoToDoItemMenu excelModuleDemoToDoItems;

    @javax.inject.Inject
    private ActionInvocationContext actionInvocationContext;

}
