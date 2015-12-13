/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.wicket.summernote.cpt.applib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that could be applied on a property or parameter
 * of type {@link String}. Such property/parameter will be visualized
 * with <a href="http://summernote.org">Summernote</a> rich editor.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.PARAMETER})
public @interface SummernoteEditor {

    /**
     * A flag indicating whether to show the text as "contenteditable"
     * and the editor only when a word selection is made
     */
    boolean airMode() default false;

    /**
     * The initial height of the editor, in pixels.
     */
    int height() default -1;

    /**
     * The maximum height of the editor, in pixels.
     */
    int maxHeight() default -1;

    /**
     * The minimum height of the editor, in pixels.
     */
    int minHeight() default -1;

    /**
     * A text to be shown when the domain property has no value
     */
    String placeholder() default "";

    /**
     * A flag indicating whether drag and drop is supported or not
     */
    boolean disableDragAndDrop() default false;

    /**
     * A flag indicating whether the editor's shortcuts are enabled or not
     */
    boolean shortcuts() default true;

    int overlayTimeout() default -1;

    /**
     * A flag indicating whether to focus the editor when it is shown
     */
    boolean focus() default false;
}
