package org.incode.example.tags.integtests;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class TagsModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new TagsModuleIntegTestModule();
    }

    protected TagsModuleIntegTestAbstract() {
        super(module());
    }

}
