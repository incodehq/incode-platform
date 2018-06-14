package org.incode.example.document.dom.contracttests.with;

import org.incode.module.base.dom.with.WithDescriptionUnique;
import org.incode.module.base.dom.with.WithFieldUniqueContractTestAllAbstract;

public class WithDescriptionUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation extends
        WithFieldUniqueContractTestAllAbstract<WithDescriptionUnique> {

    public WithDescriptionUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation() {
        super("org.incode.example.document", "description", WithDescriptionUnique.class);
    }

}
