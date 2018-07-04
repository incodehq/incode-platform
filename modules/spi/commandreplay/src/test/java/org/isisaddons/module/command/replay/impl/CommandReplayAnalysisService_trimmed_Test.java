package org.isisaddons.module.command.replay.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CommandReplayAnalysisService_trimmed_Test {

    @Test
    public void fits() {
        Assertions.assertThat(CommandReplayAnalysisService.trimmed("abcde", 5)).isEqualTo("abcde");
    }

    @Test
    public void needs_to_be_trimmed() {
        Assertions.assertThat(CommandReplayAnalysisService.trimmed("abcde", 4)).isEqualTo("a...");
    }

    @Test
    public void when_null() {
        Assertions.assertThat(CommandReplayAnalysisService.trimmed(null, 4)).isNull();
    }

    @Test
    public void when_empty() {
        Assertions.assertThat(CommandReplayAnalysisService.trimmed("", 4)).isEqualTo("");
    }

}