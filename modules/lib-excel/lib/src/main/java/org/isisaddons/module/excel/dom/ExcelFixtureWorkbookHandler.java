package org.isisaddons.module.excel.dom;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public interface ExcelFixtureWorkbookHandler {
    void workbookHandled(
            final FixtureScript.ExecutionContext executionContext,
            final ExcelFixture2 excelFixture,
            List<List<?>> rows);
}
