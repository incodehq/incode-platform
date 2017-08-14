package org.incode.module.unittestsupport.dom.with;

import org.incode.module.base.dom.with.WithTitleUnique;

public class WithTitleUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation extends WithFieldUniqueContractTestAllAbstract<WithTitleUnique> {

    public WithTitleUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation() {
        super(WithTitleUnique.class, "title", "org.incode.module");
    }

}
