SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO



CREATE TABLE [isisaudit].[AuditEntry](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[memberIdentifier] [varchar](255) NULL,
	[postValue] [varchar](255) NULL,
	[preValue] [varchar](255) NULL,
	[propertyId] [varchar](50) NULL,
	[sequence] [int] NOT NULL,
	[targetClass] [varchar](50) NULL,
	[target] [varchar](2000) NULL,
	[timestamp] [datetime2](7) NOT NULL,
	[transactionId] [varchar](36) NOT NULL,
	[user] [varchar](50) NOT NULL,
 CONSTRAINT [AuditEntry_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO



CREATE UNIQUE NONCLUSTERED INDEX [AuditEntry_ak] ON [isisaudit].[AuditEntry]
(
	[transactionId] ASC,
	[sequence] ASC,
	[target] ASC,
	[propertyId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO



