package org.isisaddons.module.excel.dom;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public interface ExcelFixtureRowHandler {
    List<Object> handleRow(
            final FixtureScript.ExecutionContext executionContext,
            final ExcelFixture excelFixture,
            final Object previousRow);
}
