package org.isisaddons.module.security.dom.contracttests.with;

import com.google.common.collect.ImmutableMap;

import org.incode.module.base.dom.with.ComparableByNameContractTestAbstract_compareTo;
import org.incode.module.base.dom.with.WithNameComparable;

/**
 * Automatically tests all domain objects implementing
 * {@link WithNameComparable}.
 */
public class WithNameComparableContractForIncodeModuleTest_compareTo extends
        ComparableByNameContractTestAbstract_compareTo {

    public WithNameComparableContractForIncodeModuleTest_compareTo() {
        super("org.isisaddons.module.security", ImmutableMap.<Class<?>, Class<?>>of());
    }

}
