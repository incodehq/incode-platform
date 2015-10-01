package org.incode.module.note.dom.impl.note;

import org.junit.Before;
import org.junit.Test;

import org.incode.module.note.dom.api.notable.Notable;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteContributionsOnNotableTest {

    NoteContributionsOnNotable noteContributionsOnNotable;

    @Before
    public void setUp() throws Exception {
        noteContributionsOnNotable = new NoteContributionsOnNotable();
    }

    public static class HideRemoveNoteTest extends NoteContributionsOnNotableTest {

        Notable notable;
        Note note;

        @Test
        public void not_visible_if_contributed_to_notable() throws Exception {

            // when
            notable = null;
            note = new Note();
            final boolean isHidden = noteContributionsOnNotable.hideRemoveNote(notable, note);

            // then
            assertThat(isHidden).isTrue();
        }

        @Test
        public void visible_if_contributed_to_note() throws Exception {

            // when
            notable = new Notable() {};
            note = null;
            final boolean isHidden = noteContributionsOnNotable.hideRemoveNote(notable, note);

            // then
            assertThat(isHidden).isFalse();
        }

    }
}