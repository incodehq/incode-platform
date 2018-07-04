package org.isisaddons.module.command.replay.spi;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.core.metamodel.facets.object.domainobject.Util;
import org.apache.isis.schema.cmd.v1.CommandDto;

public abstract class CommandReplayAnalyserAbstract implements CommandReplayAnalyser {

    private final String key;
    private final String defaultValue;

    public CommandReplayAnalyserAbstract(final String key) {
        this(key, "enabled");
    }

    public CommandReplayAnalyserAbstract(final String key, final String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    private boolean enabled;

    @PostConstruct
    public void init(final Map<String,String> properties) {
        final String anslysisStr = getPropertyElse(properties, key, defaultValue);
        enabled = Util.parseYes(anslysisStr);
    }


    @Programmatic
    public final String analyzeReplay(final Command command, final CommandDto dto) {

        if(!enabled) {
            return null;
        }
        return doAnalyzeReplay(command, dto);

    }

    protected abstract String doAnalyzeReplay(final Command command, final CommandDto dto);

    private static String getPropertyElse(final Map<String, String> properties, final String key, final String dflt) {
        final String str = properties.get(key);
        return str != null ? str : dflt;
    }


}
