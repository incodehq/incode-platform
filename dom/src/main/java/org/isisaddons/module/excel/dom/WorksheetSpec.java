/*
 *  Copyright 2016 Dan Haywood
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
package org.isisaddons.module.excel.dom;

public class WorksheetSpec {

    /**
     * Maximum supported by Microsoft Excel UI.
     *
     * @see <a href="http://stackoverflow.com/questions/3681868/is-there-a-limit-on-an-excel-worksheets-name-length">this SO answer</a>, for example.
     */
    private static final int SHEET_NAME_MAX_LEN = 31;
    private static final String ROW_HANDLER_SUFFIX = "RowHandler";

    private final Class<?> cls;
    private final String sheetName;

    /**
     * @param cls
     * @param sheetName - must be 31 chars or less
     * @param <T>
     */
    public <T> WorksheetSpec(final Class<T> cls, String sheetName) {
        this.cls = cls;
        if(sheetName == null) {
            throw new IllegalArgumentException("Sheet name must be specified");
        }
        if(isTooLong(sheetName) && hasSuffix(sheetName)) {
            sheetName = prefix(sheetName);
        }
        if(isTooLong(sheetName)) {
            throw new IllegalArgumentException(
                    String.format("Sheet name must be less than 30 characters (was '%s'", sheetName));
        }
        this.sheetName = sheetName;
    }

    public static boolean isTooLong(final String sheetName) {
        return sheetName.length() > SHEET_NAME_MAX_LEN;
    }

    public static String trim(final String sheetName) {
        return sheetName.substring(0, SHEET_NAME_MAX_LEN);
    }

    public static boolean hasSuffix(final String sheetName) {
        return sheetName.endsWith(ROW_HANDLER_SUFFIX);
    }

    public static String prefix(final String sheetName) {
        return sheetName.substring(0, sheetName.lastIndexOf(ROW_HANDLER_SUFFIX));
    }

    public Class<?> getCls() {
        return cls;
    }

    public String getSheetName() {
        return sheetName;
    }
}
