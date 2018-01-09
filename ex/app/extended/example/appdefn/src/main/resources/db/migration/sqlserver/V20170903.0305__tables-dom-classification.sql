SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO



CREATE TABLE [incodeClassification].[Category](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[fullyQualifiedName] [varchar](254) NOT NULL,
	[fullyQualifiedOrdinal] [varchar](50) NOT NULL,
	[name] [varchar](100) NOT NULL,
	[ordinal] [int] NULL,
	[parentId] [bigint] NULL,
	[reference] [varchar](24) NULL,
	[taxonomyId] [bigint] NULL,
	[version] [bigint] NOT NULL,
	[DISCRIMINATOR] [varchar](255) NOT NULL,
 CONSTRAINT [Category_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [Classification_fullyQualifiedName_UNQ] UNIQUE NONCLUSTERED
(
	[fullyQualifiedName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [Classification_parent_Name_UNQ] UNIQUE NONCLUSTERED
(
	[parentId] ASC,
	[name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [Category_N49] ON [incodeClassification].[Category]
(
	[taxonomyId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [Category_N50] ON [incodeClassification].[Category]
(
	[parentId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO





CREATE TABLE [incodeClassification].[Classification](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[categoryId] [bigint] NOT NULL,
	[classifiedStr] [varchar](2000) NOT NULL,
	[taxonomyId] [bigint] NOT NULL,
 CONSTRAINT [Classification_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [ClassificationLink_classified_category_UNQ] UNIQUE NONCLUSTERED
(
	[classifiedStr] ASC,
	[taxonomyId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [Classification_N49] ON [incodeClassification].[Classification]
(
	[categoryId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [Classification_N50] ON [incodeClassification].[Classification]
(
	[taxonomyId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [ClassificationLink_category_classified_IDX] ON [incodeClassification].[Classification]
(
	[taxonomyId] ASC,
	[classifiedStr] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO




CREATE TABLE [incodeClassification].[Applicability](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[atPath] [varchar](255) NOT NULL,
	[domainType] [varchar](255) NOT NULL,
	[taxonomyId] [bigint] NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [Applicability_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [Applicability_domainType_atPath_taxonomy_UNQ] UNIQUE NONCLUSTERED
(
	[domainType] ASC,
	[atPath] ASC,
	[taxonomyId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [Applicability_N49] ON [incodeClassification].[Applicability]
(
	[taxonomyId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO





ALTER TABLE [incodeClassification].[Category]  WITH CHECK ADD  CONSTRAINT [Category_FK1] FOREIGN KEY([parentId])
REFERENCES [incodeClassification].[Category] ([id])
GO
ALTER TABLE [incodeClassification].[Category] CHECK CONSTRAINT [Category_FK1]
GO


ALTER TABLE [incodeClassification].[Category]  WITH CHECK ADD  CONSTRAINT [Category_FK2] FOREIGN KEY([taxonomyId])
REFERENCES [incodeClassification].[Category] ([id])
GO
ALTER TABLE [incodeClassification].[Category] CHECK CONSTRAINT [Category_FK2]
GO



ALTER TABLE [incodeClassification].[Classification]  WITH CHECK ADD  CONSTRAINT [Classification_FK1] FOREIGN KEY([categoryId])
REFERENCES [incodeClassification].[Category] ([id])
GO
ALTER TABLE [incodeClassification].[Classification] CHECK CONSTRAINT [Classification_FK1]
GO


ALTER TABLE [incodeClassification].[Classification]  WITH CHECK ADD  CONSTRAINT [Classification_FK2] FOREIGN KEY([taxonomyId])
REFERENCES [incodeClassification].[Category] ([id])
GO
ALTER TABLE [incodeClassification].[Classification] CHECK CONSTRAINT [Classification_FK2]
GO


ALTER TABLE [incodeClassification].[Applicability]  WITH CHECK ADD  CONSTRAINT [Applicability_FK1] FOREIGN KEY([taxonomyId])
REFERENCES [incodeClassification].[Category] ([id])
GO
ALTER TABLE [incodeClassification].[Applicability] CHECK CONSTRAINT [Applicability_FK1]
GO


