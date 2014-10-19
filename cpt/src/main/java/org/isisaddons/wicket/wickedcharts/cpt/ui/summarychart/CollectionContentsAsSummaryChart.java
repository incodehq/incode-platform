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
package org.isisaddons.wicket.wickedcharts.cpt.ui.summarychart;

import java.math.BigDecimal;
import java.util.List;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;
import org.isisaddons.wicket.wickedcharts.cpt.ui.scalarchart.StandaloneValueAsWickedChart;
import com.google.common.collect.Lists;
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
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.model.models.ValueModel;
import org.apache.isis.viewer.wicket.ui.components.collectioncontents.summary.CollectionContentsAsSummary;
import org.apache.isis.viewer.wicket.ui.components.collectioncontents.summary.CollectionContentsAsSummary.Summary;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;
import org.apache.isis.viewer.wicket.ui.panels.PanelUtil;

/**
 * {@link PanelAbstract Panel} that represents a {@link EntityCollectionModel
 * collection of entity}s rendered using as a table of summary values with a
 * chart alongside.
 */
public class CollectionContentsAsSummaryChart extends PanelAbstract<EntityCollectionModel> {

    private static final String ID_CHART = "chart";

    private static final long serialVersionUID = 1L;

    private static final String ID_FEEDBACK = "feedback";

    public CollectionContentsAsSummaryChart(final String id, final EntityCollectionModel model) {
        super(id, model);

        buildGui();
    }

    private void buildGui() {
        final EntityCollectionModel model = getModel();

        final ObjectSpecification elementSpec = model.getTypeOfSpecification();

        final FeedbackPanel feedback = new FeedbackPanel(ID_FEEDBACK);
        feedback.setOutputMarkupId(true);
        addOrReplace(feedback);

        final List<ObjectAdapter> adapters = model.getObject();
        final List<String> titles = Lists.newArrayList();
        for (ObjectAdapter adapter: adapters) {
            titles.add(adapter.titleString(null));
        }

        final List<ObjectAssociation> numberAssociations = elementSpec.getAssociations(CollectionContentsAsSummaryChartFactory.OF_TYPE_BIGDECIMAL);
        final List<CollectionContentsAsSummary.Summary> summaries = Lists.newArrayList();
        for (final ObjectAssociation numberAssociation : numberAssociations) {

            final String propertyName = numberAssociation.getName();
            final CollectionContentsAsSummary.Summary summary = new CollectionContentsAsSummary.Summary(propertyName, adapters, numberAssociation);

            summaries.add(summary);
        }

        if(model.isStandalone()) {
            final WickedChart chartValue = createChartValue(titles, summaries);
            final StandaloneValueAsWickedChart wickedChart = new StandaloneValueAsWickedChart(ID_CHART, asValueModel(chartValue));
            addOrReplace(wickedChart);
        } else {
            add(new Label(ID_CHART, ""));
        }
    }

    private ValueModel asValueModel(WickedChart chartValue) {
        return new ValueModel(getAdapterManager().adapterFor(chartValue));
    }

    private WickedChart createChartValue(List<String> titles, List<Summary> summaries) {

        BigDecimal min = null;
        BigDecimal max = null;
        for (Summary summary : summaries) {
            final BigDecimal summaryMin = summary.getMin();
            min = minOf(min, summaryMin);
            
            final BigDecimal summaryMax = summary.getMax();
            max = maxOf(max, summaryMax);
        }
        min = minOf(min, BigDecimal.ZERO);
        max = maxOf(max, BigDecimal.ZERO);
        
        Options options = new Options();
        options.setChartOptions(new ChartOptions().setType(SeriesType.COLUMN));
        
        options.setTitle(new Title("Summary"));
        
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

        for (Summary summary : summaries) {
            String propertyName = summary.getPropertyName();
            Series<Number> setData = new SimpleSeries().setName(propertyName).setData(summary.getValuesAsNumbers());
            options.addSeries(setData);
        }
        
        return new WickedChart(options);
    }

    private BigDecimal maxOf(BigDecimal max, final BigDecimal summaryMax) {
        return max != null
            ? summaryMax != null
                ? max.max(summaryMax)
                : max
            : null;
    }

    private BigDecimal minOf(BigDecimal min, final BigDecimal summaryMin) {
        return min != null
            ? summaryMin != null
                ? min.min(summaryMin)
                : min
            : null;
    }

    @Override
    protected void onModelChanged() {
        buildGui();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        PanelUtil.renderHead(response, CollectionContentsAsSummaryChart.class);
    }
}
