package org.incode.domainapp.example.dom.demo.fixture.todoitems;

import java.util.List;

import com.google.common.io.Resources;

import org.isisaddons.module.excel.dom.ExcelFixture2;
import org.isisaddons.module.excel.dom.WorksheetSpec;
import org.isisaddons.module.excel.dom.util.Mode;

import lombok.Getter;
import lombok.Setter;

public class DemoFixture_extending_ExcelFixture2 extends ExcelFixture2 {

    public DemoFixture_extending_ExcelFixture2(){
        this.resourceName = "ToDoItems.xlsx";
    }

    @Getter @Setter
    private String resourceName;

    @Override
    protected void execute(final ExecutionContext executionContext) {

        setExcelResource(Resources.getResource(getClass(), getResourceName()));

        setMatcher(new WorksheetSpec.Matcher() {
            @Override public WorksheetSpec fromSheet(final String sheetName) {

                if (sheetName.startsWith("Sheet")) {
                    return new WorksheetSpec(
                            DemoFixture_extending_ExcelFixture2.this.rowFactoryFor(DemoToDoItemRowHandler2.class, executionContext),
                            sheetName,
                            Mode.RELAXED);
                } else
                    return null;
            }
        });

        setSequencer(new WorksheetSpec.Sequencer() {
            @Override
            public List<WorksheetSpec> sequence(final List<WorksheetSpec> specs) {
                return specs;
            }
        });

        super.execute(executionContext);
    }

}
