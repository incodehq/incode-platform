package org.isisaddons.wicket.wickedcharts.fixture.demoapp.todomodule.dom;

import java.util.Comparator;

import com.google.common.collect.Ordering;

public class DependenciesComparator implements Comparator<WicketChartsDemoToDoItem> {
    @Override
    public int compare(final WicketChartsDemoToDoItem p, final WicketChartsDemoToDoItem q) {
        final Ordering<WicketChartsDemoToDoItem> byDescription = new Ordering<WicketChartsDemoToDoItem>() {
            public int compare(final WicketChartsDemoToDoItem p, final WicketChartsDemoToDoItem q) {
                return Ordering.natural().nullsFirst().onResultOf(WicketChartsDemoToDoItem::getDescription)
                        .compare(p, q);
            }
        };
        return byDescription
                .compound(Ordering.<WicketChartsDemoToDoItem>natural())
                .compare(p, q);
    }
}
