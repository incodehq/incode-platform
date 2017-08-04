package org.incode.domainapp.example.dom.dom.classification.dom.classification.demowithatpath;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.module.classification.dom.impl.classification.T_unclassify;

@Mixin
public class ClassificationForDemoObjectWithAtPath_unclassify extends T_unclassify<DemoObjectWithAtPath> {
    public ClassificationForDemoObjectWithAtPath_unclassify(final DemoObjectWithAtPath classified) {
        super(classified);
    }
}
