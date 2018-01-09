package org.incode.domainapp.example.dom.demo.dom.todo;

import java.util.Comparator;

import com.google.common.collect.Ordering;

public class DependenciesComparator implements Comparator<DemoToDoItem> {
    @Override
    public int compare(final DemoToDoItem p, final DemoToDoItem q) {
        final Ordering<DemoToDoItem> byDescription = new Ordering<DemoToDoItem>() {
            public int compare(final DemoToDoItem p, final DemoToDoItem q) {
                return Ordering.natural().nullsFirst().onResultOf(DemoToDoItem::getDescription)
                        .compare(p, q);
            }
        };
        return byDescription
                .compound(Ordering.<DemoToDoItem>natural())
                .compare(p, q);
    }
}
