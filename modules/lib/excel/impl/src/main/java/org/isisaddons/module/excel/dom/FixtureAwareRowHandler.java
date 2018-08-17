package org.isisaddons.module.excel.dom;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public interface FixtureAwareRowHandler<T extends FixtureAwareRowHandler<T>> extends RowHandler<T> {

    void setExecutionContext(FixtureScript.ExecutionContext ec);
    void setExcelFixture2(ExcelFixture2 excelFixture2);

}
