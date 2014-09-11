/*
 *  Copyright 2014 Dan Haywood
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.OneToOneAssociation;

final class CellMarshaller {

    private final CellStyle dateCellStyle;
    private final BookmarkService bookmarkService;

    CellMarshaller(
            final BookmarkService bookmarkService, 
            final CellStyle dateCellStyle){
        this.bookmarkService = bookmarkService;
        this.dateCellStyle = dateCellStyle;
    }
    
    void setCellValue(
            final ObjectAdapter objectAdapter, 
            final OneToOneAssociation otoa, 
            final Cell cell) {
        
        final ObjectAdapter propertyAdapter = otoa.get(objectAdapter);
        
        // null
        if (propertyAdapter == null) {
            cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
            return;
        }
        
        final ObjectSpecification propertySpec = otoa.getSpecification();
        final Object propertyAsObj = propertyAdapter.getObject();
        final String propertyAsTitle = propertyAdapter.titleString(null);
        
        // value types
        if(propertySpec.isValue()) {
            if(setCellValue(cell, propertyAsObj)) {
                return;
            }
        }
        
        // reference types
        if(!propertySpec.isParentedOrFreeCollection()) {
            setCellValueForBookmark(cell, propertyAsObj, propertyAsTitle);
            return;
        }

        // fallback, best effort
        setCellValueForString(cell, propertyAsTitle);
        return;
    }

    private boolean setCellValue(final Cell cell, final Object valueAsObj) {
        if(valueAsObj == null) {
            cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
            return true;
        }
        
        // string
        if(valueAsObj instanceof String) {
            String value = (String) valueAsObj;
            setCellValueForString(cell, value);
            return true;
        } 

        // boolean
        if(valueAsObj instanceof Boolean) {
            Boolean value = (Boolean) valueAsObj;
            cell.setCellValue(value);
            cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
            return true;
        } 
        
        // date
        if(valueAsObj instanceof Date) {
            Date value = (Date) valueAsObj;
            setCellValueForDate(cell, value, dateCellStyle);
            return true;
        } 
        if(valueAsObj instanceof org.apache.isis.applib.value.Date) {
            org.apache.isis.applib.value.Date value = (org.apache.isis.applib.value.Date) valueAsObj;
            Date dateValue = value.dateValue();
            setCellValueForDate(cell, dateValue, dateCellStyle);
            return true;
        } 
        if(valueAsObj instanceof org.apache.isis.applib.value.DateTime) {
            org.apache.isis.applib.value.DateTime value = (org.apache.isis.applib.value.DateTime) valueAsObj;
            Date dateValue = value.dateValue();
            setCellValueForDate(cell, dateValue, dateCellStyle);
            return true;
        } 
        if(valueAsObj instanceof LocalDate) {
            LocalDate value = (LocalDate) valueAsObj;
            Date date = value.toDateTimeAtStartOfDay().toDate();
            setCellValueForDate(cell, date, dateCellStyle);
            return true;
        } 
        if(valueAsObj instanceof LocalDateTime) {
            LocalDateTime value = (LocalDateTime) valueAsObj;
            Date date = value.toDate();
            setCellValueForDate(cell, date, dateCellStyle);
            return true;
        } 
        if(valueAsObj instanceof DateTime) {
            DateTime value = (DateTime) valueAsObj;
            Date date = value.toDate();
            setCellValueForDate(cell, date, dateCellStyle);
            return true;
        }
        
        // number
        if(valueAsObj instanceof Double) {
            Double value = (Double) valueAsObj;
            setCellValueForDouble(cell, (double)value);
            return true;
        }
        if(valueAsObj instanceof Float) {
            Float value = (Float) valueAsObj;
            setCellValueForDouble(cell, (double)value);
            return true;
        } 
        if(valueAsObj instanceof BigDecimal) {
            BigDecimal value = (BigDecimal) valueAsObj;
            setCellValueForDouble(cell, value.doubleValue());
            return true;
        } 
        if(valueAsObj instanceof BigInteger) {
            BigInteger value = (BigInteger) valueAsObj;
            setCellValueForDouble(cell, value.doubleValue());
            return true;
        } 
        if(valueAsObj instanceof Long) {
            Long value = (Long) valueAsObj;
            setCellValueForDouble(cell, (double)value);
            return true;
        } 
        if(valueAsObj instanceof Integer) {
            Integer value = (Integer) valueAsObj;
            setCellValueForDouble(cell, (double)value);
            return true;
        } 
        if(valueAsObj instanceof Short) {
            Short value = (Short) valueAsObj;
            setCellValueForDouble(cell, (double)value);
            return true;
        } 
        if(valueAsObj instanceof Byte) {
            Byte value = (Byte) valueAsObj;
            setCellValueForDouble(cell, (double)value);
            return true;
        }
        if(valueAsObj instanceof Enum) {
            Enum<?> value = (Enum<?>) valueAsObj;
            setCellValueForEnum(cell, (Enum<?>)value);
            return true;
        }
        return false;
    }

    private static void setCellValueForString(final Cell cell, final String objectAsStr) {
        cell.setCellValue(objectAsStr);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    }

    private void setCellValueForBookmark(final Cell cell, final Object propertyAsObject, final String propertyAsTitle) {
        Bookmark bookmark = bookmarkService.bookmarkFor(propertyAsObject);
        setCellComment(cell, bookmark.toString());
        
        cell.setCellValue(propertyAsTitle);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    }

    private static void setCellComment(final Cell cell, final String commentText) {
        Sheet sheet = cell.getSheet();
        Row row = cell.getRow();
        Workbook workbook = sheet.getWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        ClientAnchor anchor = creationHelper.createClientAnchor();
        anchor.setCol1(cell.getColumnIndex());
        anchor.setCol2(cell.getColumnIndex()+1);
        anchor.setRow1(row.getRowNum());
        anchor.setRow2(row.getRowNum()+3);
        
        Drawing drawing = sheet.createDrawingPatriarch();
        Comment comment1 = drawing.createCellComment(anchor);
        
        RichTextString commentRtf = creationHelper.createRichTextString(commentText);
        
        comment1.setString(commentRtf);
        Comment comment = comment1;
        cell.setCellComment(comment);
    }

    private static <E extends Enum<E>> void setCellValueForEnum(final Cell cell, final Enum<E> objectAsStr) {
        cell.setCellValue(objectAsStr.name());
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    }
    
    private static void setCellValueForDouble(final Cell cell, double value) {
        cell.setCellValue(value);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    }

    private static void setCellValueForDate(final Cell cell, Date date, CellStyle dateCellStyle) {
        cell.setCellValue(date);
        cell.setCellStyle(dateCellStyle);
    }

    String getStringCellValue(Cell cell) {
        return getCellValue(cell, String.class);
    }

    Object getCellValue(final Cell cell, final OneToOneAssociation otoa) {

        final int cellType = cell.getCellType();

        if(cellType == HSSFCell.CELL_TYPE_BLANK) {
            return null;
        }

        final ObjectSpecification propertySpec = otoa.getSpecification();
        Class<?> requiredType = propertySpec.getCorrespondingClass();
        
        // value types
        if(propertySpec.isValue()) {
            return getCellValue(cell, requiredType);
        }
        
        // reference types
        if(!propertySpec.isParentedOrFreeCollection()) {
            return getCellComment(cell, requiredType);
        }
        
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T getCellValue(final Cell cell, final Class<T> requiredType) {
        final int cellType = cell.getCellType();

        if(requiredType == boolean.class || requiredType == Boolean.class) {
            if(cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                boolean booleanCellValue = cell.getBooleanCellValue();
                return (T) Boolean.valueOf(booleanCellValue);
            } else {
                return null;
            }
        }
        
        // enum
        if(Enum.class.isAssignableFrom(requiredType)) {
            String stringCellValue = cell.getStringCellValue();
            @SuppressWarnings("rawtypes")
            Class rawType = requiredType;
            return (T) Enum.valueOf(rawType, stringCellValue);
        }
        
        // date
        if(requiredType == java.util.Date.class) {
            java.util.Date dateCellValue = cell.getDateCellValue();
            return (T) dateCellValue;
        }

        if(requiredType == org.apache.isis.applib.value.Date.class) {
            java.util.Date dateCellValue = cell.getDateCellValue();
            return (T)new org.apache.isis.applib.value.Date(dateCellValue);
        } 

        if(requiredType == org.apache.isis.applib.value.DateTime.class) {
            java.util.Date dateCellValue = cell.getDateCellValue();
            return (T)new org.apache.isis.applib.value.DateTime(dateCellValue);
        } 
        
        if(requiredType == LocalDate.class) {
            java.util.Date dateCellValue = cell.getDateCellValue();
            return (T) new LocalDate(dateCellValue.getTime());
        } 
        
        if(requiredType == LocalDateTime.class) {
            java.util.Date dateCellValue = cell.getDateCellValue();
            return (T) new LocalDateTime(dateCellValue.getTime());
        } 

        if(requiredType == DateTime.class) {
            java.util.Date dateCellValue = cell.getDateCellValue();
            return (T) new DateTime(dateCellValue.getTime());
        } 
        
        
        // number
        if(requiredType == double.class || requiredType == Double.class) {
            if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                double doubleValue = cell.getNumericCellValue();
                return (T) Double.valueOf(doubleValue);
            } else {
                return null;
            }
        } 
        
        if(requiredType == float.class || requiredType == Float.class) {
            if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                float floatValue = (float)cell.getNumericCellValue();
                return (T) Float.valueOf(floatValue);
            } else {
                return null;
            }
        } 
        
        if(requiredType == BigDecimal.class) {
            if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                double doubleValue = cell.getNumericCellValue();
                return (T) BigDecimal.valueOf(doubleValue);
            } else {
                return null;
            }
        } 
        
        if(requiredType == BigInteger.class) {
            if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                long longValue = (long)cell.getNumericCellValue();
                return (T) BigInteger.valueOf(longValue);
            } else {
                return null;
            }
        } 

        if(requiredType == long.class || requiredType == Long.class) {
            if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                long longValue = (long) cell.getNumericCellValue();
                return (T) Long.valueOf(longValue);
            } else {
                return null;
            }
        } 
        
        if(requiredType == int.class || requiredType == Integer.class) {
            if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                int intValue = (int) cell.getNumericCellValue();
                return (T) Integer.valueOf(intValue);
            } else {
                return null;
            }
        } 
        
        if(requiredType == short.class || requiredType == Short.class) {
            if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                short shortValue = (short) cell.getNumericCellValue();
                return (T) Short.valueOf(shortValue);
            } else {
                return null;
            }
        } 
        
        if(requiredType == byte.class || requiredType == Byte.class) {
            if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                byte byteValue = (byte) cell.getNumericCellValue();
                return (T) Byte.valueOf(byteValue);
            } else {
                return null;
            }
        } 

        if(requiredType == String.class) {
            if(cellType == HSSFCell.CELL_TYPE_STRING) {
                return (T) cell.getStringCellValue();
            } else {
                return null;
            }
        }
        return null;
    }

    private Object getCellComment(final Cell cell, final Class<?> requiredType) {
        final Comment comment = cell.getCellComment();
        if(comment == null) {
            return null;
        } 
        final RichTextString commentRts = comment.getString();
        if(commentRts == null) {
            return null;
        }
        final String bookmarkStr = commentRts.getString();
        final Bookmark bookmark = new Bookmark(bookmarkStr);
        return bookmarkService.lookup(bookmark, requiredType);
    }
    

}