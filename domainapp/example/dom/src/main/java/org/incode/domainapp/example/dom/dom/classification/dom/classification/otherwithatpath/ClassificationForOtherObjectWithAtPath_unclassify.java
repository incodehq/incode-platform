package org.incode.domainapp.example.dom.dom.classification.dom.classification.otherwithatpath;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.otherwithatpath.OtherObjectWithAtPath;
import org.incode.module.classification.dom.impl.classification.T_unclassify;

@Mixin
public class ClassificationForOtherObjectWithAtPath_unclassify extends T_unclassify<OtherObjectWithAtPath> {
    public ClassificationForOtherObjectWithAtPath_unclassify(final OtherObjectWithAtPath classified) {
        super(classified);
    }
}
