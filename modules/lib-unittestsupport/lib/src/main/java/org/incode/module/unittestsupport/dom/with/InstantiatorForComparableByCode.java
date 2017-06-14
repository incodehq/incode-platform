package org.incode.module.unittestsupport.dom.with;

import org.apache.isis.core.unittestsupport.bidir.Instantiator;

import org.incode.module.base.dom.with.WithCodeComparable;

public class InstantiatorForComparableByCode implements Instantiator {

    public final Class<? extends WithCodeComparable<?>> cls;
    private int i;
    
    public InstantiatorForComparableByCode(Class<? extends WithCodeComparable<?>> cls) {
        this.cls = cls;
    }

    @Override
    public Object instantiate() {
        WithCodeComparable<?> newInstance;
        try {
            newInstance = cls.newInstance();
            newInstance.setCode(""+(++i));
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}