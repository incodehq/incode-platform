package org.incode.module.unittestsupport.dom.with;

import com.google.common.collect.ImmutableMap;

/**
 * Automatically tests all domain objects implementing {@link WithReferenceComparable}.
 */
public class WithReferenceComparableContractForIncodeModuleTest_compareTo extends
        ComparableByReferenceContractTestAbstract_compareTo {

    public WithReferenceComparableContractForIncodeModuleTest_compareTo() {
        super("org.incode.module", ImmutableMap.<Class<?>, Class<?>>of());
    }

}
