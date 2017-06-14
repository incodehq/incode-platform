package org.incode.module.unittestsupport.dom.bean;

import org.jmock.auto.Mock;
import org.junit.Rule;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

public abstract class AbstractBeanPropertiesTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Mock
    protected DomainObjectContainer mockContainer;

    protected PojoTester newPojoTester() {
        final PojoTester pojoTester = PojoTester.relaxed()
			.withFixture(FixtureDatumFactoriesForJoda.dates())
            .withFixture(DomainObjectContainer.class, mockContainer)
            ;
        return pojoTester;
    }

    protected static <T> PojoTester.FixtureDatumFactory<T> pojos(Class<T> compileTimeType) {
        return FixtureDatumFactoriesForAnyPojo.pojos(compileTimeType, compileTimeType);
    }

    protected static <T> PojoTester.FixtureDatumFactory<T> pojos(Class<T> compileTimeType, Class<? extends T> runtimeType) {
        return FixtureDatumFactoriesForAnyPojo.pojos(compileTimeType, runtimeType);
    }

}
