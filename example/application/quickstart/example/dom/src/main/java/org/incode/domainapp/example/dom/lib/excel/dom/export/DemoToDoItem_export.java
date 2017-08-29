package org.incode.domainapp.example.dom.lib.excel.dom.export;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;

import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItem;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItemMenu;

@Mixin(method = "act")
public class DemoToDoItem_export {

    private final DemoToDoItem toDoItem;

    public DemoToDoItem_export(final DemoToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    @Action(
            invokeOn = InvokeOn.OBJECT_ONLY // ISIS-705 ... bulk actions returning Blobs are not yet supported
    )
    @ActionLayout(contributed = Contributed.AS_ACTION)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Blob act() {
        if(actionInvocationContext.isLast()) {
            // ie current object only
            final List toDoItems = actionInvocationContext.getDomainObjects();
            return excelService.toExcel(toDoItems, DemoToDoItem.class, DemoToDoItem.class.getSimpleName(), "toDoItems.xlsx");
        } else {
            return null;
        }
    }

    

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private DemoToDoItemMenu excelModuleDemoToDoItems;

    @javax.inject.Inject
    private ActionInvocationContext actionInvocationContext;

}
