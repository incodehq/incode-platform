SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO


CREATE TABLE [isissettings].[ApplicationSetting](
	[key] [varchar](128) NOT NULL,
	[description] [varchar](254) NULL,
	[type] [varchar](20) NOT NULL,
	[valueRaw] [varchar](255) NOT NULL,
 CONSTRAINT [ApplicationSetting_PK] PRIMARY KEY CLUSTERED
(
	[key] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO



CREATE TABLE [isissettings].[UserSetting](
	[key] [varchar](128) NOT NULL,
	[user] [varchar](50) NOT NULL,
	[description] [varchar](254) NULL,
	[type] [varchar](20) NOT NULL,
	[valueRaw] [varchar](255) NOT NULL,
 CONSTRAINT [UserSetting_PK] PRIMARY KEY CLUSTERED
(
	[key] ASC,
	[user] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


