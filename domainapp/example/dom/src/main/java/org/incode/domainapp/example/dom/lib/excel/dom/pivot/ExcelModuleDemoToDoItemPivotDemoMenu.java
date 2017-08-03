package org.incode.domainapp.example.dom.lib.excel.dom.pivot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;

import org.incode.domainapp.example.dom.lib.excel.dom.demo.ExcelModuleDemoToDoItem;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleLibExcel.ExcelModuleDemoToDoItemPivotDemoMenu"
)
public class ExcelModuleDemoToDoItemPivotDemoMenu {

    public ExcelModuleDemoToDoItemPivotDemoMenu() {
    }

    @PostConstruct
    public void init() {
        if(excelService == null) {
            throw new IllegalStateException("Require ExcelService to be configured");
        }
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @MemberOrder(name="ToDos", sequence="90.2")
    public Blob downloadDemoPivotsheet(){
        return excelService.toExcelPivot(vm1list(), ExcelModuleDemoPivot.class, ExcelModuleDemoPivot.class.getSimpleName(), "demo-pivots.xlsx");
    }

    private List<ExcelModuleDemoPivot> vm1list(){
        List<ExcelModuleDemoPivot> result = new ArrayList<>();
        for (ExcelModuleDemoToDoItem todo : getToDoItems()){
            result.add(
                    new ExcelModuleDemoPivot(
                            todo.getCategory(),
                            todo.getSubcategory(),
                            todo.getCost()
                    )
            );
        }
        return result;
    }

    private List<ExcelModuleDemoToDoItem> getToDoItems() {
        return container.allInstances(ExcelModuleDemoToDoItem.class);
    }

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private DomainObjectContainer container;

}
