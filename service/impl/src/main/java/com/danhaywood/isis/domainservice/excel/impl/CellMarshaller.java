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
package com.danhaywood.isis.domainservice.excel.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;

public final class CellMarshaller {

    private CellStyle dateCellStyle;

    CellMarshaller(final CellStyle dateCellStyle){
        this.dateCellStyle = dateCellStyle;}
    
    void setCellValue(
            final ObjectAdapter objectAdapter, 
            final ObjectAssociation property, 
            final Cell cell) {
        
        final ObjectAdapter valueAdapter = property.get(objectAdapter);
        
        // null
        if (valueAdapter == null) {
            cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
            return;
        }
        
        final Object valueAsObj = valueAdapter.getObject();
        if(setCellValue(cell, valueAsObj)) {
            return;
        }
        
        final String objectAsStr = valueAdapter.titleString(null);
        setCellValueAsString(cell, objectAsStr);
        return;
    }

    private boolean setCellValue(final Cell cell, final Object valueAsObj) {
        if(valueAsObj == null) {
            cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
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
        return false;
    }

    private static void setCellValueAsString(final Cell cell, final String objectAsStr) {
        cell.setCellValue(objectAsStr);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    }

    private static void setCellValueForDouble(final Cell cell, double value2) {
        cell.setCellValue(value2);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    }

    private static void setCellValueForDate(final Cell cell, Date date, CellStyle dateCellStyle) {
        cell.setCellValue(date);
        cell.setCellStyle(dateCellStyle);
    }

    @SuppressWarnings("unchecked")
    public <T> T getCellValue(final Cell cell, final Class<T> requiredType) {
        final int cellType = cell.getCellType();

        if(cellType == HSSFCell.CELL_TYPE_BLANK) {
            return null;
        }

        if(requiredType == boolean.class || requiredType == Boolean.class) {
            if(cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                boolean booleanCellValue = cell.getBooleanCellValue();
                return (T) Boolean.valueOf(booleanCellValue);
            } else {
                return null;
            }
        }
        
        if(requiredType == java.util.Date.class) {
            java.util.Date dateCellValue = cell.getDateCellValue();
            return (T) dateCellValue;
        }

        // date
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

}