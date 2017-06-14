package org.incode.module.unittestsupport.dom.with;

import com.google.common.collect.ImmutableMap;

/**
 * Automatically tests all domain objects implementing
 * {@link WithNameComparable}.
 */
public class WithNameComparableContractForIncodeModuleTest_compareTo extends
        ComparableByNameContractTestAbstract_compareTo {

    public WithNameComparableContractForIncodeModuleTest_compareTo() {
        super("org.incode.module", ImmutableMap.<Class<?>, Class<?>>of());
    }

}
