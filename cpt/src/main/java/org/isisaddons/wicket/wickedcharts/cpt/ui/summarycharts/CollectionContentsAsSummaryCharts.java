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
package org.isisaddons.wicket.wickedcharts.cpt.ui.summarycharts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;
import org.isisaddons.wicket.wickedcharts.cpt.ui.scalarchart.StandaloneValueAsWickedChart;
import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.Function;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.LegendLayout;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.VerticalAlignment;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.series.Series;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.model.models.ValueModel;
import org.apache.isis.viewer.wicket.ui.components.collectioncontents.summary.CollectionContentsAsSummary;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;

/**
 * {@link PanelAbstract Panel} that represents a {@link EntityCollectionModel
 * collection of entity}s rendered using as a table of summary values with a
 * chart alongside.
 */
public class CollectionContentsAsSummaryCharts extends PanelAbstract<EntityCollectionModel> {

    private static final String ID_PROPERTY_NAME = "propertyName";

    private static final String ID_CHART = "chart";

    private static final String ID_MAX = "max";

    private static final String ID_MIN = "min";

    private static final String ID_AVG = "avg";

    private static final String ID_SUM = "sum";

    private static final String ID_REPEATING_SUMMARY_CHARTS = "repeatingSummaryCharts";

    private static final long serialVersionUID = 1L;

    private static final String ID_FEEDBACK = "feedback";

    public CollectionContentsAsSummaryCharts(final String id, final EntityCollectionModel model) {
        super(id, model);

        buildGui();
    }

    private void buildGui() {

        final EntityCollectionModel model = getModel();

        final ObjectSpecification elementSpec = model.getTypeOfSpecification();

        final FeedbackPanel feedback = new FeedbackPanel(ID_FEEDBACK);
        feedback.setOutputMarkupId(true);
        addOrReplace(feedback);

        RepeatingView repeating = new RepeatingView(ID_REPEATING_SUMMARY_CHARTS);
        addOrReplace(repeating);

        List<ObjectAssociation> numberAssociations = elementSpec.getAssociations(CollectionContentsAsSummaryChartsFactory.OF_TYPE_BIGDECIMAL);
        for (ObjectAssociation numberAssociation : numberAssociations) {
            AbstractItem item = new AbstractItem(repeating.newChildId());

            repeating.add(item);

            String propertyName = numberAssociation.getName();
            item.add(new Label(ID_PROPERTY_NAME, new Model<String>(propertyName)));

            List<ObjectAdapter> adapters = model.getObject();
            
            CollectionContentsAsSummary.Summary summary = new CollectionContentsAsSummary.Summary(propertyName, adapters, numberAssociation);

            BigDecimal min = summary.getMin();
            BigDecimal max = summary.getMax();
            
            addItem(item, ID_SUM, summary.getTotal());
            addItem(item, ID_AVG, summary.getAverage());
            addItem(item, ID_MIN, min);
            addItem(item, ID_MAX, max);

            if(model.isStandalone()) {
                final BigDecimal minElseZero = min!=null?(min.compareTo(BigDecimal.ZERO)<0?min:BigDecimal.ZERO):null;
                final BigDecimal maxElseZero = max!=null?(max.compareTo(BigDecimal.ZERO)<0?BigDecimal.ZERO:max):null;
                final WickedChart chartValue = createChartValue(propertyName, summary.getTitles(), summary.getValuesAsNumbers(), minElseZero, maxElseZero);
                final StandaloneValueAsWickedChart wickedChart = new StandaloneValueAsWickedChart(ID_CHART, asValueModel(chartValue));
                item.add(wickedChart);
            } else {
                item.add(new Label(ID_CHART, ""));
            }
        }
    }

    private ValueModel asValueModel(WickedChart chartValue) {
        return new ValueModel(getAdapterManager().adapterFor(chartValue));
    }

    private WickedChart createChartValue(String title, List<String> titles, List<Number> values, Number min, Number max) {
        Options options = new Options();
        options.setChartOptions(new ChartOptions().setType(SeriesType.COLUMN));
        
        options.setTitle(new Title(title));
        
        options.setxAxis(new Axis().setCategories(titles));
        options.setyAxis(new Axis().setMin(min).setMax(max));
        
        options.setLegend(
                new Legend()
                    .setLayout(LegendLayout.VERTICAL)
                    .setBackgroundColor(new HexColor("#FFFFFF"))
                    .setAlign(HorizontalAlignment.LEFT)
                    .setVerticalAlign(VerticalAlignment.TOP).setX(100).setY(70).setFloating(Boolean.TRUE).setShadow(Boolean.TRUE));
        
        options.setTooltip(
                new Tooltip().setFormatter(new Function().setFunction(" return ''+ this.x +': '+ this.y;")));
        
        options.setPlotOptions(
                new PlotOptionsChoice()
                    .setColumn(new PlotOptions().setPointPadding(0.2f).setBorderWidth(0)));
        
        Series<Number> setData = new SimpleSeries().setName("Value").setData(values);
        options.addSeries(setData);
        
        return new WickedChart(options);
    }

    private void addItem(AbstractItem item, String id, BigDecimal amt) {
        TextField<String> textField = new TextField<String>(id, new Model<String>(format(amt)));
        item.add(textField);
    }

    private String format(BigDecimal amt) {
        return amt != null ? amt.setScale(2, RoundingMode.HALF_UP).toPlainString() : "";
    }

    @Override
    protected void onModelChanged() {
        buildGui();
    }

}
