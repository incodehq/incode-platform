package org.isisaddons.wicket.wickedcharts.cpt.ui.scalarchart;

import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.ValueModel;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;

import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;

public class StandaloneValueAsWickedChart extends PanelAbstract<ValueModel> {

    private static final long serialVersionUID = 1L;

    public StandaloneValueAsWickedChart(final String id, final ValueModel valueModel) {
        super(id, valueModel);

        buildGui();
    }

    private void buildGui() {

        final ValueModel model = getModel();
        final ObjectAdapter chartAdapter = model.getObject();
        final Object chartObj = chartAdapter.getObject();
        WickedChart chart = (WickedChart) chartObj;
        Options options = chart.getOptions();
        
        addOrReplace(new Chart("chart", options));
    }
    
    @Override
    protected void onModelChanged() {
        buildGui();
    }
}
