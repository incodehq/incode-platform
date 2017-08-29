package org.incode.domainapp.example.dom.dom.classification.dom.classification.demowithatpath;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.module.classification.dom.impl.classification.T_classify;

@Mixin
public class ClassificationForDemoObjectWithAtPath_classify extends T_classify<DemoObjectWithAtPath> {
    public ClassificationForDemoObjectWithAtPath_classify(final DemoObjectWithAtPath classified) {
        super(classified);
    }
}
