package org.isisaddons.module.security.dom.contracttests.with;

import org.incode.module.base.dom.with.WithFieldUniqueContractTestAllAbstract;
import org.incode.module.base.dom.with.WithReferenceUnique;

public class WithReferenceUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation extends
        WithFieldUniqueContractTestAllAbstract<WithReferenceUnique> {

    public WithReferenceUniqueContractForIncodeModuleTest_hasJdoUniqueIndexAnnotation() {
        super("org.isisaddons.module.security", "reference", WithReferenceUnique.class);
    }

}
