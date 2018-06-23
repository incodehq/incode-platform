package org.incode.example.docfragment.integtests.docfragment;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class DocFragmentModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new DocFragmentModuleIntegTestModule();
    }

    protected DocFragmentModuleIntegTestAbstract() {
        super(module());
    }

}
