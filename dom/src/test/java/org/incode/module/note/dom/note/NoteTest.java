package org.incode.module.note.dom.note;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteTest {

    public static class TrimTest extends NoteTest {

        @Test
        public void whenNull() throws Exception {
            assertThat(Note.trim(null, "...", 20)).isNull();
        }
        @Test
        public void whenShort() throws Exception {
            assertThat(Note.trim("abc", "...", 4)).isEqualTo("abc");
        }
        @Test
        public void whenExact() throws Exception {
            assertThat(Note.trim("abcdef", "...", 6)).isEqualTo("abcdef");
        }
        @Test
        public void whenTooLong() throws Exception {
            assertThat(Note.trim("abcdef", "...", 5)).isEqualTo("ab...");
        }

    }

}