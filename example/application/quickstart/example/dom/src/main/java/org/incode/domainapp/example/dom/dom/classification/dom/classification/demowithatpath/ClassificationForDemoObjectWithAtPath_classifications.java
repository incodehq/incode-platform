package org.incode.domainapp.example.dom.dom.classification.dom.classification.demowithatpath;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.module.classification.dom.impl.classification.T_classifications;

@Mixin
public class ClassificationForDemoObjectWithAtPath_classifications
        extends T_classifications<DemoObjectWithAtPath> {
    public ClassificationForDemoObjectWithAtPath_classifications(final DemoObjectWithAtPath classified) {
        super(classified);
    }
}
