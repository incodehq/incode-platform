/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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

package com.danhaywood.isis.wicket.ui.components.collectioncontents.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.encoding.UrlEncoder;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.string.Strings;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.filter.Filter;
import org.apache.isis.applib.filter.Filters;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociationFilters;
import org.apache.isis.core.progmodel.facets.value.booleans.BooleanValueFacet;
import org.apache.isis.core.progmodel.facets.value.date.DateValueFacet;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;

/**
 * {@link PanelAbstract Panel} that represents a {@link EntityCollectionModel
 * collection of entity}s rendered using {@link AjaxFallbackDefaultDataTable}.
 */
public class CollectionContentsAsExcel extends PanelAbstract<EntityCollectionModel> {

    private static final long serialVersionUID = 1L;

    private static final String ID_FEEDBACK = "feedback";
    private static final String ID_DOWNLOAD = "download";

    public CollectionContentsAsExcel(final String id, final EntityCollectionModel model) {
        super(id, model);

        buildGui();
    }

    private void buildGui() {

        final EntityCollectionModel model = getModel();
        
        final FeedbackPanel feedback = new FeedbackPanel(ID_FEEDBACK);
        feedback.setOutputMarkupId(true);
        addOrReplace(feedback);

        final LoadableDetachableModel<File> fileModel = new TemporaryExcelFileModel(model);
        final String xlsxFileName = xlsxFileNameFor(model);
        final DownloadLink link = new TemporaryFileDownloadLink(ID_DOWNLOAD, fileModel, xlsxFileName);
        
        addOrReplace(link);
    }

    private static String xlsxFileNameFor(final EntityCollectionModel model) {
        return model.getName().replaceAll(" ", "") + ".xlsx";
    }

    
    @Override
    protected void onModelChanged() {
        buildGui();
    }

    static class TemporaryFileDownloadLink extends DownloadLink {

        private static final long serialVersionUID = 1L;
        
        private final String xlsxFileName;

        public TemporaryFileDownloadLink(String id, LoadableDetachableModel<File> model, String xlsxFileName) {
            super(id, model, xlsxFileName);
            this.xlsxFileName = xlsxFileName;
        }

        @Override
        public void onClick()
        {
            final File file = getModelObject();
            if (file == null)
            {
                throw new IllegalStateException(getClass().getName() +
                    " failed to retrieve a File object from model");
            }

            String fileName = UrlEncoder.QUERY_INSTANCE.encode(this.xlsxFileName, getRequest().getCharset());

            final IResourceStream resourceStream = new FileResourceStream(
                new org.apache.wicket.util.file.File(file)) {
                
                private static final long serialVersionUID = 1L;

                @Override
                public String getContentType() {
                    //return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml";
                    return "application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml";
                }
            };
            
            getRequestCycle().scheduleRequestHandlerAfterCurrent(
                new ResourceStreamRequestHandler(resourceStream)
                {
                    @Override
                    public void respond(IRequestCycle requestCycle)
                    {
                        super.respond(requestCycle);
                        Files.remove(file);
                    }
                }.setFileName(fileName)
                    .setContentDisposition(ContentDisposition.ATTACHMENT));
        }
        
        
    }

    static class TemporaryExcelFileModel extends LoadableDetachableModel<File> {

        private static final long serialVersionUID = 1L;
        
        private final EntityCollectionModel model;

        public TemporaryExcelFileModel(EntityCollectionModel model) {
            this.model = model;
        }

        static class RowFactory {
            private final Sheet sheet;
            private int rowNum;

            RowFactory(Sheet sheet) {
                this.sheet = sheet;
            }

            public Row newRow() {
                return sheet.createRow((short) rowNum++);
            }
        }
        
