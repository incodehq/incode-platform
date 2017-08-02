package org.isisaddons.module.docx.dom.traverse;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;

public class AllMatches<T> extends CallbackAbstract {

    public static <T> List<T> matching(Object docxObject, Predicate<Object> predicate) {
        return new AllMatches<T>(docxObject, predicate).getResult();
    }

    private final Object parent;
    private final Predicate<Object> predicate;

    private final List<T> result = Lists.newArrayList();

    AllMatches(Object parent, Predicate<Object> predicate) {
        this.parent = parent;
        this.predicate = predicate;
    }

    @SuppressWarnings("unchecked")
    public List<Object> apply(Object o) {
        o = XmlUtils.unwrap(o);

        if(predicate.apply(o)) {
            this.result.add((T) o);
            return null;
        }
        return null;
    }

    public List<T> getResult() {
        new TraversalUtil(parent, this);
        return this.result;
    }

}