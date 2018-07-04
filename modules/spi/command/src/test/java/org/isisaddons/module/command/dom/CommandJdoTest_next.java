package org.isisaddons.module.command.dom;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommandJdoTest_next {

    @Test
    public void test() {
        CommandJdo raj = new CommandJdo();
        assertThat(raj.next("foo"), is(0));
        assertThat(raj.next("foo"), is(1));
        assertThat(raj.next("bar"), is(0));
        assertThat(raj.next("bar"), is(1));
        assertThat(raj.next("foo"), is(2));
        assertThat(raj.next("bar"), is(2));
        assertThat(raj.next("bar"), is(3));
    }

}
