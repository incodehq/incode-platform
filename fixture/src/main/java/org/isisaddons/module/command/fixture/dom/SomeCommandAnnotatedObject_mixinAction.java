package org.isisaddons.module.command.fixture.dom;

import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

@Mixin
public class SomeCommandAnnotatedObject_mixinAction {

    private final SomeCommandAnnotatedObject someCommandAnnotatedObject;

    public SomeCommandAnnotatedObject_mixinAction(SomeCommandAnnotatedObject someCommandAnnotatedObject) {
        this.someCommandAnnotatedObject = someCommandAnnotatedObject;
    }

    @Action()
    public SomeCommandAnnotatedObject $$(final String dummy) {
        return someCommandAnnotatedObject;
    }

    public Set<String> choices0$$() {
        return Sets.newTreeSet(
                Lists.newArrayList(someCommandAnnotatedObject.getName(),  "abc","def","ghi"));
    }
    
    public String default0$$() {
        return someCommandAnnotatedObject.getName();
    }

}
