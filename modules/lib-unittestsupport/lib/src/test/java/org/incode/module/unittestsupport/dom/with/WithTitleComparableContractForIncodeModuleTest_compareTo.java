package org.incode.module.unittestsupport.dom.with;

import com.google.common.collect.ImmutableMap;

/**
 * Automatically tests all domain objects implementing {@link WithTitleComparable}.
 */
public class WithTitleComparableContractForIncodeModuleTest_compareTo extends
        ComparableByTitleContractTestAbstract_compareTo {

    public WithTitleComparableContractForIncodeModuleTest_compareTo() {
        super("org.incode.module", ImmutableMap.<Class<?>,Class<?>>of());
    }

}
