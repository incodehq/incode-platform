package org.incode.example.country.dom.impl;

import org.junit.Test;

import org.incode.example.country.dom.impl.Country;
import org.incode.example.country.dom.impl.State;
import org.incode.module.unittestsupport.dom.bean.AbstractBeanPropertiesTest;

public class StateTest {

    public static class BeanProperties extends AbstractBeanPropertiesTest {

        @Test
        public void test() {
            newPojoTester()
                    .withFixture(pojos(Country.class))
                    .exercise(new State());
        }


    }
}