package com.danhaywood.isis.domainservice.docx.traverse;

import java.util.List;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;

import com.google.common.base.Predicate;


public class FirstMatch<T> extends CallbackAbstract {

    public static <T> T matching(Object docxObject, Predicate<Object> predicate) {
        return new FirstMatch<T>(docxObject, predicate).getResult();
    }

    private final Object parent;
    private final Predicate<Object> predicate;

    private T result;

    FirstMatch(Object parent, Predicate<Object> predicate) {
        this.parent = parent;
        this.predicate = predicate;
    }

    @Override
    public boolean shouldTraverse(Object o) {
        return result == null;
    }
    
    @SuppressWarnings("unchecked")
    public List<Object> apply(Object o) {
        o = XmlUtils.unwrap(o);

        if(predicate.apply(o)) {
            this.result = (T) o;
            return null;
        }
        return null;
    }

    public T getResult() {
        new TraversalUtil(parent, this);
        return this.result;
    }

}