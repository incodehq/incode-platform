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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;
import org.isisaddons.module.excel.fixture.viewmodels.ExcelModuleDemoPivot;

@DomainService
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
        return excelService.toExcelPivot(vm1list(), ExcelModuleDemoPivot.class, null, "demo-pivots.xlsx");
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
