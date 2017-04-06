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
package org.isisaddons.module.excel.fixture.scripts;

import java.math.BigDecimal;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.excel.dom.ExcelFixture2;
import org.isisaddons.module.excel.dom.ExcelMetaDataEnabled;
import org.isisaddons.module.excel.dom.FixtureAwareRowHandler;
import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;

import lombok.Getter;
import lombok.Setter;

public class ExcelModuleDemoToDoItemRowHandler2 implements FixtureAwareRowHandler<ExcelModuleDemoToDoItemRowHandler2>, ExcelMetaDataEnabled {

    @Getter @Setter
    private String excelSheetName;

    @Getter @Setter
    private Integer excelRowNumber;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private ExcelModuleDemoToDoItem.Category category;

    @Getter @Setter
    private ExcelModuleDemoToDoItem.Subcategory subcategory;

    @Getter @Setter
    private BigDecimal cost;

    @Override
    public void handleRow(final ExcelModuleDemoToDoItemRowHandler2 previousRow) {
        final ExcelModuleDemoToDoItemRowHandler2 previous = (ExcelModuleDemoToDoItemRowHandler2) previousRow;
        if(category == null) {
            category = previous.category;
        }
        if(subcategory == null) {
            subcategory = previous.subcategory;
        }

        executionContext.addResult(excelFixture2, this);
    }


    /**
     * To allow for usage within fixture scripts also.
     */
    @Setter
    private FixtureScript.ExecutionContext executionContext;

    /**
     * To allow for usage within fixture scripts also.
     */
    @Setter
    private ExcelFixture2 excelFixture2;

}
