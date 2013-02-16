package com.danhaywood.isis.wicket.wickedcharts.applib;

import java.io.Serializable;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Value;

import com.googlecode.wickedcharts.highcharts.options.Options;

@Value(semanticsProviderClass=WickedChartSemanticsProvider.class)
public class WickedChart implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Options options;
    
    public WickedChart(Options options) {
        this.options = options;
    }

    @Programmatic
    public Options getOptions() {
        return options;
    }
}
