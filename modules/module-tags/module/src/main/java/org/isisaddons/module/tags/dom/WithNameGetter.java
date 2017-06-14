package org.isisaddons.module.tags.dom;

import org.apache.isis.applib.util.ObjectContracts.ToStringEvaluator;

/**
 * Indicates that the implementing class has a {@link #getKey() name}.
 */
interface WithNameGetter {

    public String getKey();

    /**
     * Utility class for obtaining the string value of an object that implements {@link WithNameGetter}.
     */
    public final static class ToString {
        private ToString() {}
        public static ToStringEvaluator evaluator() {
            return new ToStringEvaluator() {
                @Override
                public boolean canEvaluate(final Object o) {
                    return o instanceof WithNameGetter;
                }
                
                @Override
                public String evaluate(final Object o) {
                    return ((WithNameGetter)o).getKey();
                }
            };
        }
    }


}
