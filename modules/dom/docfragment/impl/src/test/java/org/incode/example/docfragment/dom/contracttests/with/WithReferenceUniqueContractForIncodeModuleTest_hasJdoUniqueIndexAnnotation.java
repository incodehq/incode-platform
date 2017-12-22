package org.incode.example.docfragment.dom.contracttests.with;

import org.incode.module.base.dom.with.WithFieldUniqueContractTestAllAbstract;
import org.incode.module.base.dom.with.WithReferenceUnique;

public class WithReferenceUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation extends
        WithFieldUniqueContractTestAllAbstract<WithReferenceUnique> {

    public WithReferenceUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation() {
        super("org.incode.example.docfragment", "reference", WithReferenceUnique.class);
    }

}
