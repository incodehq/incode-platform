package org.incode.domainapp.example.dom.dom.classification.dom.classification.otherwithatpath;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.otherwithatpath.OtherObjectWithAtPath;
import org.incode.module.classification.dom.impl.classification.T_classify;

@Mixin
public class ClassificationForOtherObjectWithAtPath_classify extends T_classify<OtherObjectWithAtPath> {
    public ClassificationForOtherObjectWithAtPath_classify(final OtherObjectWithAtPath classified) {
        super(classified);
    }
}
