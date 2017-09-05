SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO


CREATE TABLE [incodeCountry].[Country](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[alpha2Code] [varchar](2) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[reference] [varchar](24) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [Country_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [Country_alpha2Code_UNQ] UNIQUE NONCLUSTERED
(
	[alpha2Code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [Country_name_UNQ] UNIQUE NONCLUSTERED
(
	[name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [Country_reference_UNQ] UNIQUE NONCLUSTERED
(
	[reference] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO





CREATE TABLE [incodeCountry].[State](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[countryId] [bigint] NOT NULL,
	[name] [varchar](50) NOT NULL,
	[reference] [varchar](6) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [State_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [State_name_UNQ] UNIQUE NONCLUSTERED
(
	[name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [State_reference_UNQ] UNIQUE NONCLUSTERED
(
	[reference] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [State_N49] ON [incodeCountry].[State]
(
	[countryId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO





ALTER TABLE [incodeCountry].[State]  WITH CHECK ADD  CONSTRAINT [State_FK1] FOREIGN KEY([countryId])
REFERENCES [incodeCountry].[Country] ([id])
GO
ALTER TABLE [incodeCountry].[State] CHECK CONSTRAINT [State_FK1]
GO
