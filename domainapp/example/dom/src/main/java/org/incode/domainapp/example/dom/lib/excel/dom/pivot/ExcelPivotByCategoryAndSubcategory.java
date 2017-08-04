package org.incode.domainapp.example.dom.lib.excel.dom.pivot;

import java.math.BigDecimal;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.excel.dom.AggregationType;
import org.isisaddons.module.excel.dom.PivotColumn;
import org.isisaddons.module.excel.dom.PivotRow;
import org.isisaddons.module.excel.dom.PivotValue;

import org.incode.domainapp.example.dom.demo.dom.todo.Category;
import org.incode.domainapp.example.dom.demo.dom.todo.Subcategory;

import lombok.Getter;
import lombok.Setter;

@DomainObject(nature = Nature.VIEW_MODEL)
public class ExcelPivotByCategoryAndSubcategory {

    public ExcelPivotByCategoryAndSubcategory(
            final Category category,
            final Subcategory subcategory,
            final BigDecimal cost){
        this.category = category;
        this.subcategory = subcategory;
        this.cost = cost;
    }

    @Getter @Setter
    @PivotRow
    private Subcategory subcategory;

    @Getter @Setter
    @PivotColumn(order = 1)
    private Category category;

    @Getter @Setter
    @PivotValue(order = 1, type = AggregationType.SUM)
    private BigDecimal cost;

}
