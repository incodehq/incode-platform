package org.isisaddons.wicket.wickedcharts.fixture;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.wicket.wickedcharts.fixture.demoapp.todomodule.fixturescripts.WickedChartsDemoToDoItem_tearDown;

@XmlRootElement(name = "module")
public class WickedChartsFixturesModule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new WickedChartsDemoToDoItem_tearDown();
    }
}
