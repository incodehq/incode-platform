package domainapp.modules.exampledom.lib.excel.fixture.data;

import java.math.BigDecimal;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.excel.dom.ExcelFixture2;
import org.isisaddons.module.excel.dom.ExcelMetaDataEnabled;
import org.isisaddons.module.excel.dom.FixtureAwareRowHandler;
import domainapp.modules.exampledom.lib.excel.dom.demo.ExcelModuleDemoToDoItem;

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
        final ExcelModuleDemoToDoItemRowHandler2 previous = previousRow;
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
