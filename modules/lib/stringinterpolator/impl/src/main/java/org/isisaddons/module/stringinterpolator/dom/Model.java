package org.isisaddons.module.stringinterpolator.dom;

import java.util.Map;

/**
 * Acts as the context for the OGNL evaluation.
 */
class Model {

    private Object _this;
    private Map<String, String> _properties;

    public Model(final Object context, final Map<String, String> properties) {
        this._this = context;
        this._properties = properties;
    }

    public Object getThis() {
        return _this;
    }

    public Map<String, String> getProperties() {
        return _properties;
    }
}