        @Override
        protected File load() {
            
            try {
                return createFile();
                
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private File createFile() throws IOException, FileNotFoundException {
            final Workbook wb = new XSSFWorkbook();
            String sheetName = model.getName();
            final File tempFile = File.createTempFile("/tmp", sheetName + ".xlsx");
            final FileOutputStream fos = new FileOutputStream(tempFile);
            final Sheet sh = wb.createSheet(sheetName);
            
            final ObjectSpecification typeOfSpec = model.getTypeOfSpecification();
            @SuppressWarnings("unchecked")
            final Filter<ObjectAssociation> filter = Filters.and(
                    ObjectAssociationFilters.PROPERTIES, 
                    ObjectAssociationFilters.staticallyVisible(model.isParented()? Where.PARENTED_TABLES: Where.STANDALONE_TABLES));
            final List<? extends ObjectAssociation> propertyList = typeOfSpec.getAssociations(filter);

            final RowFactory rowFactory = new RowFactory(sh);
            Row row = rowFactory.newRow();
            
            // header row
            int i=0;
            for (ObjectAssociation property : propertyList) {
                final Cell cell = row.createCell((short) i++);
                cell.setCellValue(property.getName());
            }
            
            // detail rows
            final List<ObjectAdapter> adapters = model.getObject();
            for (final ObjectAdapter objectAdapter : adapters) {
                row = rowFactory.newRow();
                i=0;
                for (final ObjectAssociation property : propertyList) {
                    final Cell cell = row.createCell((short) i++);
                    setCellValue(objectAdapter, property, cell);
                }
            }
            
            wb.write(fos);
            fos.close();
            return tempFile;
        }

        private void setCellValue(final ObjectAdapter objectAdapter, final ObjectAssociation property, final Cell cell) {
            
            final ObjectAdapter valueAdapter = property.get(objectAdapter);
            
            // null
            if (valueAdapter == null) {
                cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
                return;
            } 
            final Object valueAsObj = valueAdapter.getObject();
            if(valueAsObj == null) {
                cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
                return;
            }
            
            // boolean
            if(valueAsObj instanceof Boolean) {
                Boolean value = (Boolean) valueAsObj;
                cell.setCellValue(value);
                cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
                return;
            } 

            // 
//            
//            // date
//            if(valueAsObj instanceof Date) {
//                Date value = (Date) valueAsObj;
//                cell.setCellValue(value);
//                return;
//            } 
//            if(valueAsObj instanceof org.apache.isis.applib.value.Date) {
//                org.apache.isis.applib.value.Date value = (org.apache.isis.applib.value.Date) valueAsObj;
//                cell.setCellValue(value.dateValue());
//                return;
//            } 
//            if(valueAsObj instanceof org.apache.isis.applib.value.DateTime) {
//                org.apache.isis.applib.value.DateTime value = (org.apache.isis.applib.value.DateTime) valueAsObj;
//                cell.setCellValue(value.dateValue());
//                return;
//            } 
//            if(valueAsObj instanceof LocalDate) {
//                LocalDate value = (LocalDate) valueAsObj;
//                cell.setCellValue(value.toDateTimeAtStartOfDay().toDate());
//                return;
//            } 
//            if(valueAsObj instanceof LocalDateTime) {
//                LocalDateTime value = (LocalDateTime) valueAsObj;
//                cell.setCellValue(value.toDate());
//                return;
//            } 
//            if(valueAsObj instanceof DateTime) {
//                LocalDateTime value = (LocalDateTime) valueAsObj;
//                cell.setCellValue(value.toDate());
//                return;
//            }
//            
            // number
            if(valueAsObj instanceof Double) {
                Double value = (Double) valueAsObj;
                cell.setCellValue(value);
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                return;
            }
            if(valueAsObj instanceof Float) {
                Float value = (Float) valueAsObj;
                cell.setCellValue(value);
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                return;
            } 
            if(valueAsObj instanceof BigDecimal) {
                BigDecimal value = (BigDecimal) valueAsObj;
                cell.setCellValue(value.doubleValue());
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                return;
            } 
            if(valueAsObj instanceof BigInteger) {
                BigInteger value = (BigInteger) valueAsObj;
                cell.setCellValue(value.doubleValue());
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                return;
            } 
            if(valueAsObj instanceof Long) {
                Long value = (Long) valueAsObj;
                cell.setCellValue(value);
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                return;
            } 
            if(valueAsObj instanceof Integer) {
                Integer value = (Integer) valueAsObj;
                cell.setCellValue(value);
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                return;
            } 
            if(valueAsObj instanceof Short) {
                Short value = (Short) valueAsObj;
                cell.setCellValue(value);
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                return;
            } 
            if(valueAsObj instanceof Byte) {
                Byte value = (Byte) valueAsObj;
                cell.setCellValue(value);
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                return;
            } 
            
            final String objectAsStr = valueAdapter.titleString(null);
            cell.setCellValue(objectAsStr);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            return;
        }
    }
}
