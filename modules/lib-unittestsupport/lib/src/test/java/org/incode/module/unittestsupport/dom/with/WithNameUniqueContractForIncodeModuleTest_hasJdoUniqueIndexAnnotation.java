package org.incode.module.unittestsupport.dom.with;

import org.incode.module.base.dom.with.WithNameUnique;

public class WithNameUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation extends WithFieldUniqueContractTestAllAbstract<WithNameUnique> {

    public WithNameUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation() {
        super(WithNameUnique.class, "name", "org.incode.module");
    }

}
