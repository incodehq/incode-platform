package org.isisaddons.module.excel.fixture.viewmodels;

import java.math.BigDecimal;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.excel.dom.AggregationType;
import org.isisaddons.module.excel.dom.PivotColumn;
import org.isisaddons.module.excel.dom.PivotRow;
import org.isisaddons.module.excel.dom.PivotValue;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;

import lombok.Getter;
import lombok.Setter;

@DomainObject(nature = Nature.VIEW_MODEL)
public class ExcelModuleDemoPivot {

    public ExcelModuleDemoPivot(
            final ExcelModuleDemoToDoItem.Category category,
            final ExcelModuleDemoToDoItem.Subcategory subcategory,
            final BigDecimal cost){
        this.category = category;
        this.subcategory = subcategory;
        this.cost = cost;
    }

    @Getter @Setter
    @PivotRow
    private ExcelModuleDemoToDoItem.Subcategory subcategory;

    @Getter @Setter
    @PivotColumn(order = 1)
    private ExcelModuleDemoToDoItem.Category category;

    @Getter @Setter
    @PivotValue(order = 1, type = AggregationType.SUM)
    private BigDecimal cost;

}
