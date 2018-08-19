package org.isisaddons.wicket.wickedcharts.fixture.demoapp.todomodule.dom;

import java.util.Comparator;

import com.google.common.collect.Ordering;

public class DependenciesComparator implements Comparator<WickedChartsDemoToDoItem> {
    @Override
    public int compare(final WickedChartsDemoToDoItem p, final WickedChartsDemoToDoItem q) {
        final Ordering<WickedChartsDemoToDoItem> byDescription = new Ordering<WickedChartsDemoToDoItem>() {
            public int compare(final WickedChartsDemoToDoItem p, final WickedChartsDemoToDoItem q) {
                return Ordering.natural().nullsFirst().onResultOf(WickedChartsDemoToDoItem::getDescription)
                        .compare(p, q);
            }
        };
        return byDescription
                .compound(Ordering.<WickedChartsDemoToDoItem>natural())
                .compare(p, q);
    }
}
