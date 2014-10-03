/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.wicket.wickedcharts.fixture.app;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;
import com.google.common.collect.Maps;
import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.HighchartsColor;
import com.googlecode.wickedcharts.highcharts.options.color.NullColor;
import com.googlecode.wickedcharts.highcharts.options.color.RadialGradient;
import com.googlecode.wickedcharts.highcharts.options.functions.PercentageFormatter;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.highcharts.options.series.PointSeries;
import com.googlecode.wickedcharts.highcharts.options.series.Series;
import org.isisaddons.wicket.wickedcharts.fixture.dom.WickedChartsWicketToDoItem;
import org.isisaddons.wicket.wickedcharts.fixture.dom.WickedChartsWicketToDoItem.Category;
import org.isisaddons.wicket.wickedcharts.fixture.dom.WickedChartsWicketToDoItems;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;

@Named("Charts")
@DomainService(menuOrder = "15")
public class WickedChartsWicketToDoItemChartingService {

    @ActionSemantics(Of.SAFE)
    public WickedChart pieChart() {
        
        Map<Category, AtomicInteger> byCategory = Maps.newTreeMap();
        List<WickedChartsWicketToDoItem> allToDos = toDoItems.allToDos();
        for (WickedChartsWicketToDoItem toDoItem : allToDos) {
            Category category = toDoItem.getCategory();
            AtomicInteger integer = byCategory.get(category);
            if(integer == null) {
                integer = new AtomicInteger();
                byCategory.put(category, integer);
            }
            integer.incrementAndGet();
        }
        
        return new WickedChart(new PieWithGradientOptions(byCategory));
    }
    
    public static class PieWithGradientOptions extends Options {
        private static final long serialVersionUID = 1L;

        public PieWithGradientOptions(Map<Category, AtomicInteger> byCategory) {
        
            setChartOptions(new ChartOptions()
                .setPlotBackgroundColor(new NullColor())
                .setPlotBorderWidth(null)
                .setPlotShadow(Boolean.FALSE));
            
            setTitle(new Title("ToDoItems by category"));
        
            PercentageFormatter formatter = new PercentageFormatter();
            setTooltip(
                    new Tooltip()
                        .setFormatter(
                                formatter)
                        .       setPercentageDecimals(1));
        
            setPlotOptions(new PlotOptionsChoice()
                .setPie(new PlotOptions()
                .setAllowPointSelect(Boolean.TRUE)
                .setCursor(Cursor.POINTER)
                .setDataLabels(new DataLabels()
                .setEnabled(Boolean.TRUE)
                .setColor(new HexColor("#000000"))
                .setConnectorColor(new HexColor("#000000"))
                .setFormatter(formatter))));

            Series<Point> series = new PointSeries()
                .setType(SeriesType.PIE);
            int i=0;
            for (Map.Entry<Category, AtomicInteger> entry : byCategory.entrySet()) {
                series
                .addPoint(
                        new Point(entry.getKey().name(), entry.getValue().get()).setColor(
                                new RadialGradient()
                                    .setCx(0.5)
                                    .setCy(0.3)
                                    .setR(0.7)
                                        .addStop(0, new HighchartsColor(i))
                                        .addStop(1, new HighchartsColor(i).brighten(-0.3f))));
                i++;
            }
            addSeries(series);
        }
    }

    
    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private WickedChartsWicketToDoItems toDoItems;

}
