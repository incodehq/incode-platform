package org.incode.module.unittestsupport.dom.with;

import com.google.common.collect.ImmutableMap;

/**
 * Automatically tests all domain objects implementing {@link WithDescriptionComparable}.
 */
public class WithDescriptionComparableContractForIncodeModuleTest_compareTo extends
        ComparableByDescriptionContractTestAbstract_compareTo {

    public WithDescriptionComparableContractForIncodeModuleTest_compareTo() {
        super("org.incode.module", ImmutableMap.<Class<?>,Class<?>>of());
    }

}
