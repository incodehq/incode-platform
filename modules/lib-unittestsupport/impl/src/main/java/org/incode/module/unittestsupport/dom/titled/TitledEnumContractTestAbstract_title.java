package org.incode.module.unittestsupport.dom.titled;

import java.util.Set;

import org.junit.Test;
import org.reflections.Reflections;

import org.incode.module.base.dom.TitledEnum;

/**
 * Automatically tests all enums implementing {@link TitledEnum}.
 */
public abstract class TitledEnumContractTestAbstract_title {

    private final String prefix;

    protected TitledEnumContractTestAbstract_title(final String prefix) {
        this.prefix = prefix;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void searchAndTest() {
        Reflections reflections = new Reflections(prefix);
        
        Set<Class<? extends TitledEnum>> subtypes = 
                reflections.getSubTypesOf(TitledEnum.class);
        for (Class<? extends TitledEnum> subtype : subtypes) {
            if(!Enum.class.isAssignableFrom(subtype)) {
                continue; // ignore non-enums
            }
            Class<? extends Enum> enumType = (Class<? extends Enum>) subtype;
            new TitledEnumContractTester(enumType).test();
        }
    }

}
