package org.incode.platform.lib.excel.integtests.tests;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.domainapp.example.dom.demo.fixture.setup.todoitems.ExcelModuleDemoToDoItemRowHandler2;
import org.incode.domainapp.example.dom.demo.fixture.setup.todoitems.ExcelModuleDemoExtendingExcelFixture2;
import org.incode.platform.lib.excel.integtests.ExcelModuleModuleIntegTestAbstract;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExcelModuleDemoMetaDataEnabled_IntegTest extends ExcelModuleModuleIntegTestAbstract {

    List<FixtureResult> fixtureResults;

    @Before
    public void setUpData() throws Exception {
        FixtureScript script = new FixtureScript() {
            @Override
            protected void execute(final FixtureScript.ExecutionContext executionContext) {
                executionContext.executeChild(this, new ExcelModuleDemoExtendingExcelFixture2());
                fixtureResults = executionContext.getResults();
            }
        };
        fixtureScripts.runFixtureScript(script, "");
    }


    @Test
    public void testResults() throws Exception{

        assertThat(fixtureResults.size(), is(8));

        List<ExcelModuleDemoToDoItemRowHandler2> resultToTest = new ArrayList<>();
        for (FixtureResult fr : fixtureResults){
            resultToTest.add((ExcelModuleDemoToDoItemRowHandler2) fr.getObject());
        }

        assertThat(resultToTest.get(0).getExcelRowNumber(), is(1));
        assertThat(resultToTest.get(0).getExcelSheetName(), is("Sheet2"));

        assertThat(resultToTest.get(6).getExcelRowNumber(), is(7));
        assertThat(resultToTest.get(6).getExcelSheetName(), is("Sheet2"));

        assertThat(resultToTest.get(7).getExcelRowNumber(), is(3));
        assertThat(resultToTest.get(7).getExcelSheetName(), is("Sheet3"));
        assertThat(resultToTest.get(7).getDescription(), is("Another Item"));

    }

    @Inject
    protected FixtureScripts fixtureScripts;




}
