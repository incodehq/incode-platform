SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO


CREATE TABLE [incodeDocuments].[DocumentType](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[reference] [varchar](24) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [DocumentType_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [DocumentType_name_IDX] UNIQUE NONCLUSTERED
(
	[name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [DocumentType_reference_IDX] UNIQUE NONCLUSTERED
(
	[reference] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO



CREATE TABLE [incodeDocuments].[RenderingStrategy](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[inputNature] [varchar](255) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[outputNature] [varchar](255) NOT NULL,
	[previewsToUrl] [bit] NOT NULL,
	[reference] [varchar](24) NOT NULL,
	[rendererClassName] [varchar](254) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [RenderingStrategy_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [RenderingStrategy_name_IDX] UNIQUE NONCLUSTERED
(
	[name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [RenderingStrategy_reference_IDX] UNIQUE NONCLUSTERED
(
	[reference] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO




CREATE TABLE [incodeDocuments].[DocumentAbstract](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[atPath] [varchar](255) NOT NULL,
	[blob_bytes] [image] NULL,
	[clob_chars] [text] NULL,
	[mimeType] [varchar](255) NOT NULL,
	[name] [varchar](255) NOT NULL,
	[sort] [varchar](255) NOT NULL,
	[text] [varchar](4000) NULL,
	[typeId] [bigint] NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [DocumentAbstract_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [DocumentAbstract_atPath_type_IDX] ON [incodeDocuments].[DocumentAbstract]
(
	[atPath] ASC,
	[typeId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [DocumentAbstract_N49] ON [incodeDocuments].[DocumentAbstract]
(
	[typeId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [DocumentAbstract_type_atPath_IDX] ON [incodeDocuments].[DocumentAbstract]
(
	[typeId] ASC,
	[atPath] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO




CREATE TABLE [incodeDocuments].[Document](
	[id] [bigint] NOT NULL,
	[createdAt] [datetime2](7) NOT NULL,
	[externalUrl] [varchar](2000) NULL,
	[renderedAt] [datetime2](7) NULL,
	[state] [varchar](255) NOT NULL,
 CONSTRAINT [Document_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO




CREATE TABLE [incodeDocuments].[Paperclip](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[attachedToStr] [varchar](2000) NOT NULL,
	[documentId] [bigint] NOT NULL,
	[documentCreatedAt] [datetime2](7) NULL,
	[roleName] [varchar](50) NULL,
 CONSTRAINT [Paperclip_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [Paperclip_document_attachedTo_roleName_idx] UNIQUE NONCLUSTERED
(
	[documentId] ASC,
	[attachedToStr] ASC,
	[roleName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [Paperclip_attachedTo_document_idx] ON [incodeDocuments].[Paperclip]
(
	[attachedToStr] ASC,
	[documentId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [Paperclip_N49] ON [incodeDocuments].[Paperclip]
(
	[documentId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO



CREATE TABLE [incodeDocuments].[DocumentTemplate](
	[id] [bigint] NOT NULL,
	[atPathCopy] [varchar](255) NOT NULL,
	[contentRenderStrategyId] [bigint] NOT NULL,
	[date] [date] NOT NULL,
	[fileSuffix] [varchar](12) NOT NULL,
	[nameRenderStrategyId] [bigint] NOT NULL,
	[nameText] [varchar](255) NOT NULL,
	[previewOnly] [bit] NOT NULL,
	[typeId] [bigint] NOT NULL,
 CONSTRAINT [DocumentTemplate_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [DocumentTemplate_type_atPath_date_IDX] UNIQUE NONCLUSTERED
(
	[typeId] ASC,
	[atPathCopy] ASC,
	[date] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [DocumentTemplate_atPath_date_IDX] ON [incodeDocuments].[DocumentTemplate]
(
	[atPathCopy] ASC,
	[date] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [DocumentTemplate_N49] ON [incodeDocuments].[DocumentTemplate]
(
	[contentRenderStrategyId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [DocumentTemplate_N50] ON [incodeDocuments].[DocumentTemplate]
(
	[nameRenderStrategyId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [DocumentTemplate_N51] ON [incodeDocuments].[DocumentTemplate]
(
	[typeId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [DocumentTemplate_type_date_IDX] ON [incodeDocuments].[DocumentTemplate]
(
	[typeId] ASC,
	[date] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO





CREATE TABLE [incodeDocuments].[PaperclipForDocument](
	[id] [bigint] NOT NULL,
	[attachedToId] [bigint] NOT NULL,
 CONSTRAINT [PaperclipForDocument_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [PaperclipForDocument_N49] ON [incodeDocuments].[PaperclipForDocument]
(
	[attachedToId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO





CREATE TABLE [incodeDocuments].[Applicability](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[attachmentAdvisorClassName] [varchar](254) NOT NULL,
	[documentTemplateId] [bigint] NOT NULL,
	[domainClassName] [varchar](254) NOT NULL,
	[rendererModelFactoryClassName] [varchar](254) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [Applicability_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [Applicability_documentTemplate_domainClassName_UNQ] UNIQUE NONCLUSTERED
(
	[documentTemplateId] ASC,
	[domainClassName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [Applicability_N49] ON [incodeDocuments].[Applicability]
(
	[documentTemplateId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO





ALTER TABLE [incodeDocuments].[Document]  WITH CHECK ADD  CONSTRAINT [Document_FK1] FOREIGN KEY([id])
REFERENCES [incodeDocuments].[DocumentAbstract] ([id])
GO
ALTER TABLE [incodeDocuments].[Document] CHECK CONSTRAINT [Document_FK1]
GO


ALTER TABLE [incodeDocuments].[Paperclip]  WITH CHECK ADD  CONSTRAINT [Paperclip_FK1] FOREIGN KEY([documentId])
REFERENCES [incodeDocuments].[DocumentAbstract] ([id])
GO
ALTER TABLE [incodeDocuments].[Paperclip] CHECK CONSTRAINT [Paperclip_FK1]
GO


ALTER TABLE [incodeDocuments].[DocumentTemplate]  WITH CHECK ADD  CONSTRAINT [DocumentTemplate_FK1] FOREIGN KEY([id])
REFERENCES [incodeDocuments].[DocumentAbstract] ([id])
GO
ALTER TABLE [incodeDocuments].[DocumentTemplate] CHECK CONSTRAINT [DocumentTemplate_FK1]
GO



ALTER TABLE [incodeDocuments].[DocumentTemplate]  WITH CHECK ADD  CONSTRAINT [DocumentTemplate_FK2] FOREIGN KEY([contentRenderStrategyId])
REFERENCES [incodeDocuments].[RenderingStrategy] ([id])
GO
ALTER TABLE [incodeDocuments].[DocumentTemplate] CHECK CONSTRAINT [DocumentTemplate_FK2]
GO


ALTER TABLE [incodeDocuments].[DocumentTemplate]  WITH CHECK ADD  CONSTRAINT [DocumentTemplate_FK3] FOREIGN KEY([nameRenderStrategyId])
REFERENCES [incodeDocuments].[RenderingStrategy] ([id])
GO
ALTER TABLE [incodeDocuments].[DocumentTemplate] CHECK CONSTRAINT [DocumentTemplate_FK3]
GO


ALTER TABLE [incodeDocuments].[DocumentTemplate]  WITH CHECK ADD  CONSTRAINT [DocumentTemplate_FK4] FOREIGN KEY([typeId])
REFERENCES [incodeDocuments].[DocumentType] ([id])
GO
ALTER TABLE [incodeDocuments].[DocumentTemplate] CHECK CONSTRAINT [DocumentTemplate_FK4]
GO


ALTER TABLE [incodeDocuments].[PaperclipForDocument]  WITH CHECK ADD  CONSTRAINT [PaperclipForDocument_FK1] FOREIGN KEY([id])
REFERENCES [incodeDocuments].[Paperclip] ([id])
GO
ALTER TABLE [incodeDocuments].[PaperclipForDocument] CHECK CONSTRAINT [PaperclipForDocument_FK1]
GO


ALTER TABLE [incodeDocuments].[PaperclipForDocument]  WITH CHECK ADD  CONSTRAINT [PaperclipForDocument_FK2] FOREIGN KEY([attachedToId])
REFERENCES [incodeDocuments].[Document] ([id])
GO
ALTER TABLE [incodeDocuments].[PaperclipForDocument] CHECK CONSTRAINT [PaperclipForDocument_FK2]
GO



ALTER TABLE [incodeDocuments].[Applicability]  WITH CHECK ADD  CONSTRAINT [Applicability_FK1] FOREIGN KEY([documentTemplateId])
REFERENCES [incodeDocuments].[DocumentTemplate] ([id])
GO
ALTER TABLE [incodeDocuments].[Applicability] CHECK CONSTRAINT [Applicability_FK1]
GO


ALTER TABLE [incodeDocuments].[DocumentAbstract]  WITH CHECK ADD  CONSTRAINT [DocumentAbstract_FK1] FOREIGN KEY([typeId])
REFERENCES [incodeDocuments].[DocumentType] ([id])
GO
ALTER TABLE [incodeDocuments].[DocumentAbstract] CHECK CONSTRAINT [DocumentAbstract_FK1]
GO


