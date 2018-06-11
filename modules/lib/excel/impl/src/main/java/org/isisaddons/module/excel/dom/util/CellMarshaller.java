package org.isisaddons.module.excel.dom.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
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
    private final CellStyle defaultCellStyle;
    private final BookmarkService bookmarkService;

    CellMarshaller(
            final BookmarkService bookmarkService, 
            final CellStyle dateCellStyle,
            final CellStyle defaultCellStyle){
        this.bookmarkService = bookmarkService;
        this.dateCellStyle = dateCellStyle;
        this.defaultCellStyle = defaultCellStyle;
    }
    
    void setCellValue(
            final ObjectAdapter objectAdapter, 
            final OneToOneAssociation otoa, 
            final Cell cell) {
        
        final ObjectAdapter propertyAdapter = otoa.get(objectAdapter);
        
        // null
        if (propertyAdapter == null) {
            cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
            cell.setCellStyle(defaultCellStyle);
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
            setCellValueForBookmark(cell, propertyAsObj, propertyAsTitle, defaultCellStyle);
            return;
        }

        // fallback, best effort
        setCellValueForString(cell, propertyAsTitle, defaultCellStyle);
        return;
    }

    void setCellValueForHyperlink(
            final ObjectAdapter objectAdapter,
            final OneToOneAssociation otoa,
            final Cell cell) {

        final ObjectAdapter propertyAdapter = otoa.get(objectAdapter);

        // null
        if (propertyAdapter == null) {
            cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
            cell.setCellStyle(defaultCellStyle);
            return;
        }

        // only String type expected
        if(propertyAdapter.getObject() instanceof String) {

            String stringValue = (String) propertyAdapter.getObject();

            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(stringValue);

            CreationHelper createHelper = cell.getSheet().getWorkbook().getCreationHelper();
            XSSFHyperlink link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_URL);
            link.setAddress(stringValue);
            cell.setHyperlink((XSSFHyperlink) link);

            cell.setCellStyle(defaultCellStyle);

        } else {
            // silently ignore annotation and fall back
            setCellValue(objectAdapter, otoa, cell);
        }

    }

    private boolean setCellValue(final Cell cell, final Object valueAsObj) {
        if(valueAsObj == null) {
            cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
            cell.setCellStyle(defaultCellStyle);
            return true;
        }
        
        // string
        if(valueAsObj instanceof String) {
            String value = (String) valueAsObj;
            setCellValueForString(cell, value, defaultCellStyle);
            return true;
        } 

        // boolean
        if(valueAsObj instanceof Boolean) {
            Boolean value = (Boolean) valueAsObj;
            cell.setCellValue(value);
            cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
            cell.setCellStyle(defaultCellStyle);
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
            setCellValueForDouble(cell, (double)value, defaultCellStyle);
            return true;
        }
        if(valueAsObj instanceof Float) {
            Float value = (Float) valueAsObj;
            setCellValueForDouble(cell, (double)value, defaultCellStyle);
            return true;
        } 
        if(valueAsObj instanceof BigDecimal) {
            BigDecimal value = (BigDecimal) valueAsObj;
            setCellValueForDouble(cell, value.doubleValue(), defaultCellStyle);
            return true;
        } 
        if(valueAsObj instanceof BigInteger) {
            BigInteger value = (BigInteger) valueAsObj;
            setCellValueForDouble(cell, value.doubleValue(), defaultCellStyle);
            return true;
        } 
        if(valueAsObj instanceof Long) {
            Long value = (Long) valueAsObj;
            setCellValueForDouble(cell, (double)value, defaultCellStyle);
            return true;
        } 
        if(valueAsObj instanceof Integer) {
            Integer value = (Integer) valueAsObj;
            setCellValueForDouble(cell, (double)value, defaultCellStyle);
            return true;
        } 
        if(valueAsObj instanceof Short) {
            Short value = (Short) valueAsObj;
            setCellValueForDouble(cell, (double)value, defaultCellStyle);
            return true;
        } 
        if(valueAsObj instanceof Byte) {
            Byte value = (Byte) valueAsObj;
            setCellValueForDouble(cell, (double)value, defaultCellStyle);
            return true;
        }
        if(valueAsObj instanceof Enum) {
            Enum<?> value = (Enum<?>) valueAsObj;
            setCellValueForEnum(cell, (Enum<?>)value, defaultCellStyle);
            return true;
        }
        return false;
    }

    private static void setCellValueForString(final Cell cell, final String objectAsStr, CellStyle cellStyle) {
        // char 10 is for linebreak within a cell; to display correctly wrap text needs to be set to true
        if (objectAsStr.contains(Character.toString((char)10))) {
            CellStyle wrappedCellStyle = cell.getSheet().getWorkbook().createCellStyle();
            wrappedCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
            wrappedCellStyle.setWrapText(true);
            cell.setCellStyle(wrappedCellStyle);
        } else {
            cell.setCellStyle(cellStyle);
        }
        cell.setCellValue(objectAsStr);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    }

    private void setCellValueForBookmark(final Cell cell, final Object propertyAsObject, final String propertyAsTitle, final CellStyle cellStyle) {
        Bookmark bookmark = bookmarkService.bookmarkFor(propertyAsObject);
        setCellComment(cell, bookmark.toString());
        
        cell.setCellValue(propertyAsTitle);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle);
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

    private static <E extends Enum<E>> void setCellValueForEnum(final Cell cell, final Enum<E> objectAsStr, final CellStyle cellStyle) {
        cell.setCellValue(objectAsStr.name());
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle);
    }
    
    private static void setCellValueForDouble(final Cell cell, double value, final CellStyle cellStyle) {
        cell.setCellValue(value);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(cellStyle);
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