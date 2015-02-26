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
import javax.annotation.PostConstruct;
import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.value.Blob;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class ExcelModuleDemoToDoItemBulkUpdateContributions {

    @PostConstruct
    public void init() {
        if(excelService == null) {
            throw new IllegalStateException("Require ExcelService to be configured");
        }
    }

    // //////////////////////////////////////


    /**
     * Bulk actions of this type are not yet supported, hence have hidden...
     * 
     * @see https://issues.apache.org/jira/browse/ISIS-705.
     */
    @Action(
            hidden = Where.EVERYWHERE, // ISIS-705
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Blob export(final ExcelModuleDemoToDoItem toDoItem) {
        if(actionInvocationContext.isLast()) {
            final List toDoItems = actionInvocationContext.getDomainObjects();
            return excelService.toExcel(toDoItems, ExcelModuleDemoToDoItem.class, "toDoItems.xlsx");
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
    private ActionInvocationContext actionInvocationContext;

}
