package org.incode.example.country.dom.impl;

import org.junit.Test;

import org.incode.example.country.dom.impl.Country;
import org.incode.module.unittestsupport.dom.bean.AbstractBeanPropertiesTest;

public class CountryTest {

    public static class BeanProperties extends AbstractBeanPropertiesTest {

        @Test
        public void test() {
            newPojoTester()
                    .exercise(new Country());
        }

    }
}