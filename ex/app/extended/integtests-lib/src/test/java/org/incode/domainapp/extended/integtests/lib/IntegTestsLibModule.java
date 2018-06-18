package org.incode.domainapp.extended.integtests.lib;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.domainapp.extended.integtests.lib.docx.DocxModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.lib.excel.ExcelModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.lib.fakedata.FakeDataModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.lib.poly.PolyModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.lib.stringinterpolator.StringInterpolatorModuleIntegTestAbstract;

@XmlRootElement(name = "module")
public class IntegTestsLibModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                DocxModuleIntegTestAbstract.module(),
                ExcelModuleIntegTestAbstract.module(),
                FakeDataModuleIntegTestAbstract.module(),
                PolyModuleIntegTestAbstract.module(),
                StringInterpolatorModuleIntegTestAbstract.module()
        );
    }
}
