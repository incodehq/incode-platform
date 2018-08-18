package org.isisaddons.module.docx.integtests;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class DocxModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new DocxModuleIntegTestModule();
    }

    protected DocxModuleIntegTestAbstract() {
        super(module());
    }

}
