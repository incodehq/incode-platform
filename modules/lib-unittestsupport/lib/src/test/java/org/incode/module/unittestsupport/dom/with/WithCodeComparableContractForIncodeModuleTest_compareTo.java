package org.incode.module.unittestsupport.dom.with;

import com.google.common.collect.ImmutableMap;

/**
 * Automatically tests all domain objects implementing {@link WithCodeComparable}.
 */
public class WithCodeComparableContractForIncodeModuleTest_compareTo extends
        ComparableByCodeContractTestAbstract_compareTo {

    public WithCodeComparableContractForIncodeModuleTest_compareTo() {
        super("org.incode.module", ImmutableMap.<Class<?>,Class<?>>of());
    }

}
