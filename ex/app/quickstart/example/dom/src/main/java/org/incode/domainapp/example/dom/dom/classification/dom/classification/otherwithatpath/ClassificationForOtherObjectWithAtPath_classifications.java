package org.incode.domainapp.example.dom.dom.classification.dom.classification.otherwithatpath;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.otherwithatpath.OtherObjectWithAtPath;
import org.incode.module.classification.dom.impl.classification.T_classifications;

@Mixin
public class ClassificationForOtherObjectWithAtPath_classifications extends T_classifications<OtherObjectWithAtPath> {
    public ClassificationForOtherObjectWithAtPath_classifications(final OtherObjectWithAtPath classified) {
        super(classified);
    }
}
