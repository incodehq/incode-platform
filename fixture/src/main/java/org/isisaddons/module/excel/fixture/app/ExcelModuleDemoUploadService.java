package org.isisaddons.module.excel.fixture.app;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelFixture;
import org.isisaddons.module.excel.fixture.scripts.ExcelModuleDemoToDoItemRowHandler;
import org.isisaddons.module.excel.fixture.viewodels.ExcelModuleDemoProfessionalImp;

@DomainService(nature = NatureOfService.VIEW)
public class ExcelModuleDemoUploadService {

    public List<FixtureResult> uploadSpreadsheet(
            @ParameterLayout(named = "file")
            final Blob file,
            @ParameterLayout(named = "parameters")
            @Parameter(optionality = Optionality.OPTIONAL)
            final String parameters){
        FixtureScript script = new ExcelFixture(
                file,
                ExcelModuleDemoToDoItemRowHandler.class,
                ExcelModuleDemoProfessionalImp.class);
        return fixtureScripts.runFixtureScript(script, parameters);
    }

    @Inject
    private FixtureScripts fixtureScripts;
}
