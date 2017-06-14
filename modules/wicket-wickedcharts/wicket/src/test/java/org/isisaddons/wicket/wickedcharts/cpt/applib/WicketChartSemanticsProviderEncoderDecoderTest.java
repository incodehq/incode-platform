package org.isisaddons.wicket.wickedcharts.cpt.applib;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.apache.isis.applib.adapters.EncoderDecoder;
import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;
import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChartSemanticsProvider;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.wickedcharts.highcharts.jackson.JsonRenderer;
import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.LegendLayout;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.VerticalAlignment;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;

public class WicketChartSemanticsProviderEncoderDecoderTest {


    private EncoderDecoder<WickedChart> encoderDecoder;

    @Before
    public void setUp() throws Exception {
        encoderDecoder = new WickedChartSemanticsProvider().getEncoderDecoder();
    }


    @Test
    public void roundTrip() throws Exception {
        final WickedChart wickedChartBefore = new WickedChart(createOptions());
        final String encodedString = encoderDecoder.toEncodedString(wickedChartBefore);
        final WickedChart wickedChartAfter = encoderDecoder.fromEncodedString(encodedString);
        
        String jsonBefore = new JsonRenderer().toJson(wickedChartBefore.getOptions());
        String jsonAfter = new JsonRenderer().toJson(wickedChartAfter.getOptions());
        
        assertThat(jsonBefore, is(jsonAfter));
    }

    Options createOptions() {
        Options options = new Options();

        options.setChartOptions(new ChartOptions().setType(SeriesType.LINE));

        options.setTitle(new Title("My very own chart."));

        options.setxAxis(new Axis().setCategories(Arrays.asList(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" })));

        options.setyAxis(new Axis().setTitle(new Title("Temperature (C)")));

        options.setLegend(new Legend().setLayout(LegendLayout.VERTICAL).setAlign(HorizontalAlignment.RIGHT).setVerticalAlign(VerticalAlignment.TOP).setX(-10).setY(100).setBorderWidth(0));

        options.addSeries(new SimpleSeries().setName("Tokyo").setData(Arrays.asList(new Number[] { 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6 })));

        options.addSeries(new SimpleSeries().setName("New York").setData(Arrays.asList(new Number[] { -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5 })));
        return options;
    }

}
