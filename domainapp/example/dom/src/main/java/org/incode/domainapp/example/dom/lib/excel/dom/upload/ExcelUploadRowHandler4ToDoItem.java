package org.incode.domainapp.example.dom.lib.excel.dom.upload;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.wrapper.WrapperFactory;

import org.isisaddons.module.excel.dom.ExcelFixture;
import org.isisaddons.module.excel.dom.ExcelFixtureRowHandler;

import org.incode.domainapp.example.dom.demo.dom.todo.Category;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItemMenu;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItem;
import org.incode.domainapp.example.dom.demo.dom.todo.Subcategory;

import lombok.Getter;
import lombok.Setter;

@DomainObject(nature = Nature.VIEW_MODEL)
public class ExcelUploadRowHandler4ToDoItem implements ExcelFixtureRowHandler {

    @Getter @Setter
    private String description;

    @Getter @Setter
    private String subCategory;

    @Getter @Setter
    private String ownedBy;

    @Getter @Setter
    private LocalDate dueBy;

    @Getter @Setter
    private BigDecimal cost;

    @Override
    public List<Object> handleRow(final FixtureScript.ExecutionContext executionContext, final ExcelFixture excelFixture, final Object previousRow) {
        //final ExcelModuleDemoToDoItem toDoItem = wrapperFactory.wrap(toDoItems).newToDo(
        final DemoToDoItem toDoItem = toDoItems.newToDoItem(
                description,
                Category.Professional,
                Subcategory.valueOf(subCategory),
                ownedBy,
                dueBy,
                cost);
        executionContext.addResult(excelFixture, toDoItem);
        return Collections.<Object>singletonList(toDoItem);
    }

    @Inject
    DemoToDoItemMenu toDoItems;

    @Inject
    WrapperFactory wrapperFactory;
}
