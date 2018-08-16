SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO


CREATE TABLE [isissessionlogger].[SessionLogEntry](
	[sessionId] [varchar](15) NOT NULL,
	[causedBy] [varchar](18) NULL,
	[loginTimestamp] [datetime2](7) NOT NULL,
	[logoutTimestamp] [datetime2](7) NULL,
	[user] [varchar](50) NOT NULL,
 CONSTRAINT [SessionLogEntry_PK] PRIMARY KEY CLUSTERED
(
	[sessionId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


