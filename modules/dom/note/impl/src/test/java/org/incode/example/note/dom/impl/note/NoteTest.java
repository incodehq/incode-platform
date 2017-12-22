package org.incode.example.note.dom.impl.note;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteTest {

    public static class NotesAbbreviatedTest extends NoteTest {

        Note note;

        @Before
        public void setUp() throws Exception {
            note = new Note();
        }

        @Test
        public void does_not_abbreviate_less_than_40() throws Exception {
            // given
            note.setContent("123456789012345678901234567890123456789"); // 39 chars

            // when
            final String notesAbbreviated = note.getAbbreviated();

            // then
            assertThat(notesAbbreviated).isEqualTo("123456789012345678901234567890123456789");
        }

        @Test
        public void does_not_abbreviate_40() throws Exception {
            // given
            note.setContent("1234567890123456789012345678901234567890"); // 40 chars

            // when
            final String notesAbbreviated = note.getAbbreviated();

            // then
            assertThat(notesAbbreviated).isEqualTo("1234567890123456789012345678901234567890");
        }

        @Test
        public void abbreviates_longer_than_40() throws Exception {
            // given
            note.setContent("1234567890123456789012345678901234567890a"); // 41 chars

            // when
            final String notesAbbreviated = note.getAbbreviated();

            // then
            assertThat(notesAbbreviated).isEqualTo("1234567890123456789012345678901234567...");
        }
    }

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