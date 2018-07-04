package org.incode.module.base.dom;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Where;

import org.incode.module.base.dom.with.WithInterval;
import org.incode.module.base.dom.with.WithIntervalContiguous;

public interface Chained<T extends Chained<T>> {

    
    /**
     * The object (usually an {@link WithInterval}, but not necessarily) that precedes this one, if any (not
     * necessarily contiguously)..
     * 
     * <p>
     * Implementations where successive intervals are contiguous should instead implement 
     * {@link WithIntervalContiguous}.
     */
    @Hidden(where=Where.ALL_TABLES)
    @Disabled
    @Optional
    public T getPrevious();

    /**
     * The object (usually an {@link WithInterval}, but not necessarily) that succeeds this one, if any (not 
     * necessarily contiguously).
     * 
     * <p>
     * Implementations where successive intervals are contiguous should instead implement 
     * {@link WithIntervalContiguous}.
     */
    @Hidden(where=Where.ALL_TABLES)
    @Disabled
    @Optional
    public T getNext();
    
    
}
